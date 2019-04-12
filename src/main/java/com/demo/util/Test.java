package com.demo.util;

import java.util.*;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("123");

    }

    public static void run() throws InterruptedException {
        List<String> itemId = Arrays.asList("33940","33941","33942","33943","33944","33945","33946","33947","33948","33949","33950","33951","33952","33953","33954","33955","33956","33957","33959","33963","33965","33967","33971","33973","33974","33975","33976","33980");
        for (String s : itemId) {
            hahaha(s);
            double time = Math.random() * (3600000 - 1800000) + 1800000;
            System.out.println("休息"+time);
            Thread.sleep((long) time);

        }
    }

    private static void hahaha(String itemId) throws InterruptedException {
        Map<String, String> head = HttpClientUtil.toMap("Accept: application/xml, text/xml, */*; q=0.01\n"
            + "Content-Type: application/x-www-form-urlencoded; charset=UTF-8\n"
            + "Cookie: _csrf=5bc5248414517005b6874deb85c92f9e2a02dc76b18ef206fe8ea6c567e14283a%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%22NKyEHoma7OvgFgT9s4qRX4pZ5U-E7uB0%22%3B%7D; Hm_lvt_fc82d39d0aabdf72237c051c09b9428e=1555040372; PHPFRONTSESSID=vub75hleiht3ei68uu12mtfqo2; contact=%5B%7B%22id%22%3A%221%22%2C%22teacher_name%22%3A%22%5Cu9ea6%5Cu80fd%5Cu7f51%5Cu54a8%5Cu8be21%22%2C%22qq_number%22%3A%222924134909%22%7D%2C%7B%22id%22%3A%222%22%2C%22teacher_name%22%3A%22%5Cu9ea6%5Cu80fd%5Cu7f51%5Cu54a8%5Cu8be22%22%2C%22qq_number%22%3A%221283346554%22%7D%5D; noticeNumber=2; Hm_lpvt_fc82d39d0aabdf72237c051c09b9428e=1555040408\n"
            + "Host: zgs.cjnep.net\n"
            + "Origin: http://zgs.cjnep.net\n"
            + "Pragma: no-cache\n"
            + "Referer: http://zgs.cjnep.net/lms/web/course/20?itemid=13549\n"
            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) "
            + "Chrome/73.0.3683.86 Safari/537.36\n"
            + "X-Requested-With: XMLHttpRequest");

        String getUrl = "http://zgs.cjnep.net/lms/web/course/20?itemid="+itemId;
        String getResult = HttpClientUtil.get(getUrl,new HashMap<>(),head);
        int historyidIndex = getResult.indexOf("historyid");
        String historyId = getResult.substring(historyidIndex + 10, historyidIndex + 16);

        System.out.println(historyId);
        Map<String, String> param = HttpClientUtil.toMap(
            "userId: 450329199801231727\n"
            + "courseId: 18\n"
                //第一
            + "scoId: "+itemId+"\n"
                //第二
            + "historyId: "+historyId+"\n"
            + "hasCheckOne: false\n"
            + "hasCheckTwo: false\n"
            + "hasCheckThree: true\n"
            + "firstUpdate: false");

        //第三
        Double totalTime = (Math.random() * (2500 - 2000) + 2000);

        Integer currentTime = 0;
        Integer addTime = 60;

        while (currentTime < totalTime) {
            System.out.println(itemId+"~~~~~~~~~~"+currentTime+"~~~~~~~~~~~"+addTime);

            param.put("totalTime", totalTime.toString());
            if (!addTime.equals(60)) {
                param.put("finished", "1");
            } else {
                param.put("currentTime", currentTime.toString());
            }
            param.put("addTime", addTime.toString());
            String post = HttpClientUtil.post("http://zgs.cjnep.net/lms/web/timing/updstatus", param, head);
            Thread.sleep((long)addTime*1000);
            System.out.println("~~~~~~~~~"+post);
            currentTime += addTime;
            if (currentTime > totalTime) {
                addTime = Math.toIntExact(Math.round(totalTime - (currentTime  - 60)));
            }
        }
    }
}
