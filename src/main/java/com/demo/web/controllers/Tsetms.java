package com.demo.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.mongo.dao.MongoTestDao;
import com.demo.mongo.domain.MongoTest;
@Controller
@RequestMapping("/")
public class Tsetms {
@Autowired
MongoTestDao mongoTestDao;
	
	@RequestMapping("mongoTest")
	@ResponseBody
	public String name213(){
		MongoTest mongoTest = new MongoTest();
		mongoTest.setId("123");
		mongoTest.setMessage("test");
		mongoTestDao.save(mongoTest);
	return "123";
	}
	
	
	@RequestMapping("jsp")
	public String name() {
		return "index.jsp";
	}
	
	@RequestMapping("html")
	public String name1() {
		return "index.html";
	}
	
	@RequestMapping("mhtml")
	public String name2() {
		return "111.mhtml";
	}
	
	
}
