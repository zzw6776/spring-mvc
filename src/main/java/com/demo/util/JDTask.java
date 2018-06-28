package com.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.hibernate.dao.KeyValueDao;
import com.demo.hibernate.entity.KeyValue;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
@EnableScheduling
@Log4j
public class JDTask {

    @Resource(name = "keyValue")
    Map<String, String> keyValueMap;

    @Autowired
    KeyValueDao keyValueDao;

    @Autowired
    JdUtils jdUtils;

    @Scheduled(cron = "0/5 * * * * ?")
    public void monitorAndOrder() {
        String isRun = keyValueMap.get("JDRunStatus");
        if (isRun.equals("true")) {
            String jdIdemId = keyValueMap.get("JDIdemId");
            String[] ids = jdIdemId.split(",");
            for (String id : ids) {
                jdUtils.monitorAndOrder(id);
            }
        }
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void monitorLogistics() {
        jdUtils.monitorLogistics("76602995224");
    }

    public static void main(String[] args) {
        Map<String, Integer> temp = new HashMap<>();
        Integer i = 0;
        temp.put("i", i);
        Integer p = temp.get("i");
        p++;
        System.out.println(temp.get("i"));
    }
}
