package com.demo.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class UrlMapping {

	
	
	// 正常映射
	@RequestMapping(value = "/", method = { RequestMethod.GET })
	public String index() {
		return "redirect:/html/index.html";
	}

	// 多路径映射一个方法
	@RequestMapping(value = { "test1", "test2" }, method = { RequestMethod.GET })
	@ResponseBody
	public String test1() {
		return "success";
	}

	// url参数绑定and正则表达式
	@RequestMapping(value = "/{id://d+}", method = { RequestMethod.GET })
	@ResponseBody
	public Object getDetail(@PathVariable(value = "id") Integer id) {
		return id;
	}

	// url通配符映射
	// 通配符有“？”和“*”两个字符。其中“？”表示1个字符，“*”表示匹配多个字符，“**”表示匹配0个或多个路径。
	@RequestMapping(value = "urlTest/**")
	@ResponseBody
	public String url() {
		return "urlTestsuccess";
	}

	// 参数是否被包括
	// params="!example"
	// params="example=AAA"
	// params="example!=AAA"
	// 限制action所接受请求头参数：
	// 同限制action所接受的请求参数一样，我们也可以为某个action指定映射的请求头中必须包含某参数，或必须不包含某参数，或者某参数必须等于某个值，或者某参数必须不等于某个值这些限制。
	// 指定映射请求头必须包含某参数：
	// @RequestMapping(value="/headerTest", headers ="example")。与限制请求参数是一样的，可以参考上面的例子进行测试。
	// 注：当我们为headers指定多个参数时如：headers={"example1","example2"}，表示的是and关系，即两个参数限制必须同时满足。
	@RequestMapping(value = "/paramstest", params = "example", method = { RequestMethod.GET })
	@ResponseBody
	public String param() {
		return "paramSuccess";
	}

}