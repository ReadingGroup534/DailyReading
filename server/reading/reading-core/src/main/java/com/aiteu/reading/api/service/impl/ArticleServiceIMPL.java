package com.aiteu.reading.api.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aiteu.reading.api.dao.ArticleDao;
import com.aiteu.reading.api.model.Article;
import com.aiteu.reading.api.model.ArticleVO;
import com.aiteu.reading.api.service.ArticleService;
import com.aiteu.reading.base.ConvertUtil;

@Service("articleService")
public class ArticleServiceIMPL implements ArticleService{
	
	@Autowired
	private ArticleDao articleDao;

	public List<ArticleVO> getList(Map<String, String> form) {
		// TODO Auto-generated method stub
		List<Article> list = articleDao.getArticles(form);
		
		return ConvertUtil.convertArticle(list);
	}

	public int getCount(Map<String, String> form) {
		// TODO Auto-generated method stub
		return articleDao.getCount(form);
	}

}
