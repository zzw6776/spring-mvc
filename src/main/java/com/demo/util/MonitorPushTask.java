package com.demo.util;

import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Component
@EnableScheduling
@Log4j
public class MonitorPushTask {


    private List<String> surfaceBookIds = Arrays.asList("mic2173","mic2188");

    private List<String> surfaceProIds = Arrays.asList("mic2408");


    public static final String GET_SURFACE_BOOK_URL = "https://www.microsoftstore.com.cn/refurbishedsurface/certified-refurbished-surface-book123/p/ID";

    public static final String GET_SURFACE_PRO_URL
        = "https://www.microsoftstore.com.cn/refurbishedsurface/certified-refurbished-surface-pro/p/ID";

    //public static void main(String[] args) {
    //    MonitorPushTask monitorPushTask = new MonitorPushTask();
    //    monitorPushTask.push1();
    //    monitorPushTask.push2();
    //}


    @Scheduled(cron = "1/5 * * * * ?")
    public void push1() {
        log.info("微软库存监控开始");
        for (String code : surfaceBookIds) {
            String result = HttpClientUtil.get(GET_SURFACE_BOOK_URL.replace("ID", code));
            if (!StringUtils.isEmpty(result)) {
                int index = result.indexOf("加入购物车");

                if (index>-1) {
                    WeChatPushUtil.weChatPush(WeChatPushUtil.MY_SCKEY, code+"有货啦", code+"有货啦");
                }
            }
            int i = result.indexOf("到货通知");
            if (i<0) {
                WeChatPushUtil.weChatPush(WeChatPushUtil.MY_SCKEY, code+"出错啦", code+"出错啦");
            }
        }
    }

    @Scheduled(cron = "1/5 * * * * ?")
    public void push2() {
        log.info("微软库存监控开始");
        for (String code : surfaceProIds) {
            String url = GET_SURFACE_PRO_URL.replace("ID", code);
            String result = HttpClientUtil.get(url);
            if (!StringUtils.isEmpty(result)) {
                int index = result.indexOf("加入购物车");

                if (index>-1) {
                    WeChatPushUtil.weChatPush(WeChatPushUtil.MY_SCKEY, code+"有货啦", code+"有货啦");
                }
            }
            int i = result.indexOf("到货通知");
            if (i < 0) {
                WeChatPushUtil.weChatPush(WeChatPushUtil.MY_SCKEY, code+"出错啦", code+"出错啦");
            }
        }
    }

}