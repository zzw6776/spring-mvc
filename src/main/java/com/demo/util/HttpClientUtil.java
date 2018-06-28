package com.demo.util;

import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * httpClient工具类
 */
@Log4j
public class HttpClientUtil {

    static CloseableHttpClient client = HttpClients.createDefault();

    public static Map<String, String> toMap(String paramString) {
        String[] split = paramString.split("\\n");
        Map<String, String> param = new HashMap<>();
        for (String s : split) {
            int i = s.indexOf(":");
            param.put(s.substring(0, i), s.substring(i + 1));
        }
        return param;
    }

    public static void main(String[] args) throws IOException {
        HttpGet httpGet = new HttpGet("https://authcode.jd.com/verify/image?a=1&acid=634a3a3d-2a52-45ea-8bdd-3b474b1c9824&uid=634a3a3d-2a52-45ea-8bdd-3b474b1c9824&yys=1529925079277");
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();//设置请求和传输超时时间
        httpGet.setConfig(requestConfig);
         httpGet.setHeader("Referer", "https://passport.jd.com/new/login.aspx");
        CloseableHttpResponse httpResponse = client.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        System.out.println("ContentType:"+httpEntity.getContentType().getValue());
        InputStream inputStream=httpEntity.getContent();
        FileUtils.copyInputStreamToFile(inputStream,new File("/Users/zzw/Downloads/1.png"));
        System.out.println(EntityUtils.toString(httpEntity, "utf8"));
    }


    public static String post(String url, Map<String, String> value) {
        return post(url, value, new HashMap<>());
    }

    public static String post(String url, Map<String, String> value, Map<String,String> header) {
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
            for (String headKey : header.keySet()) {
                httpPost.addHeader(headKey,header.get(headKey));
            }
            httpResponse = client.execute(httpPost);
            return EntityUtils.toString(httpResponse.getEntity(), "utf8");
        } catch (Exception e) {
            log.error(TypeUtil.getErrorInfoFromException(e));

        }
        return null;
    }

    public static String get(String url, Map<String, String> value) {
        return get( url, value, new HashMap<>());
    }

    public static String get(String url, Map<String, String> value, Map<String,String> header) {
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
            for (String headKey : header.keySet()) {
                httpGet.addHeader(headKey,header.get(headKey));
            }
            httpResponse = client.execute(httpGet);
            return EntityUtils.toString(httpResponse.getEntity(), "utf8");
        } catch (Exception e) {
            log.error(TypeUtil.getErrorInfoFromException(e));
        }
        return null;
    }

    public static HttpResponse getForResponse(String url, Map<String, String> value, Map<String,String> header) {
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
            for (String headKey : header.keySet()) {
                httpGet.addHeader(headKey,header.get(headKey));
            }
            httpResponse = client.execute(httpGet);
            return httpResponse;
        } catch (Exception e) {
            log.error(TypeUtil.getErrorInfoFromException(e));
        }
        return null;
    }

    public static String get(String url) {
        return get(url, new HashMap<>());
    }


}



