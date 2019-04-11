package com.demo.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Test2 implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("start thread");

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Test.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        System.out.println("end thread");
    }
}
