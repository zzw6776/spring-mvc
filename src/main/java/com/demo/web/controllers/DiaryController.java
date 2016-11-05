package com.demo.web.controllers;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.hibernate.dao.DiaryDao;
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
	
	@Autowired
	DiaryDao diaryDao;
	
	@RequestMapping("insert")
	@ResponseBody
	public void insert(DiaryHistory diary,String encryptKey,HttpServletRequest request) {
		if (StringUtils.isEmpty(diary.getuAccount())) {
					diary.setuAccount(request.getRemoteAddr());
			}
		if (!StringUtils.isEmpty(encryptKey)) {
			String message  = EncryptUtil.encode(diary.getMessage(), encryptKey);
			diary.setMessage(message);
			diary.setIsEncrypt(true);
		}
		diary.setCreateTime(System.currentTimeMillis());
		diaryHistoryService.insert(diary);
	}
	@RequestMapping("query")
	@ResponseBody
	public List<Diary> queryByUser(String user,String encryptKey) {
		Diary diary = new Diary();
		diary.setuAccount(user);
		List<Diary>  diaries = diaryDao.select(diary);
		if (!StringUtils.isEmpty(encryptKey)) {
			for (Diary diary2 : diaries) {
				if (diary2.getIsEncrypt()!=null&&diary2.getIsEncrypt()) {
					diary2.setMessage(EncryptUtil.decode(diary2.getMessage(),encryptKey));
				}
			}
		}
		diaries.sort((d1,d2)->{
			return d1.getCreateTime()-d2.getCreateTime()>0?-1:1;
		});
		return diaries;
	}
	@RequestMapping("queryById")
	@ResponseBody
	public Diary queryById(String dId,String encryptKey) {
		Diary diary =diaryDao.find(dId);
		if (diary!=null&&diary.getIsEncrypt()!=null&&diary.getIsEncrypt()&&!StringUtils.isEmpty(encryptKey)) {
			diary.setMessage(EncryptUtil.decode(diary.getMessage(),encryptKey));
		}
		return diary;
	}
	
	@RequestMapping("queryByUserInToday")
	@ResponseBody
	public Diary queryByUserInToday(String user,String encryptKey) {
		Diary diary = diaryDao.queryToday(user);
		if (diary!=null&&diary.getIsEncrypt()!=null&&diary.getIsEncrypt()&&!StringUtils.isEmpty(encryptKey)) {
			diary.setMessage(EncryptUtil.decode(diary.getMessage(),encryptKey));
		}
		return diary;
	}
}
