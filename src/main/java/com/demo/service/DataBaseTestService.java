package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.mybatis3.dao.MybatisTestMapper;
import com.demo.mybatis3.domain.MybayisTest;

//@Service
//@Transactional
public class DataBaseTestService {

	@Autowired
	MybatisTestMapper mybatisDao;
	
	
	public void mybatisTransactionTest() {
		MybayisTest test = new MybayisTest();
		test.setMessage("中文支持");
		mybatisDao.insert(test);
	}
}
