package com.aiteu.dbs;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 主要用来产生数据源
 * @author liwei
 *
 */
public class DbsFactory {
	
	private JdbcTemplate template = null;
	private Map<String, BasicDataSource> dataSources = null;
	//读取jdbc配置文件信息/config/jdbc
	private static ResourceBundle config = ResourceBundle.getBundle("config/jdbc");
	
	public DbsFactory(){
		template = new JdbcTemplate();
		dataSources = new HashMap<String, BasicDataSource>();
	}
	
	public JdbcTemplate getTemplate(String type){
		if(dataSources.containsKey(type)){
			template.setDataSource(dataSources.get(type));
			return template;
		}
		BasicDataSource dataSource = selectDataSource(type);
		template.setDataSource(dataSource);
		return template;
	}
	
	public BasicDataSource selectDataSource(String type){
		BasicDataSource defDataSource = new BasicDataSource();
		defDataSource.setDriverClassName(config.getString("database.driverClassName"));
		defDataSource.setUrl(config.getString("database.url"));
		defDataSource.setUsername(config.getString("database.username"));
		defDataSource.setPassword(config.getString("database.password"));
		defDataSource.setMaxActive(Integer.valueOf(config.getString("database.maxActive")));
		defDataSource.setMaxIdle(Integer.valueOf(config.getString("database.maxIdle")));
		defDataSource.setMaxWait(Long.valueOf(config.getString("database.maxIdle")));
		defDataSource.setInitialSize(Integer.valueOf(config.getString("database.initialSize")));
		defDataSource.setTestWhileIdle(true);
		return defDataSource;
	}
}
