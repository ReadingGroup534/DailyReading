package com.aiteu.reading.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aiteu.reading.api.dao.ArticleDao;
import com.aiteu.reading.api.dao.BrowseDao;
import com.aiteu.reading.api.model.Article;
import com.aiteu.reading.api.model.Browse;
import com.aiteu.reading.api.service.ApiService;

@Service("apiService")
public class ApiServiceImpl implements ApiService{
	
	@Autowired
	private BrowseDao browseDao;
	@Autowired
	private ArticleDao articleDao;
	
	public Map<String, Object> getAllBrowses() {
		// TODO Auto-generated method stub
		List<Browse> browses = browseDao.getBrowses();
		if(null != browses){
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("total", browses.size());
			data.put("list", browses);
			data.put("status", 200);
			return data;
		}
		
		return null;
	}

	public Map<String, Object> getArticlesBelowBrowse(Map<String, Object> param) {
		// TODO Auto-generated method stub
		
		return null;
	}

	public Map<String, Object> getDailyAiticle(HttpServletRequest req) {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("offset", 0);
		String nm = req.getParameter("nm");
		int limit = 4;
		if(null != nm && !nm.equals("")){
			limit = Integer.parseInt(nm);
		}
		param.put("limit", limit);
		param.put("order", "show_time desc");
		List<Article> articles = articleDao.getDailyArticle(param);
		Map<String, Object> results = new HashMap<String, Object>();
		if(null != articles){
			results.put("total", articles.size());
			results.put("list", articles);
			results.put("code", 200);
		}else{
			results.put("code", 100);
			results.put("msg", "no results");
		}
		return results;
	}

	public Map<String, Object> getSearchResults(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
