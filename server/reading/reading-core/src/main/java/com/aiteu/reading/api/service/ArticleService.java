package com.aiteu.reading.api.service;

import java.util.List;
import java.util.Map;

import com.aiteu.reading.api.model.ArticleVO;

public interface ArticleService {
	
	public int getCount(Map<String, String> form);
	public List<ArticleVO> getList(Map<String, String> form);
	public List<ArticleVO> getTodayList(Map<String, String> form);
}
