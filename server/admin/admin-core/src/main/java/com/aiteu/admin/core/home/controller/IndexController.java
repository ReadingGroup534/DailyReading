package com.aiteu.admin.core.home.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/")
	public String indexRoot(HttpServletResponse res) throws IOException{
		res.sendRedirect("/index");
		return "home/index";
	}
	
	@RequestMapping("/index")
	public String index(ModelMap modelMap, HttpServletRequest request){
		modelMap.put("body", "Hello Freemaker");
		modelMap.put("request", request);
		return "home/index";
	}
}
