package com.demo.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

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
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();//设置请求和传输超时时间
            httpPost.setConfig(requestConfig);
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

            List<String> params = new ArrayList<>();

            for (String key : value.keySet()) {
                params.add(key + "=" + value.get(key));
            }
            if (!CollectionUtils.isEmpty(params)) {
                String v = "?" + StringUtils.join(params, "&");
                url += v;
            }
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();//设置请求和传输超时时间
            httpGet.setConfig(requestConfig);
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

    public static void main(String[] args) {
        get("www.baidu.com");
        Map<String, String> map = new HashMap<>();
        map.put("a", "a");
        map.put("b", "b");
        get("www.baidu.com", map);
    }
}



