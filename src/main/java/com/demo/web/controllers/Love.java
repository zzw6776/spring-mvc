package com.demo.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Love {

	@RequestMapping("/love")
	public String love() {
		return "love.html";
	}
}
