package com.demo.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.util.FileDownload;

@RequestMapping("down")
@Controller
public class DownController {

	
	@RequestMapping
	@ResponseBody
	public boolean down(String url,String path){
		return	FileDownload.httpDownload(url, path);
	}
}
