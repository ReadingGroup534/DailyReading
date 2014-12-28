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
		
		return article;
	}
}
