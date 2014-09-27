package com.aiteu.reading.base.dao;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author liwei
 *
 */
public interface BaseDao {
	
	public JdbcTemplate getTemplate();
}
