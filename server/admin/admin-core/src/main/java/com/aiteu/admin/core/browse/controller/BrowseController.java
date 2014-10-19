package com.aiteu.admin.core.browse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aiteu.admin.core.browse.service.BrowseService;

@Controller
public class BrowseController {
	
	@Autowired
	private BrowseService browseService;
	
	@RequestMapping("/browse")
	public String browses(ModelMap modelMap){
		modelMap.put("browses", browseService.getBrowses());
		return "browse.htm";
	}
}
