package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.hibernate.dao.UserDao;
import com.demo.hibernate.entity.User;

@Service
public class UserServiceImpl {
	@Autowired
	UserDao userDao;
	
	public void save(User user) {
		userDao.save(user);
	}

	public List<User> select(User user) {
		return userDao.select(user);
	}
}
