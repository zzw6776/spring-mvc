package com.demo.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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

    private Set<String> ids = new HashSet<>();

    @Scheduled(cron = "0 45 14,15 ? * 1-5")
    public void fundPush() {
        try {
            HttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("http://fundgz.1234567.com.cn/js/004505.js");
            HttpResponse httpResponse = null;
            httpResponse = client.execute(httpGet);
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
                String res = "截至" + jzrq + "," + name + "为" + gszzl.replace("-", "负");
                //http://sc.ftqq.com/?c=code#
                HttpPost httpPost = new HttpPost(
                    "https://sc.ftqq.com/SCU12427T981f7b2e2ed51c827ba5ffa7f65f18d559c5dc3614d0d.send");
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add(new BasicNameValuePair("text", "基金"));
                list.add(new BasicNameValuePair("desp", res));

                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf8");
                httpPost.setEntity(entity);
                client.execute(httpPost);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Scheduled(cron = "0 45 14 ? * 1-5")
    public void fundPushT() {
        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost httpGet = new HttpPost("https://jijinbaapi.eastmoney.com/gubaapi/v3/read/Article/Post/UserPostList.ashx");
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
            HttpResponse  httpResponse = client.execute(httpGet);
            String result = EntityUtils.toString(httpResponse.getEntity(), "utf8");

            String r ="";
            JSONArray jsonObjects = JSON.parseObject(result).getJSONArray("re");
            for(int i=0;i<jsonObjects.size();i++){
                JSONObject job = jsonObjects.getJSONObject(i);
                String post_title = job.getString("post_title");
                String post_id = job.getString("post_id");
                String post_content = job.getString("post_content");
                String post_url = "https://fundbarmob.eastmoney.com/index.html#aid=" + post_id
                    + "&goPage=articleView&lastPage=personDetailView";
                if (!ids.contains(post_id)) {
                    ids.add(post_id);
                    r += "["+post_title+"]("+post_url+")  \n  \n";
                    r += post_content+  "  \n  \n";
                    r += "----------------------------    \n  \n";
                }

            }
                //http://sc.ftqq.com/?c=code#
                HttpPost httpPost = new HttpPost(
                    "https://sc.ftqq.com/SCU12427T981f7b2e2ed51c827ba5ffa7f65f18d559c5dc3614d0d.send");
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add(new BasicNameValuePair("text", "最新主题"));
                list.add(new BasicNameValuePair("desp", r));

                UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(list, "utf8");
                httpPost.setEntity(entity1);
                client.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        FundPushTask fundPushTask = new FundPushTask();
        fundPushTask.fundPushT();

    }
}