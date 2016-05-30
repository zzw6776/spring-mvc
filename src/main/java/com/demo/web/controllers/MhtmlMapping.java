package com.demo.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/mhtml")
public class MhtmlMapping {
	
	@RequestMapping(value="/")
	public String s(){
		
		return "中国移动ID类短信网关错误代码表 _ 短信运营者";
		
	}

}
