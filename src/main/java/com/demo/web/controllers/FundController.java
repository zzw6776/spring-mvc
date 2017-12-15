package com.demo.web.controllers;

import com.demo.service.impl.FundPushServiceImpl;
import com.demo.util.FundPushTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("fund")
@Controller
public class FundController {

    @Autowired
    FundPushServiceImpl fundPushService;
    @Autowired
    FundPushTask fundPushTask;

    @RequestMapping("add")
    @ResponseBody
    public String add(String fundId, String account) {
        if (StringUtils.isEmpty(fundId) || StringUtils.isEmpty(account)) {
            return "参数缺失";
        }
        fundPushService.updateAndAdd(fundId,account);
        return "成功";
    }

    @RequestMapping("delete")
    @ResponseBody
    public String delete(String fundId, String account) {
        if (StringUtils.isEmpty(fundId) || StringUtils.isEmpty(account)) {
            return "参数缺失";
        }
        fundPushService.updateAndDelete(fundId,account);
        return "成功";
    }

    @RequestMapping("fundEstimatePush")
    @ResponseBody
    public String fundEstimatePush() {
        fundPushTask.fundEstimatePush();
        return "成功";
    }

    @RequestMapping("fundActualPush")
    @ResponseBody
    public String fundActualPush() {
        fundPushTask.fundActualPush();
        return "成功";
    }
}
