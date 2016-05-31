package com.demo.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/mhtml")
public class MhtmlMapping {
	
	@RequestMapping(value="/")
	public String s(){
		System.out.println("12312312312321");
		return "test2";
		
	}

}
