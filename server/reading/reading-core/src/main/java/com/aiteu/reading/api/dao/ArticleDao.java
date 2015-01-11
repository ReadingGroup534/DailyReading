package com.aiteu.reading.api.dao;

import java.util.List;
import java.util.Map;

import com.aiteu.reading.api.model.Article;

public interface ArticleDao {
	
	public int getCount(Map<String, String> form);
	public List<Article> getArticles(Map<String, String> form);
	public List<Article> getDailyList(String sql);
}
