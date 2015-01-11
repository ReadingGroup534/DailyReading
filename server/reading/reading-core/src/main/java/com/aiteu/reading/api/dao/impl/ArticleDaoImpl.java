package com.aiteu.reading.api.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.aiteu.reading.api.dao.ArticleDao;
import com.aiteu.reading.api.model.Article;
import com.aiteu.reading.base.dao.impl.BaseDaoImpl;
import com.aiteu.reading.base.mapper.ArticleRowMapper;

@Repository
public class ArticleDaoImpl extends BaseDaoImpl implements ArticleDao{

	public List<Article> getArticles(Map<String, String> form) {
		final Map<String, String> params = form;

		List<Article> articles = this.getTemplate().query(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				// TODO Auto-generated method stub
				String sql = "select * from article where active = 'y'";
				if(null != params.get("browseId") && !params.get("browseId").equals("0")){
					sql += (" and browse_id = "+params.get("browseId"));
				}
				sql +=" order by show_time desc limit ?,?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(params.get("start")));
				ps.setInt(2, Integer.parseInt(params.get("limit")));
				return ps;
			}
		}, new ArticleRowMapper());
		
		return (null != articles && articles.size() > 0) ? articles : null;
	}

	public int getCount(Map<String, String> form) {
		final Map<String, String> params = form;
		return this.getTemplate().query(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				String sql = "select count(*) from article where active = 'y'";
				if(null != params.get("browseId") && !params.get("browseId").equals("0")){
					sql += (" and browse_id = "+params.get("browseId"));
				}
				PreparedStatement ps = conn.prepareStatement(sql);
				return ps;
			}
		}, new ResultSetExtractor<Integer>() {

			public Integer extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				// TODO Auto-generated method stub
				if(rs.next()){
					return rs.getInt(1);
				}
				return 0;
			}
		});
	}

	public List<Article> getDailyList(final String sql) {
		// TODO Auto-generated method stub
		List<Article> articles = this.getTemplate().query(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				// TODO Auto-generated method stub
				System.out.println("============================="+sql);
				PreparedStatement ps = conn.prepareStatement(sql);
				return ps;
			}
		}, new ArticleRowMapper());
		
		return (null != articles && articles.size() > 0) ? articles : null;
	}
	
}
