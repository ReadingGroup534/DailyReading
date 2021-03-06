package com.aiteu.reading.web.api;

import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;

import com.aiteu.reading.api.model.Browse;
import com.aiteu.reading.api.service.ArticleService;
import com.aiteu.reading.api.service.BrowseService;

/**
 * 主要是处理api的核心事物
 * @author liwei
 *
 */
public class ApiDealer {
	private static final int LIMIT = 5;
	
	public ApiDealer(){
		
	}
	
	public void doGetBrowses(ModelMap modelMap, Map<String, String> form, BrowseService service){
		List<Browse> browseList = service.getBrowses();
		modelMap.put("browses", browseList);
	}
	
	public void doGetList(ModelMap modelMap, Map<String, String> form, ArticleService service){
		if(null == form.get("start")){
			form.put("start", "0");
		}
		if(null == form.get("limit")){
			form.put("limit", String.valueOf(LIMIT));
		}
		if(null == form.get("browseId")){
			form.put("browseId", "0");// 默认加载全部
		}
		modelMap.put("total", service.getCount(form));
		modelMap.put("list", service.getList(form));
	}
	
	public void doGetTodayList(ModelMap modelMap, Map<String, String> form, ArticleService service){
		if(null == form.get("offset")){
			form.put("offset", "0");
		}
		if(null == form.get("prevd")){
			form.put("prevd", "1");
		}
		modelMap.put("offset", form.get("offset"));
		modelMap.put("prevd", form.get("prevd"));
		modelMap.put("list", service.getTodayList(form));
	}
}
