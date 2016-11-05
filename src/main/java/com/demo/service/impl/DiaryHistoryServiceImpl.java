package com.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.demo.hibernate.dao.DiaryDao;
import com.demo.hibernate.dao.DiaryHistoryDao;
import com.demo.hibernate.entity.Diary;
import com.demo.hibernate.entity.DiaryHistory;
import com.demo.util.TypeUtil;
@Service
public class DiaryHistoryServiceImpl {
	@Autowired
	DiaryHistoryDao diaryHistoryDao;

	@Autowired
	DiaryDao diaryDao;
	
	public void insert(DiaryHistory diaryHistory) {
		Diary diary;
		if (!StringUtils.isEmpty(diaryHistory.getdId())) {
			 diary = diaryDao.find(diaryHistory.getdId());
		}else {
			diary = diaryDao.queryToday(diaryHistory.getuAccount());
		}
		if (diary==null) {
			diary = new Diary();
			TypeUtil.ClassToClass(diaryHistory, diary);
			diaryDao.save(diary);
		}else {
			diaryHistory.setdId(diary.getId());
			diary.setUpdateTime(System.currentTimeMillis());
			diary.setMessage(diaryHistory.getMessage());
			diary.setIsEncrypt(diaryHistory.getIsEncrypt());
		}
		diaryHistoryDao.save(diaryHistory);
	}
}
