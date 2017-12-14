package com.demo.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

/**
 * httpClient工具类
 */
@Log4j
public class HttpClientUtil {
    public static String post(String url, Map<String, String> value) {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (String key : value.keySet()) {
                list.add(new BasicNameValuePair(key, value.get(key)));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf8");
            httpPost.setEntity(entity);
            httpResponse = client.execute(httpPost);
            return EntityUtils.toString(httpResponse.getEntity(), "utf8");
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
        return null;
    }

    public static  String get(String url, Map<String, String> value) {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            String v = "";
            for (String key : value.keySet()) {
                v = v + "&" + key + "=" + value.get(key);
            }
            if (!StringUtils.isEmpty(v)) {
                v = "?" + v.substring(1, v.length());
                url += v;
            }
            httpResponse = client.execute(httpGet);
            return EntityUtils.toString(httpResponse.getEntity(), "utf8");
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
        return null;
    }

    public static  String get(String url) {
       return get(url, new HashMap<>());
    }
}



