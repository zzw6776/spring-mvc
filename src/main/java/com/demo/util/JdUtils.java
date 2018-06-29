package com.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.hibernate.dao.KeyValueDao;
import com.demo.hibernate.entity.KeyValue;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Log4j
public class JdUtils {

    @Resource(name = "keyValue")
    Map<String, String> keyValueMap;

    @Autowired
    KeyValueDao keyValueDao;

    Map<String, Integer> logisticsTemp = new HashMap<>();

    //跟踪物流
    public void monitorLogistics(String orderId) {
        //没有清理,可能有内存溢出问题
        Integer index = logisticsTemp.get(orderId);
        if (null == index) {
            index = 0;
            logisticsTemp.put(orderId, index);
        }
        try {
            String result = HttpClientUtil.get(
                "https://details.jd.com/lazy/getOrderTrackInfoMultiPackage.action?orderId=" + orderId);
            JSONObject jsonObject = JSON.parseObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("multiPackageTrackInfoList").getJSONObject(0).getJSONObject(
                "trackGroupInfo").getJSONArray("orderTrackShowList");
            if (jsonArray.size() > index) {
                //Integer为final,无法进行引用更新,所以需要重新put
                logisticsTemp.put(orderId, jsonArray.size());
                String message = "";
                for (int i = jsonArray.size()-1 ; i >= 0; i--) {
                    message += jsonArray.getJSONObject(i).getString("Content")+"  \n";
                }
                WeChatPushUtil.weChatPush(WeChatPushUtil.MY_SCKEY, "订单" + orderId + "物流已更新",message);
            }
        } catch (Exception e) {
            log.error(TypeUtil.getErrorInfoFromException(e));
            //偷懒,认为只要报错就是没登录引起的,出问题再说吧
            retryLogin();
        }

    }

