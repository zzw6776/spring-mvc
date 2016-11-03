package com.demo.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.hibernate.entity.Diary;
import com.demo.hibernate.entity.DiaryHistory;
import com.demo.service.impl.DiaryHistoryServiceImpl;
import com.demo.service.impl.DiaryServiceImpl;
import com.demo.util.EncryptUtil;
@RequestMapping("diary")
@Controller
public class DiaryController {

	@Autowired
	DiaryHistoryServiceImpl diaryHistoryService;
	
	@RequestMapping("insert")
	@ResponseBody
	public void insert(DiaryHistory diary,String encryptKey) {
		if (!StringUtils.isEmpty(encryptKey)) {
			String message  = EncryptUtil.encode(diary.getMessage(), encryptKey);
			diary.setMessage(message);
			diary.setIsEncrypt(true);
		}
		diary.setCreateTime(System.currentTimeMillis());
		diaryHistoryService.insert(diary);
	}
	
}