package com.demo.util;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j;

/**
 * 微信推送类
 */
@Log4j
public class WeChatPushUtil {
    private static final String URL = "https://sc.ftqq.com/url.send";

    public static final String MY_SCKEY = "SCU12427T981f7b2e2ed51c827ba5ffa7f65f18d559c5dc3614d0d";

    /**
     * server酱的微信推送系统
     *
     * @param scKey
     * @param title 标题
     * @param desp  内容
     */
    public static void weChatPush(String scKey, String title, String desp) {
        log.info("推送"+title+"~~~"+desp);
        Map<String, String> value = new HashMap<>();
        value.put("text", title);
        value.put("desp", desp);
        HttpClientUtil.post(URL.replace("url", scKey), value);

    }
}
