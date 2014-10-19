package com.aiteu.admin.core.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

public class SqlTemplate {
	
	private JdbcTemplate jdbcTemplate;
	
	public SqlTemplate(JdbcTemplate template){
		this.jdbcTemplate = template;
	}
	
	public List<Map<String, Object>> findAll(final Object...args){
		final String sql = args[0].toString();
		List<Map<String, Object>> mList = null;
		mList = this.jdbcTemplate.query(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement ps = conn.prepareStatement(sql);
				if(args.length > 1){
					for(int i=1;i<args.length;i++){
						ps.setObject(i, args[i]);
					}
				}
				return ps;
			}
		}, new ItemMapper());
		
		return  (null != mList && mList.size() > 0) ? mList : null;
	}
	
	class ItemMapper implements RowMapper<Map<String, Object>>{

		public Map<String, Object> mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			// TODO Auto-generated method stub
			ResultSetMetaData metaData = rs.getMetaData();
			int column = metaData.getColumnCount();
			Map<String, Object> item = new HashMap<String, Object>();
			for(int i=1; i<= column; i++){
				item.put(metaData.getColumnLabel(i), rs.getObject(i));
			}
			return item;
		}
		
	}
}
