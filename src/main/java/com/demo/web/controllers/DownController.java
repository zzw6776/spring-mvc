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
	
	
	@RequestMapping("/resume")
	@ResponseBody
	public boolean upload(){
		return	FileDownload.httpDownload("http://www.500d.me/editresume/export/500d_329950_69_336867_20161222172158.pdf", "/usr/local/tomcat/webapps/upload/resume.pdf");
	}
}
