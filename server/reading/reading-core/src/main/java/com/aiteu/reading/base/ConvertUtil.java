package com.aiteu.reading.base;

import java.util.ArrayList;
import java.util.List;

import com.aiteu.reading.api.model.Article;
import com.aiteu.reading.api.model.ArticleVO;

public class ConvertUtil {
	
	public static List<ArticleVO> convertArticle(List<Article> articles){
		if(null == articles) return null;
		List<ArticleVO> voList = new ArrayList<ArticleVO>();
		for(Article article : articles){
			ArticleVO vo = new ArticleVO();
			vo.setTitle(article.getTitle());
			voList.add(vo);
		}
		return voList;
	}
}
