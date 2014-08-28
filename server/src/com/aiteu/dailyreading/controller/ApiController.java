package com.aiteu.dailyreading.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 接口控制器
 * 与客户端的数据交互接口
 * @author liwei
 *
 */
@Controller
public class ApiController {
	
	@RequestMapping("/test")
	public ModelAndView test(){
		ModelAndView testModelView = new ModelAndView();
		
		Map<String, Object> testData = new HashMap<String, Object>();
		testData.put("title", "DailyReading");
		testData.put("time", "2014-08-28");
		testModelView.addObject(testData);
		testModelView.setViewName("test");
		
		return testModelView;
	}
}
