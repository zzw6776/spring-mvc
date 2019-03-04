package com.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

@Component
@EnableScheduling
@Log4j
public class BankPushTask {


    private List<String> ids = Arrays.asList("DK6LK180008","DK8LK180009","DK7LK180007","DK3LK180003");

    public static final String GET_BANK_URL = "https://bank.pingan.com.cn/rmb/bron/ibank/cust/bron-ibank-pd/dailyprofit/queryFinaDetailNoHold.do?prdCode=ID";




    public static void main(String[] args) {
    }



    @Scheduled(cron = "1/5 * * * * ?")
    public void push() {
        log.info("理财监控开始");
        for (String code : ids) {
            log.info("理财监控开始:" + code);
            String result = HttpClientUtil.get(GET_BANK_URL.replace("ID", code));
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSON.parseObject(result).getJSONObject("data");
                String prdSaleStatus = jsonObject.getString("prdSaleStatus");
                String name = jsonObject.getString("prdName");
                String remainQuota = jsonObject.getString("remainQuota");
                String message = name + "~~~~~~" + prdSaleStatus + "~~~~~" + remainQuota;
                log.info(message);
                if (!prdSaleStatus.equals("SALE_OUT")) {

                    WeChatPushUtil.weChatPush(WeChatPushUtil.MY_SCKEY, "理财监控", message);
                }
            }
        }
    }



}