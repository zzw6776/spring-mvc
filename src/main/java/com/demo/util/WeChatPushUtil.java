package com.demo.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

@Log4j
public class WeChatPushUtil {
    private static final String URL = "https://sc.ftqq.com/url.send";

    /**
     * server酱的微信推送系统
     *
     * @param scKey
     * @param title 标题
     * @param desp  内容
     */
    public static void weChatPush(String scKey, String title, String desp) {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        try {
            //http://sc.ftqq.com/?c=code#
            HttpPost httpPost = new HttpPost(URL.replace("url",scKey));
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("text", "title"));
            list.add(new BasicNameValuePair("desp", desp));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf8");
            httpPost.setEntity(entity);
            client.execute(httpPost);
        } catch (Exception e) {
            log.error(e);
        } finally {
            try {
                if (null != httpResponse) {
                    httpResponse.close();
                }
                client.close();
            } catch (IOException e) {
                log.error(e);
            }
        }

    }
}
