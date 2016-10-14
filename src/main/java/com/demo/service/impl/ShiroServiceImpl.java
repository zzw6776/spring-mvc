package com.demo.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class ShiroServiceImpl {

	
	public void login(String account,String password) {
		UsernamePasswordToken token = new UsernamePasswordToken(account, password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
	}
}
