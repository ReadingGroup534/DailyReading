package com.aiteu.http.inteface;

import java.io.InputStream;
import java.util.List;

import com.aiteu.http.xml.XmlDocument;

public interface ArticleParser {
	
	/**
	 * 解析接口，实现不同的解析方式，都得先继承该接口，便于扩展不同的解析方式
	 * @author liyangchao
	 */

	/**
	 * 解析输入流 得到Book对象集合
	 * @param is
	 * @return
	 * @throws Exception
	 */
	 public XmlDocument readXML(InputStream is) throws Exception; 
	 
	 
	 /**
	  * 序列化Book对象集合 得到XML形式的字符串 
	  * @param books
	  * @return
	  * @throws Exception
	  */
	 public String serialize(List<XmlDocument> books) throws Exception; 
}
