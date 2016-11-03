package com.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.hibernate.dao.DiaryDao;
import com.demo.hibernate.dao.DiaryHistoryDao;
import com.demo.hibernate.entity.Diary;
import com.demo.hibernate.entity.DiaryHistory;
@Service
public class DiaryHistoryServiceImpl {
	@Autowired
	DiaryHistoryDao diaryHistoryDao;

	
	
	public void insert(DiaryHistory diaryHistory) {
		diaryHistoryDao.save(diaryHistory);
	}
}
