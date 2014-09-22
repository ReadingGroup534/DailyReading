package com.aiteu.dbs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据库多数据源管理,切换
 * @author liwei
 *
 */
public class DbsManager {
	//缓存使用过的数据源
	private HashMap<String, BasicDataSource> targetDataSources;
	//数据库语句执行模版
	private JdbcTemplate template;
	//读取jdbc配置文件信息/config/jdbc
	private static ResourceBundle config = ResourceBundle.getBundle("config/jdbc");
	
	public DbsManager(){
		targetDataSources = new HashMap<String, BasicDataSource>();
		template = new JdbcTemplate();
	}
	/**
	 * 获取一个sql执行的模版
	 * @param type 数据库的类型
	 * @return
	 */
	public JdbcTemplate getTemplate(String type){
		
		//已经创建过数据源，并且尚可用,就直接使用
		if(targetDataSources.containsKey(type)){
			BasicDataSource dataSource = targetDataSources.get(type);
			template.setDataSource(dataSource);
			return template;
		}
		//创建新的数据源，并缓存
		BasicDataSource dataSource = selectDataSource(type);
		template.setDataSource(dataSource);
		return template;
	}
	
	private BasicDataSource selectDataSource(String type){
		String defType = config.getString("database.type");
		BasicDataSource defDataSource = null;
		//若默认的数据库已缓存，将不再创建,否则创建默认的数据库
		if(targetDataSources.containsKey(defType)){
			defDataSource = targetDataSources.get(defType);
		}else{
			defDataSource = new BasicDataSource();
			defDataSource.setDriverClassName(config.getString("database.driverClassName"));
			defDataSource.setUrl(config.getString("database.url"));
			defDataSource.setUsername(config.getString("database.username"));
			defDataSource.setPassword(config.getString("database.password"));
			defDataSource.setMaxActive(Integer.valueOf(config.getString("database.maxActive")));
			defDataSource.setMaxIdle(Integer.valueOf(config.getString("database.maxIdle")));
			defDataSource.setMaxWait(Long.valueOf(config.getString("database.maxIdle")));
			defDataSource.setInitialSize(Integer.valueOf(config.getString("database.initialSize")));
			defDataSource.setTestWhileIdle(true);
			System.out.println("default: "+defDataSource.getUrl());
		}
		template.setDataSource(defDataSource);
		//查询type对应的数据库
		BasicDataSource targetDataSource = getDataSource(type);
		targetDataSources.put(type, targetDataSource);
		
		return targetDataSource;
	}
	
	//从dbs中取得type对应的数据库
	public BasicDataSource getDataSource(String dbtype) {
		List<Map<String, Object>> list = template.queryForList(
				"SELECT * FROM dbs WHERE active = 'y' and type = ?", dbtype);
		if (null != list && !list.isEmpty()) {
			final int size = list.size();
			//随即取出数据源
			int dbIndex = (int)(Math.random()*size);
			Map<String, Object> item = list.get(dbIndex);
			String url = "jdbc:mysql://" + item.get("ip") + ":"
					+ item.get("port") + "/" + item.get("db_name")
					+ "?useUnicode=true&characterEncoding=utf-8";
			BasicDataSource dataSource = createDataSource(url);
			return dataSource;
		}

		return null;
	}
	
	//创建数据源实体
	private BasicDataSource createDataSource(String url) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(config.getString("database.driverClassName"));
		dataSource.setUrl(url);
		dataSource.setUsername(config.getString("database.username"));
		dataSource.setPassword(config.getString("database.password"));
		dataSource.setMaxActive(Integer.valueOf(config.getString("database.maxActive")));
		dataSource.setMaxIdle(Integer.valueOf(config.getString("database.maxIdle")));
		dataSource.setMaxWait(Long.valueOf(config.getString("database.maxIdle")));
		dataSource.setInitialSize(Integer.valueOf(config.getString("database.initialSize")));
		dataSource.setTestWhileIdle(true);
		System.out.println("create new datasource url : "+url);
		return dataSource;
	}
}
