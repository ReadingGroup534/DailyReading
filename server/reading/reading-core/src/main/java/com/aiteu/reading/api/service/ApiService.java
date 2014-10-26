package com.aiteu.reading.api.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface ApiService {
	//获得所有的类目
	public Map<String, Object> getAllBrowses();
	//获得类目browse_id下的所有文章
	public Map<String, Object> getArticlesBelowBrowse(Map<String, Object> param);
	//获得每日更新的文章
	public Map<String, Object> getDailyAiticle(HttpServletRequest req);
	//支持搜索
	public Map<String, Object> getSearchResults(Map<String, String> param);
}
