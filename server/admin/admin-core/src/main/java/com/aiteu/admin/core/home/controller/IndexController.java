package com.aiteu.admin.core.home.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aiteu.admin.core.home.service.HomeService;

@Controller
public class IndexController {
	
	@Autowired
	private HomeService homeService;
	
	@RequestMapping("/index.htm")
	public String index(ModelMap modelMap, HttpServletRequest req){
		
		modelMap.put("indexs", homeService.getIndexList());
		modelMap.put("base_context", req.getContextPath());
		return "index.htm";
	}
}
