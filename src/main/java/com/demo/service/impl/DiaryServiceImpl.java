package com.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.hibernate.dao.DiaryDao;
import com.demo.hibernate.entity.Diary;
@Service
public class DiaryServiceImpl {
	@Autowired
	DiaryDao diaryDao;

	
	
	public void insert(Diary diary) {
		diaryDao.save(diary);
	}
	
	

}
