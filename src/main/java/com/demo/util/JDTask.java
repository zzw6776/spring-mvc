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

    private boolean run = true;

    @Scheduled(cron = "0/15 * * * * ?")
    public void test() {
        if (run) {
            String result = HttpClientUtil.get("https://c0.3.cn/stock?skuId=7428766&area=15_1213_3411_52667&cat=1,1,1&buyNum=1&extraParam=%7B%22originid%22:%221%22%7D",keyValueMap.get("JDCookie"));
            try {
                JSONObject jsonObject = JSON.parseObject(result);
                int intValue = jsonObject.getJSONObject("stock").getIntValue("StockState");
                log.info(intValue);
                if (intValue != 34) {
                    WeChatPushUtil.weChatPush(WeChatPushUtil.MY_SCKEY, "有货啦", "有货啦");
                    //勾选购物车商品
                    Map<String, String> selectParam = HttpClientUtil.toMap("pid:7428766\n" +
                            "ptype:1");
                    String selectResult = HttpClientUtil.post("https://cart.jd.com/selectItem.action", selectParam, keyValueMap.get("JDCookie"));
                    if (JSON.parseObject(selectResult).getInteger("isLogin") != 1) {
                        WeChatPushUtil.weChatPush(WeChatPushUtil.MY_SCKEY, "JD登录失效", "JD登录失效");
                        run = false;
                    } else {
                        //下单
                        Map<String, String> submitParam = HttpClientUtil.toMap("overseaPurchaseCookies:\n" +
                                "submitOrderParam.sopNotPutInvoice:false\n" +
                                "submitOrderParam.trackID:15tAsNbWfZUQ7t-5kK0BJLGVWdBlaNNBker_CHrm7gEq2fc_rMO0Q7uXYY4jxmeBZB0yfLFKnlzIDTTfQGB_cto2DEkHx7H8RHRoDogEuJtLRc5FCl9O5lm8xTh1769fh\n" +
                                "submitOrderParam.ignorePriceChange:0\n" +
                                "submitOrderParam.btSupport:0\n" +
                                "submitOrderParam.eid:UIYZNIFSFKSWYWULEVAAM26BGU57K46OMP7FRXDXTUTUZKJNPIY2DL7GMHKMLNIGT62SU4STSIHMYNXTMEUXCCMIUQ\n" +
                                "submitOrderParam.fp:67394e74e4eaa61cbab24cdb4d448e90\n" +
                                "riskControl:D0E404CB705B9732A413F41984FFC1121390510A21284B28F2CBEC1FDCA75647");
                        String submitResult = HttpClientUtil.post("https://trade.jd.com/shopping/order/submitOrder.action", selectParam, keyValueMap.get("JDCookie"));
                        if (JSON.parseObject(submitResult).getBoolean("success")) {
                            WeChatPushUtil.weChatPush(WeChatPushUtil.MY_SCKEY, "JD下单成功", "JD下单成功");
                            run = false;
                        } else {
                            WeChatPushUtil.weChatPush(WeChatPushUtil.MY_SCKEY, "JD下单失败", submitResult);
                        }
                    }
                }
            } catch (Exception e) {
                log.error(TypeUtil.getErrorInfoFromException(e));
            }
        }

    }

    public static void main(String[] args) {
        JDTask jdTask = new JDTask();
        jdTask.test();
    }
}
