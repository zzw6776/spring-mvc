package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.hibernate.dao.HDao;
import com.demo.hibernate.entity.HEntity;
import com.demo.mybatis3.dao.BDao;
import com.demo.mybatis3.domain.BEntity;

@Service
@Transactional
public class DataBaseTestService {

	@Autowired
	BDao mybatisDao;
	@Autowired
	HDao hibernateDao;
	
	public void mybatisTransactionTest() {
		BEntity test = new BEntity();
		test.setMessage("中文支持");
		mybatisDao.insert(test);
	}
	
	public List<HEntity> findAll(){
		List<HEntity> aEntities = hibernateDao.findAll();
		return aEntities;
		
	}
}
