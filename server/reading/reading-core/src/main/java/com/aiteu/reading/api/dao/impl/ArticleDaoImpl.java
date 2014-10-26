package com.aiteu.reading.api.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import com.aiteu.reading.api.dao.ArticleDao;
import com.aiteu.reading.api.model.Article;
import com.aiteu.reading.base.dao.impl.BaseDaoImpl;
import com.aiteu.reading.base.mapper.ArticleRowMapper;

@Repository
public class ArticleDaoImpl extends BaseDaoImpl implements ArticleDao{

	public List<Article> getDailyArticle(final Map<String, Object> param) {
		// TODO Auto-generated method stub
		final String query = "select * from article where active='y' order by ? limit ? offset ?";
		
		List<Article> articles = this.getTemplate().query(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement ps = con.prepareStatement(query);
				ps.setObject(1, param.get("order"));
				ps.setInt(2, Integer.parseInt(param.get("limit")+""));
				ps.setInt(3, Integer.parseInt(param.get("offset")+""));
				return ps;
			}
		}, new ArticleRowMapper(param.get("base_url").toString()));
		
		return (null != articles && articles.size() > 0) ? articles : null;
	}
	
}
