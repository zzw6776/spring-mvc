package com.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
@EnableScheduling
@Log4j
public class JDTask {

    @Resource(name = "keyValue")
    Map<String, String> keyValueMap;

    @Scheduled(cron = "0/30 * * * * ?")
    public void test() {
        String result = HttpClientUtil.get("https://c0.3.cn/stock?skuId=6023789&area=15_1213_3411_52667&cat=1,1,1&buyNum=1&extraParam=%7B%22originid%22:%221%22%7D");
        try {
            JSONObject jsonObject = JSON.parseObject(result);
            int intValue = jsonObject.getJSONObject("stock").getIntValue("StockState");
            log.info(intValue);
            if (intValue != 34) {
                WeChatPushUtil.weChatPush("SCU12427T981f7b2e2ed51c827ba5ffa7f65f18d559c5dc3614d0d","有货啦",System.currentTimeMillis()+"有货啦");
                //勾选购物车商品

            }
        } catch (Exception e) {
            log.error(TypeUtil.getErrorInfoFromException(e));
        }


    }
}
