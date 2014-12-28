package com.aiteu.reading.api.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import com.aiteu.reading.api.dao.BrowseDao;
import com.aiteu.reading.api.model.Browse;
import com.aiteu.reading.base.dao.impl.BaseDaoImpl;
import com.aiteu.reading.base.mapper.BrowseRowMapper;

@Repository
public class BrowseDaoImpl extends BaseDaoImpl implements BrowseDao{

	public List<Browse> getBrowses() {
		// TODO Auto-generated method stub
		final String query = "select browse_id,browse_value from browse where active = 'y'";
		List<Browse> browses = this.getTemplate().query(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(query);
				return ps;
			}
		}, new BrowseRowMapper());

		return (null!=browses && browses.size() > 0)? browses : null;
	}
	
}
