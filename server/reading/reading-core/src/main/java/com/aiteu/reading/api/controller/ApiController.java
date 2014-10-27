package com.aiteu.reading.api.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;







import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aiteu.reading.api.service.ApiService;
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
	
	@RequestMapping("/api/browse.json")
	public String apiBrowse(ModelMap modelMap){
		Map<String, Object> data = apiService.getAllBrowses();
		modelMap.putAll(data);
		return "/api/browse.json";
	}
	
	@RequestMapping("/api/list.json")
	public String articleList(ModelMap modelMap, HttpServletRequest req){
		Map<String, String> form = initSharedForm(req);
		Map<String, Object> results = apiService.getSearchResults(form);
		modelMap.putAll(results);
		return "/api/list.json";
	}
	
	@RequestMapping("/api/detail.htm")
	public void articleDetail(HttpServletRequest req, HttpServletResponse res){
		Map<String, String> form = initSharedForm(req);
		apiService.getDetail(res, form);
	}
	
	/**
	 * 初始化公共参数
	 * @return
	 */
	private Map<String, String> initSharedForm(HttpServletRequest req){
		Map<String, String> form = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		Map<String, Object> params = req.getParameterMap();
		Iterator<Map.Entry<String, Object>> entries = params.entrySet().iterator();
		String name = "";
		String value = "";
		Map.Entry<String, Object> entry;
		while(entries.hasNext()){
			entry = entries.next();
			name = entry.getKey();
			Object valueObj = entry.getValue();
			if(valueObj == null){
				value = "";
			}else if(valueObj instanceof String[]){
				String[] values = (String[])valueObj;
	            for(int i=0;i<values.length;i++){
	                value = values[i] + ",";
	            }
	            value = value.substring(0, value.length()-1);
			}else{
				value = valueObj.toString();
			}
			form.put(name, value);
		}
		form.put("base_url", "http://"+req.getLocalName()+":"+req.getLocalPort()+req.getContextPath());
		return form;
	}
}
