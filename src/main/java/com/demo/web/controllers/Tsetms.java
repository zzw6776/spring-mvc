package com.demo.web.controllers;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.hibernate.entity.HEntity;
import com.demo.mongo.dao.MDao;
import com.demo.mongo.domain.MEntity;
import com.demo.service.DataBaseTestService;
import com.demo.service.impl.ShiroServiceImpl;

@Controller
@RequestMapping("/")
public class Tsetms {
	@Autowired
	MDao mongoTestDao;

	@Autowired
	DataBaseTestService service;

	@Autowired
	ShiroServiceImpl shiroservice;
	
	@RequestMapping("login")
	public void login(String account,String password){
		shiroservice.login(account, password);
	}
	
	@RequestMapping("jsp")
	@RequiresRoles("admin")
	public String name() {
		return "index.jsp";
	}

	@RequestMapping("html")
	@RequiresRoles("adminasd")
	public String name1() {
		return "index.html";
	}

	@RequestMapping("mhtml")
	public String name2() {
		return "111.mhtml";
	}

}