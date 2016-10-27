package com.demo.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.hibernate.dao.UserDao;
import com.demo.hibernate.entity.User;
import com.demo.service.impl.ShiroServiceImpl;
import com.demo.service.impl.UserServiceImpl;
import com.demo.util.EncryptUtil;

@Controller
@RequestMapping("user")
public class LoginController {
@Autowired
UserServiceImpl userService;
@Autowired
ShiroServiceImpl shiroService;
	@RequestMapping("signUp")
	@ResponseBody
	public Boolean signUp(User user) {
		user.setPassword(EncryptUtil.MD5(user.getPassword(), false));
		userService.save(user);
		return true;
	}
	
	@RequestMapping("login")
	@ResponseBody
	public Boolean login(User user) {
		shiroService.login(user.getAccount(), user.getPassword());
		return true;
	}
	
}
