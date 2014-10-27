package com.aiteu.reading.base.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.aiteu.reading.api.model.Article;
import com.aiteu.reading.api.model.Browse;

public class BrowseRowMapper implements RowMapper<Browse>{

	public Browse mapRow(ResultSet rs, int rownum) throws SQLException {
		// TODO Auto-generated method stub
		Browse browse = new Browse();
		browse.setBrowseId(rs.getString("browse_id"));
		browse.setBrowseValue(rs.getString("browse_value"));
		browse.setDescription(rs.getString("description"));
		return browse;
	}

}
