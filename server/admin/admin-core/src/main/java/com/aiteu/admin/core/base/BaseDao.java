package com.aiteu.admin.core.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public SqlTemplate getSqlTemplate(){
		
		return new SqlTemplate(jdbcTemplate);
	}
}
