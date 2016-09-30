package com.demo.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.hibernate.entity.HEntity;
import com.demo.mongo.dao.MDao;
import com.demo.mongo.domain.MEntity;
import com.demo.service.DataBaseTestService;

@Controller
@RequestMapping("/")
public class Tsetms {
	@Autowired
	MDao mongoTestDao;

	@Autowired
	DataBaseTestService service;

	
	@RequestMapping("hibernateTest")
	@ResponseBody
	public List<HEntity> namehiber(){
		List<HEntity> bEntities = service.findAll();
		return bEntities;
		
	}
	
	@RequestMapping("mongoTest")
	@ResponseBody
	public String name213() {
		MEntity mongoTest = new MEntity();
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
