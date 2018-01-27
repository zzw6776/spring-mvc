package com.demo.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.demo.hibernate.dao.KeyValueDao;
import com.demo.hibernate.entity.FundPush;
import com.demo.hibernate.entity.KeyValue;
import com.demo.hibernate.entity.User;
import com.demo.service.impl.FundPushServiceImpl;
import com.demo.service.impl.UserServiceImpl;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.log4j.Log4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
@EnableScheduling
@Log4j
public class FundPushTask {

    @Autowired
    private FundPushServiceImpl fundPushService;
    @Autowired
    private UserServiceImpl userService;
    @Resource(name = "keyValue")
    Map<String, String> keyValueMap;
    @Autowired
    KeyValueDao keyValueDao;

    private Set<String> ids = new HashSet<>();

    public static final String GET_ESTIMATE_FUND_URL = "http://fundgz.1234567.com.cn/js/ID.js";

    public static final String GET_ACTUAL_FUND_URL = "https://fundmobapi.eastmoney.com/FundMApi/FundBaseTypeInformation.ashx?FCODE=ID&deviceid=Wap&plat=Wap&product=EFund&version=2.0.0";

    private static  boolean ERROR_NOTICE = false;

    private static  boolean FIRST_RUN = false;

    public static void main(String[] args) {
        FundPushTask fundPushTask = new FundPushTask();
        fundPushTask.test();
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void test() {
        if (!FIRST_RUN) {
            WeChatPushUtil.weChatPush("SCU12427T981f7b2e2ed51c827ba5ffa7f65f18d559c5dc3614d0d","开始运行",System.currentTimeMillis()+"开始运行");
            FIRST_RUN = true;
        }
        String result = HttpClientUtil.get("https://c0.3.cn/stock?skuId=6023789&area=15_1213_3411_52667&cat=1,1,1&buyNum=1&extraParam=%7B%22originid%22:%221%22%7D");
        try {
            JSONObject jsonObject = JSON.parseObject(result);
            int intValue = jsonObject.getJSONObject("stock").getIntValue("StockState");
            if (intValue != 34) {
                WeChatPushUtil.weChatPush("SCU12427T981f7b2e2ed51c827ba5ffa7f65f18d559c5dc3614d0d","有货啦",System.currentTimeMillis()+"有货啦");
            }
        } catch (Exception e) {
            log.error(e);
            if (!ERROR_NOTICE) {
                WeChatPushUtil.weChatPush("SCU12427T981f7b2e2ed51c827ba5ffa7f65f18d559c5dc3614d0d","系统出错",TypeUtil.getErrorInfoFromException(e));
                ERROR_NOTICE = true;
            }
        }


    }


    @Scheduled(cron = "0 0/30 * * * ?")
    public void flushMap() {
        List<KeyValue> keyValues = keyValueDao.findAll();
        for (KeyValue keyValue : keyValues) {
            keyValueMap.put(keyValue.getKey(), keyValue.getValue());
        }
    }


    @Scheduled(cron = "0 45 14 ? * 1-5")
    public void fundEstimatePush() {
        log.info("估值播报开始");
        if (!keyValueMap.get("FundEstimateSwitch").equals("true")) {
            return;
        }
        List<FundPush> fundList = fundPushService.findAll();
        for (FundPush fundPush : fundList) {
            log.info("估值播报开始:" + fundPush.getFundName());
            String fundId = fundPush.getFundId();
            String result = HttpClientUtil.get(GET_ESTIMATE_FUND_URL.replace("ID", fundId));
            String fundText = getEstimateFundTextByJson(result);
            if (!StringUtils.isEmpty(fundText)) {
                String[] accounds = fundPush.getAccounts().split(",");
                for (String accound : accounds) {
                    User user = new User();
                    user.setAccount(accound);
                    List<User> users = userService.select(user);
                    if (!CollectionUtils.isEmpty(users)) {
                        //accout为唯一索引,所以只可能有一条
                        String scKey = users.get(0).getScKey();
                        WeChatPushUtil.weChatPush(scKey, "基金估值", fundText);
                    }
                }
            }
        }
    }

    private String getEstimateFundTextByJson(String result) {
        String pattern = "jsonpgz\\((.*)\\);";
        // 创建 Pattern 对象
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(result);
        if (m.find()) {
            JSONObject jsonObject = JSON.parseObject(m.group(1));
            String name = (String) jsonObject.get("name");
            String gszzl = (String) jsonObject.get("gszzl");
            String jzrq = (String) jsonObject.get("jzrq");
            String res = "截至" + new SimpleDateFormat("MM月dd日").format(new Date()) + "," + name + "  净值估算为" + gszzl.replace("-", "负") + "%";
            return res;
        }
        return null;
    }

    @Scheduled(cron = "0/20 * 18-23,0 ? * 1-5")
    public void fundActualPush() {
        try {
            if (!keyValueMap.get("FundActualSwitch").equals("true")) {
                return;
            }
            List<FundPush> fundList = fundPushService.findAll();
            String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            for (FundPush fundPush : fundList) {
                if (!now.equals(fundPush.getLastActualTime())) {
                    String fundId = fundPush.getFundId();
                    String result = HttpClientUtil.get(GET_ACTUAL_FUND_URL.replace("ID", fundId));
                    log.info(fundPush.getFundName());
                    String fundText = getActualFundText(result);
                    if (!StringUtils.isEmpty(fundText)) {
                        String[] accounds = fundPush.getAccounts().split(",");
                        for (String accound : accounds) {
                            User user = new User();
                            user.setAccount(accound);
                            List<User> users = userService.select(user);
                            if (!CollectionUtils.isEmpty(users)) {
                                //accout为唯一索引,所以只可能有一条
                                String scKey = users.get(0).getScKey();
                                WeChatPushUtil.weChatPush(scKey, "基金净值", fundText);
                            }
                        }
                        fundPush.setLastActualTime(now);
                        fundPushService.update(fundPush);
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取基金净值失败", e);
        }
    }

    private String getActualFundText(String result) {
        JSONObject jsonObject = JSON.parseObject(result).getJSONObject("Datas");
        String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if (now.equals(jsonObject.getString("FSRQ"))) {
            String rzdf = (String) jsonObject.get("RZDF");
            String shortname = (String) jsonObject.get("SHORTNAME");
            String res = "截至" + now + "," + shortname + "  实际净值为" + String.format("%.2f", new Double(rzdf)).toString().replace("-", "负") + "%";

            log.info(res);
            return res;
        }
        return null;
    }

    //@Scheduled(cron = "0/20 45-59 14 ? * 1-5")
    public void fundPushT() {
        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost httpGet = new HttpPost(
                    "https://jijinbaapi.eastmoney.com/gubaapi/v3/read/Article/Post/UserPostList.ashx");
            List<NameValuePair> list_0 = new ArrayList<NameValuePair>();
            list_0.add(new BasicNameValuePair("deviceid", "837EC5754F503CFAAEE0929FD48974E7"));
            list_0.add(new BasicNameValuePair("ps", "20"));
            list_0.add(new BasicNameValuePair("plat", "Wap"));
            list_0.add(new BasicNameValuePair("product", "Fund"));
            list_0.add(new BasicNameValuePair("version", "201"));
            list_0.add(new BasicNameValuePair("uid", "5604094139349852"));
            list_0.add(new BasicNameValuePair("p", "1"));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list_0, "utf8");
            httpGet.setEntity(entity);
            HttpResponse httpResponse = client.execute(httpGet);
            String result = EntityUtils.toString(httpResponse.getEntity(), "utf8");

            String r = "";
            JSONArray jsonObjects = JSON.parseObject(result).getJSONArray("re");
            for (int i = 0; i < jsonObjects.size(); i++) {
                JSONObject job = jsonObjects.getJSONObject(i);
                String post_title = job.getString("post_title");
                String post_id = job.getString("post_id");
                String post_content = job.getString("post_content");
                String post_url = "https://fundbarmob.eastmoney.com/index.html#aid=" + post_id
                        + "&goPage=articleView&lastPage=personDetailView";
                if (!ids.contains(post_id)) {
                    ids.add(post_id);
                    r += "[" + post_title + "](" + post_url + ")  \n  \n";
                    r += post_content + "  \n  \n";
                    r += "----------------------------    \n  \n";
                }

            }
            if (!StringUtils.isEmpty(r)) {
                //http://sc.ftqq.com/?c=code#
                HttpPost httpPost = new HttpPost(
                        "https://sc.ftqq.com/SCU12427T981f7b2e2ed51c827ba5ffa7f65f18d559c5dc3614d0d.send");
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add(new BasicNameValuePair("text", "最新主题"));
                list.add(new BasicNameValuePair("desp", r));
                UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(list, "utf8");
                httpPost.setEntity(entity1);
                client.execute(httpPost);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}