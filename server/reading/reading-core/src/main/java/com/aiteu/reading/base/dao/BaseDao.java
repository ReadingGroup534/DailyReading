package com.aiteu.reading.base.dao;

import com.aiteu.reading.base.ContentValues;
import com.aiteu.reading.base.ResultsContainer;
/**
 * 数据库的增删改查，操作和sqlite类似
 * @author liwei
 *
 */
public interface BaseDao {
	/**
	 * 数据库查询
	 * @param table 表名字
	 * @param columns 查询的列
	 * @param whereClause 条件
	 * @param whereArgs 条件参数
	 * @param limit 限制数量
	 * @param orderBy 排序
	 * @param splitPage 是否进行数据分页
	 * @return
	 */
	public ResultsContainer select(String table, String[] columns, String whereClause, String[] whereArgs, String limit, String[] orderBy, boolean splitPage);
	
	/**
	 * 数据库查询
	 * @param table 表名字
	 * @param columns 查询的列
	 * @param whereClause 条件
	 * @param whereArgs 条件参数
	 * @param limit 限制数量
	 * @param orderBy 排序
	 * @param groupBy 分组
	 * @param splitPage 是否进行数据分页
	 * @return
	 */
	public ResultsContainer select(String table, String[] columns, String whereClause, String[] whereArgs, String limit, String[] orderBy, String groupBy, boolean splitPage);
	
	/**
	 * 更新数据集合
	 * @param table 表名字
	 * @param values 更新字段集
	 * @param whereArgs 条件
	 * @return 更新行数
	 */
	public int update(String table, ContentValues values, String whereAlause);
	
	/**
	 * 
	 * @param table
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public int insert(String table, ContentValues values, String whereClause,String[] whereArgs);
	/**
	 * 
	 * @param table
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 * @param isReturnInsertId 是否返回插入id
	 * @return
	 */
	public int insert(String table, ContentValues values, String whereClause,String[] whereArgs, boolean isReturnInsertId);
	/**
	 * 
	 * @param table
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public int delete(String table, String whereClause, String[] whereArgs);
}
