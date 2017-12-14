package com.demo.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.demo.hibernate.dao.FundPushDao;
import com.demo.hibernate.entity.FundPush;
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
        FundPush query = new FundPush();
        query.setFundId(fundId);
        FundPush fundPush = fundPushDao.selectByUnique(query);
        if (null != fundPush) {
            Set<String> set = new HashSet<String>(Arrays.asList(fundPush.getAccounts().split(",")));
            set.add(account);
            fundPush.setAccounts(StringUtils.join(set.toArray(), ","));
        } else {
            query.setAccounts(account);
            fundPushDao.save(query);
        }
    }
}
