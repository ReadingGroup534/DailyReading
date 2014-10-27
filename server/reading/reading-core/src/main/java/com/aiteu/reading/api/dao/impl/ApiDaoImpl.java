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

import com.aiteu.reading.api.dao.ApiDao;
import com.aiteu.reading.api.model.Article;
import com.aiteu.reading.base.dao.impl.BaseDaoImpl;
import com.aiteu.reading.base.mapper.ArticleRowMapper;

/**
 * 提供数据库的相关操作
 * @author liwei
 *
 */
@Repository
public class ApiDaoImpl extends BaseDaoImpl implements ApiDao{

	public List<Article> search(final Map<String, String> param) {
		// TODO Auto-generated method stub
		final String sql = "select * from article where active = 'y' and show_time < now()  order by "+ param.get("order")+" limit ?,?";
		List<Article> articles = this.getTemplate().query(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(param.get("offset")));
				ps.setInt(2, Integer.parseInt(param.get("limit")));
				return ps;
			}
		}, new ArticleRowMapper(param.get("base_url")));
		return (articles == null || articles.size() == 0) ? null : articles;
	}

	public int totalCount(Map<String, String> param) {
		// TODO Auto-generated method stub
		final String sql = "select count(*) from article where active = 'y' and show_time < now()";
		Integer countValue = this.getTemplate().query(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement ps = conn.prepareStatement(sql);
				return ps;
			}
		}, new ResultSetExtractor<Integer>() {

			public Integer extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				// TODO Auto-generated method stub
				Integer countInt = 0;
				while(rs.next()){
					countInt = rs.getInt(1);
				}
				return countInt;
			}
		});
		
		return (countValue == null) ? 0 : countValue.intValue();
	}

}
