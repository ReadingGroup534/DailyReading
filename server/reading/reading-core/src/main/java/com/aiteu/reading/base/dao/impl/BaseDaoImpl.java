package com.aiteu.reading.base.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aiteu.dbs.DbsManager;
import com.aiteu.reading.base.ContentValues;
import com.aiteu.reading.base.ResultsContainer;
import com.aiteu.reading.base.dao.BaseDao;

@Repository
public class BaseDaoImpl implements BaseDao{
	
	@Autowired
	private DbsManager dbsManager;

	public ResultsContainer select(String table, String[] columns,
			String whereClause, String[] whereArgs, String limit,
			String[] orderBy, boolean splitPage) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultsContainer select(String table, String[] columns,
			String whereClause, String[] whereArgs, String limit,
			String[] orderBy, String groupBy, boolean splitPage) {
		// TODO Auto-generated method stub
		return null;
	}

	public int update(String table, ContentValues values, String whereAlause) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insert(String table, ContentValues values, String whereClause,
			String[] whereArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insert(String table, ContentValues values, String whereClause,
			String[] whereArgs, boolean isReturnInsertId) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int delete(String table, String whereClause, String[] whereArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
