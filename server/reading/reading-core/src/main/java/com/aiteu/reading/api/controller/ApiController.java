package com.aiteu.reading.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aiteu.reading.core.JsonConvertUtils;
/**
 * 提供所有的api接口
 * @author liwei
 *
 */
@Controller
public class ApiController {
	
	@RequestMapping("/api/browse.json")
	
	public void apiBrowse(HttpServletResponse res){
		
		Map<String, Object> item = new HashMap<String, Object>();
		item.put("name", "liwei");
		item.put("age", 24);
		
		JsonConvertUtils.outJson(res, item);
	}
	
	
}
