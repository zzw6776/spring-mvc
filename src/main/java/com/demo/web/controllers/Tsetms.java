package com.demo.web.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.demo.util.JdUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import com.demo.hibernate.entity.HEntity;
import com.demo.mongo.dao.MDao;
import com.demo.mongo.domain.MEntity;
import com.demo.service.DataBaseTestService;
import com.demo.service.impl.ShiroServiceImpl;
import com.demo.util.ExpireJobTask;
import com.demo.util.FileUpload;

@Controller
@RequestMapping("/")
public class Tsetms {
	@Autowired
	MDao mongoTestDao;

	@Autowired
	DataBaseTestService service;

	@Autowired
	ShiroServiceImpl shiroservice;

	@Autowired
	ExpireJobTask expireJobTask;

	@Autowired
	JdUtils jdUtils;

	@RequestMapping("jdLogin")
	@ResponseBody
	public boolean login() throws Exception {
		return jdUtils.login();
	}

	@RequestMapping("monitorLogistics")
	@ResponseBody
	public void login(String order) throws Exception {
		 jdUtils.monitorLogistics(order);
	}


	@RequestMapping("mysql")
	public void mysql() throws Exception {
		expireJobTask.exportSql();
	}
	@RequestMapping("backUpSql")
	public void backUpSql() throws Exception {
		expireJobTask.backUpSql();
	}
	
	
	@RequestMapping("login")
	public void login(String account,String password){
		shiroservice.login(account, password);
	}
	
	@RequestMapping("jsp")
	public String name() {
		return "index.jsp";
	}

	@RequestMapping("html")
	@RequiresRoles("admin")
	public String name1() {
		return "index.html";
	}

	@RequestMapping("mhtml")
	public String name2(String path) {
		System.out.println(path);
		return path+".mhtml";
	}

}
