package com.aiteu.reading.api.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aiteu.reading.api.dao.ApiDao;
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
	@Autowired
	private ApiDao apiDao;
	
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
		List<Article> articles = null; 
				//articleDao.getDailyArticle(param);
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

	public Map<String, Object> getSearchResults(Map<String, String> param) {
		if(param.get("limit") == null || param.get("limit").equals("")){
			param.put("limit", "5");
		}
		if(param.get("offset") == null || param.get("offset").equals("")){
			param.put("offset", "0");
		}
		if(param.get("order") == null || param.get("order").equals("")){
			param.put("order", "show_time desc");
		}
		
		List<Article> articles = apiDao.search(param);
		int total = apiDao.totalCount(param);
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("list", articles);
		results.put("count", total);
		
		return results;
	}

	public void getDetail(HttpServletResponse res, Map<String, String> form) {
		String articleId = form.get("article_id");
		if(null == articleId || articleId.equals("")){
			System.out.println("parameter error");
			return;
		}
		try{
			res.sendRedirect(form.get("base_url")+"/detail/"+articleId+".txt");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
}
