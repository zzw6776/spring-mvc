package com.demo.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;

import com.demo.hibernate.dao.FundPushDao;
import com.demo.hibernate.entity.FundPush;
import com.demo.util.FundPushTask;
import com.demo.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FundPushServiceImpl {
    @Autowired
    private FundPushDao fundPushDao;

    public List<FundPush> findAll() {
        return fundPushDao.findAll();
    }

    public void updateAndAdd(String fundId,String account) {
        String result = HttpClientUtil.get(FundPushTask.GET_ACTUAL_FUND_URL.replace("ID", fundId));
        String shortname = JSON.parseObject(result).getJSONObject("Datas").getString("SHORTNAME");
        FundPush query = new FundPush();
        query.setFundId(fundId);
        FundPush fundPush = fundPushDao.selectByUnique(query);
        if (null != fundPush) {
            Set<String> set = new HashSet<String>(Arrays.asList(fundPush.getAccounts().split(",")));
            set.add(account);
            fundPush.setAccounts(StringUtils.join(set.toArray(), ","));
            fundPush.setFundName(shortname);
        } else {
            query.setAccounts(account);
            query.setFundName(shortname);
            fundPushDao.save(query);
        }
    }

    public void updateAndDelete(String fundId,String account) {
        FundPush query = new FundPush();
        query.setFundId(fundId);
        FundPush fundPush = fundPushDao.selectByUnique(query);
        if (null != fundPush) {
            Set<String> set = new HashSet<String>(Arrays.asList(fundPush.getAccounts().split(",")));
            set.remove(account);
            fundPush.setAccounts(StringUtils.join(set.toArray(), ","));
            if (set.size() == 0) {
                fundPushDao.delete(fundPush);
            }
        }

    }
}
