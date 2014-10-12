package com.aiteu.reading.api.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aiteu.reading.api.service.ApiService;
import com.aiteu.reading.core.JsonConvertUtils;
/**
 * 提供所有的api接口
 * 1.api/browse 文章分类
 * 2.api/list	文章列表
 * @author liwei
 *
 */
@Controller
public class ApiController {
	
	@Autowired
	private ApiService apiService;
	
	@RequestMapping("/api/browse")
	public void apiBrowse(HttpServletRequest req, HttpServletResponse res){
		Map<String, Object> data = apiService.getAllBrowses();
		JsonConvertUtils.outJson(res, data);
	}
	
	@RequestMapping("/api/daily")
	public void apiDailyList(HttpServletRequest req, HttpServletResponse res){
		Map<String, Object> data = apiService.getDailyAiticle(req);
		JsonConvertUtils.outJson(res, data);
	}
}