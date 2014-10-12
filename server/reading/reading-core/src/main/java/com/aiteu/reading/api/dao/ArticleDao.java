package com.aiteu.reading.api.dao;

import java.util.List;
import java.util.Map;

import com.aiteu.reading.api.model.Article;

public interface ArticleDao {
	
	public List<Article> getDailyArticle(Map<String, Object> param);
}
