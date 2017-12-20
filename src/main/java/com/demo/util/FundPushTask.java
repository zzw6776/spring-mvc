package com.demo.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.demo.hibernate.entity.FundPush;
import com.demo.hibernate.entity.User;
import com.demo.service.impl.FundPushServiceImpl;
import com.demo.service.impl.UserServiceImpl;
import lombok.extern.java.Log;
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
@Log
public class FundPushTask {

    @Autowired
    private FundPushServiceImpl fundPushService;
    @Autowired
    private UserServiceImpl userService;
    @Resource(name = "keyValue")
    Map<String, String> keyValueMap;

    private Set<String> ids = new HashSet<>();

    public static final String GET_ESTIMATE_FUND_URL = "http://fundgz.1234567.com.cn/js/ID.js";

    public static final String GET_ACTUAL_FUND_URL = "https://fundmobapi.eastmoney.com/FundMApi/FundBaseTypeInformation.ashx?FCODE=ID&deviceid=Wap&plat=Wap&product=EFund&version=2.0.0";

    @Scheduled(cron = "0 45 14,15 ? * 1-5")
    public void fundEstimatePush() {
        if (!keyValueMap.get("FundEstimateSwitch").equals("true")) {
            return;
        }
        List<FundPush> fundList = fundPushService.findAll();
        for (FundPush fundPush : fundList) {
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

    @Scheduled(cron = "0/20 * 18-23 ? * 1-5")
    public void fundActualPush() {
        if (!keyValueMap.get("FundActualSwitch").equals("true")) {
            return;
        }
        List<FundPush> fundList = fundPushService.findAll();
        String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        for (FundPush fundPush : fundList) {
            String fundId = fundPush.getFundId();
            String result = HttpClientUtil.get(GET_ACTUAL_FUND_URL.replace("ID", fundId));
            if (!now.equals(fundPush.getLastActualTime())) {
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
    }

    private String getActualFundText(String result) {
        JSONObject jsonObject = JSON.parseObject(result).getJSONObject("Datas");
        String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if (now.equals(jsonObject.getString("FSRQ")) ) {
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

    public static void main(String[] args) throws IOException {
        FundPushTask fundPushTask = new FundPushTask();

    }
}