package com.aiteu.admin.core.browse.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aiteu.admin.core.base.BaseDao;
import com.aiteu.admin.core.base.SqlTemplate;
import com.aiteu.admin.core.browse.dao.BrowseDao;

@Repository
public class BrowseDaoImpl extends BaseDao implements BrowseDao{

	public List<Map<String, Object>> getBrowses() {
		// TODO Auto-generated method stub
		SqlTemplate sqlTemp = getSqlTemplate();
		String sql = "select * from browse where active='y'";
		return sqlTemp.findAll(sql);
	}
	
}
