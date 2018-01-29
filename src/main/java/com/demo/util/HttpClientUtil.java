package com.demo.util;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * httpClient工具类
 */
@Log4j
public class HttpClientUtil {

    public static Map<String, String> toMap(String paramString) {
        String[] split = paramString.split("\\n");
        Map<String, String> param = new HashMap<>();
        for (String s : split) {
            int i = s.indexOf(":");
            param.put(s.substring(0, i), s.substring(i + 1));
        }
        return param;
    }

    public static void main(String[] args) {
        toMap("outSkus:\n" +
                "pid:6023789\n" +
                "ptype:1\n" +
                "packId:0\n" +
                "targetId:0\n" +
                "promoID:0\n" +
                "locationId:15-1213-3411-52667\n" +
                "t:0");
    }


    public static String post(String url, Map<String, String> value) {
        return post(url, value, "");
    }

    public static String post(String url, Map<String, String> value, String cookieString) {
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
            httpPost.setHeader("Cookie", cookieString);

            httpResponse = client.execute(httpPost);
            return EntityUtils.toString(httpResponse.getEntity(), "utf8");
        } catch (Exception e) {
            log.error(TypeUtil.getErrorInfoFromException(e));
        } finally {
            try {
                if (null != httpResponse) {
                    httpResponse.close();
                }
                client.close();
            } catch (IOException e) {
                log.error(TypeUtil.getErrorInfoFromException(e));
            }
        }
        return null;
    }

    public static String get(String url, Map<String, String> value) {
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
            log.error(TypeUtil.getErrorInfoFromException(e));
        } finally {
            try {
                if (null != httpResponse) {
                    httpResponse.close();
                }
                client.close();
            } catch (IOException e) {
                log.error(TypeUtil.getErrorInfoFromException(e));
            }
        }
        return null;
    }

    public static String get(String url) {
        return get(url, new HashMap<>());
    }


}



