package com.aiteu.reading.base.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.aiteu.dbs.DbsFactory;
import com.aiteu.reading.base.dao.BaseDao;

@Repository
public class BaseDaoImpl implements BaseDao{
	
	@Autowired
	private DbsFactory dbsFactory;
	private JdbcTemplate template = null;

	public JdbcTemplate getTemplate() {
		template = dbsFactory.getTemplate("reading");
		return template;
	}
}
