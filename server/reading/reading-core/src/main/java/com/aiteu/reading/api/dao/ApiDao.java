package com.aiteu.reading.api.dao;

import java.util.List;
import java.util.Map;

import com.aiteu.reading.api.model.Article;

public interface ApiDao {
	
	public List<Article> search(Map<String, String> param);
	
	public int totalCount(Map<String, String> param);
}
