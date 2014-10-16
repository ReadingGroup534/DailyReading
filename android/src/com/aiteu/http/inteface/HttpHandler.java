package com.aiteu.http.inteface;

import java.io.InputStream;
import java.util.Map;

/**
 * 定义能够处理的事物
 * @author liwei
 *
 */
public interface HttpHandler {
	/**
	 * get method request
	 * @param url request url
	 * @return
	 */
	public InputStream doGet(String url);
	
	/**
	 * post method request
	 * @param url request url
	 * @param params post params
	 * @return
	 */
	public InputStream doPost(String url, Map<String, String> params);
}