    private void retryLogin() {
        System.out.println("尝试登陆");
        //尝试登陆
        Integer time = 3;
        Boolean isLogin = false;
        while (!isLogin&&time>0) {
            time--;
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e1) { }
            isLogin = login();
        }
    }

    //自动监控库存下单,下单的前提为已经存在购物车
    public void monitorAndOrder(String jdId) {
            String result = HttpClientUtil.get("https://c0.3.cn/stock?skuId=" + jdId
                + "&area=15_1213_3411_52667&cat=1,1,1&buyNum=1&extraParam=%7B%22originid%22:%221%22%7D");
            try {
                JSONObject jsonObject = JSON.parseObject(result);
                int intValue = jsonObject.getJSONObject("stock").getIntValue("StockState");
                log.info(intValue);
                if (intValue != 34) {
                    WeChatPushUtil.weChatPush(WeChatPushUtil.MY_SCKEY, "有货啦", "有货啦");
                    //勾选购物车商品
                    Map<String, String> selectParam = HttpClientUtil.toMap("pid:" + jdId + "\n" +
                        "ptype:1");
                    String selectResult = HttpClientUtil.post("https://cart.jd.com/selectItem.action", selectParam);
                    if (JSON.parseObject(selectResult).getInteger("isLogin") != 1) {
                        retryLogin();
                    } else {
                        //下单
                        Map<String, String> submitParam = HttpClientUtil.toMap("overseaPurchaseCookies:\n" +
                            "submitOrderParam.sopNotPutInvoice:false\n" +
                            "submitOrderParam"
                            + ".trackID:15tAsNbWfZUQ7t-5kK0BJLGVWdBlaNNBker_CHrm7gEq2fc_rMO0Q7uXYY4jxmeBZB0yfLFKnlzIDTTfQGB_cto2DEkHx7H8RHRoDogEuJtLRc5FCl9O5lm8xTh1769fh\n"
                            +
                            "submitOrderParam.ignorePriceChange:0\n" +
                            "submitOrderParam.btSupport:0\n" +
                            "submitOrderParam"
                            + ".eid:UIYZNIFSFKSWYWULEVAAM26BGU57K46OMP7FRXDXTUTUZKJNPIY2DL7GMHKMLNIGT62SU4STSIHMYNXTMEUXCCMIUQ\n"
                            +
                            "submitOrderParam.fp:67394e74e4eaa61cbab24cdb4d448e90\n" +
                            "riskControl:D0E404CB705B9732A413F41984FFC1121390510A21284B28F2CBEC1FDCA75647");
                        String submitResult = HttpClientUtil.post(
                            "https://trade.jd.com/shopping/order/submitOrder.action", selectParam);
                        if (JSON.parseObject(submitResult).getBoolean("success")) {
                            WeChatPushUtil.weChatPush(WeChatPushUtil.MY_SCKEY, "JD下单成功", "JD下单成功");
                            closeRun();
                        } else {
                            WeChatPushUtil.weChatPush(WeChatPushUtil.MY_SCKEY, "JD下单失败", submitResult);
                        }
                    }
                }
            } catch (Exception e) {
                log.error(TypeUtil.getErrorInfoFromException(e));
            }

    }


    public synchronized boolean login() {

        //判断是否已登录
        String verify = HttpClientUtil.get("https://home.jd.com/getUserVerifyRight.action");
        if (verify.length() < 200) {
            return true;
        }

        try {
            Document doc = Jsoup.parse(HttpClientUtil.get("https://passport.jd.com/new/login.aspx"));
            Thread.sleep(1000);
            Elements elements = doc.select("form[id=formlogin] input[type=hidden]");
            Map<String, String> map = new HashMap<String, String>();
            String k, v;
            for (Element input : elements) {
                k = input.attr("name");
                v = input.attr("value");
                if (StringUtils.isNotBlank(k)) {
                    map.put(k, v);
                    System.out.println(input);
                }
            }
            map.put("loginname", keyValueMap.get("JDAccount"));
            map.put("nloginpwd", keyValueMap.get("JDPassword"));
            map.put("eid",
                "5IHVYPPYV6J2MMNWCTU4D36JSWJ2PNNJPI7ZKCQUCJ5GZATRWVMJOWR273DE3FE62RFTWKT2W3FOCYTZZRKOIWHPLY");
            map.put("fp", "6db6dd56a3c895c6a6e18e6863fbc5e9");

            String result = HttpClientUtil.get(
                "https://seq.jd.com/jseqf.html?bizId=passport_jd_com_login_pc&platform=js&version=1");
            Thread.sleep(1000);
            String pattern = "sessionId=.+_jdtdseq_config_data";
            // 创建 Pattern 对象
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(result);
            if (m.find()) {
                System.out.println(m.group().substring(11, 29));
                map.put("seqSid", m.group().substring(11, 29));
            }
            String url = "https://passport.jd.com/uc/loginService?";
            url = url + "&uuid=" + map.get("uuid") + "&r=" + Math.random() + "&version=2015";
            System.out.println("url: " + url);
            String post = HttpClientUtil.post(url, map);
            System.out.println(post);
            String ans = decodeUnicode(post);
            System.out.println(ans);
            if (ans.contains("emptyAuthcode")) {
                String authUrl = "https://authcode.jd.com/verify/image";
                Map<String, String> param = new HashMap<>();
                param.put("a", "1");
                param.put("acid", map.get("uuid"));
                param.put("uid", map.get("uuid"));
                param.put("yys", String.valueOf(System.currentTimeMillis()));
                Map<String, String> header = new HashMap<>();
                header.put("Referer", "https://passport.jd.com/new/login.aspx");
                HttpResponse response = HttpClientUtil.getForResponse(authUrl, param, header);
                String authCode = RuoKuai.createByPost(keyValueMap.get("RKAccount"), keyValueMap.get("RKPassword"),
                    response.getEntity().getContent());
                System.out.println(authCode);
                map.put("authcode", authCode);
                ans = decodeUnicode(HttpClientUtil.post(url, map));
                log.info(ans);
            }
            if (ans.contains("success")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error(TypeUtil.getErrorInfoFromException(e));
            return false;
        }

    }

    public void closeRun() {
        KeyValue obj = new KeyValue();
        obj.setKey("JDRunStatus");
        obj.setValue("false");
        keyValueDao.update(obj);
    }

    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                    "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char)value);
                } else {
                    if (aChar == 't') { aChar = '\t'; } else if (aChar == 'r') { aChar = '\r'; } else if (aChar
                        == 'n') { aChar = '\n'; } else if (aChar
                        == 'f') { aChar = '\f'; }
                    outBuffer.append(aChar);
                }
            } else { outBuffer.append(aChar); }
        }
        return outBuffer.toString();
    }
}
