package com.demo.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/")
public class Tsetms {

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
