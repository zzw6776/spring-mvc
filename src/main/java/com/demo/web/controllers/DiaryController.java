package com.demo.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import com.demo.hibernate.entity.Diary;
import com.demo.service.impl.DiaryServiceImpl;

public class DiaryController {

	@Autowired
	DiaryServiceImpl diaryService;
	
	
	public void insert(Diary diary) {
		
	}
	
}
