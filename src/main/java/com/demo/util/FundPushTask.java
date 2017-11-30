package com.demo.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class FundPushTask {

    @Scheduled(cron = "0 30 09 * * ?")
    public void test1(){
        try {
            jijin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 30 11 * * ?")
    public void test2(){
        try {
            jijin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 40 14 * * ?")
    public void test3(){
        try {
            jijin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Scheduled(cron = "0 05 15 * * ?")
    public void test4(){
        try {
            jijin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 22 * * ?")
    public void test5(){
        try {
            jijin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public  void jijin() throws ClientProtocolException, IOException {
        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://fundgz.1234567.com.cn/js/004505.js");
        HttpResponse httpResponse = client.execute(httpGet);
        String result = EntityUtils.toString(httpResponse.getEntity(), "utf8");
        String pattern = "jsonpgz\\((.*)\\);";
        // 创建 Pattern 对象
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(result);
        if (m.find()) {
            JSONObject jsonObject = JSON.parseObject(m.group(1));
            String name = (String)jsonObject.get("name");
            String gszzl = (String)jsonObject.get("gszzl");
            String jzrq = (String)jsonObject.get("jzrq");
            String res = "截至"+jzrq+","+name + "为" + gszzl.replace("-","负");
            //http://sc.ftqq.com/?c=code#
            HttpPost httpPost = new HttpPost(
                "https://sc.ftqq.com/SCU12427T981f7b2e2ed51c827ba5ffa7f65f18d559c5dc3614d0d.send");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("text", "基金"));
            list.add(new BasicNameValuePair("desp", res));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"utf8");
            httpPost.setEntity(entity);
            client.execute(httpPost);
        }
    }

    public static void main(String[] args) throws IOException {
        FundPushTask fundPushTask = new FundPushTask();
        fundPushTask.jijin();

    }
}