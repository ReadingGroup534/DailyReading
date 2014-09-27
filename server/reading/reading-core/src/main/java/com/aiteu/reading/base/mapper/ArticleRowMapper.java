package com.aiteu.reading.base.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.aiteu.reading.api.model.Article;

public class ArticleRowMapper implements RowMapper<Article>{

	public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Article article = new Article();
		
		article.setTitle(rs.getString("title"));
		article.setAuthor(rs.getString("author"));
		article.setArticleType(rs.getString("article_type"));
		article.setAbstracts(rs.getString("abstracts"));
		article.setRecommendStar(rs.getInt("recommend_star"));
		article.setPraiseTimes(rs.getInt("praise_times"));
		article.setShareTimes(rs.getInt("share_times"));
		article.setScanTimes(rs.getInt("scan_times"));
		article.setDetailUrl(rs.getString("physical_path"));
		
		return article;
	}
}
