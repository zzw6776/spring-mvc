package com.demo.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("down")
@Controller
public class DownController {

	
	@RequestMapping
	@ResponseBody
	public boolean down(String url,String path){
		
		return false;
	}
}
