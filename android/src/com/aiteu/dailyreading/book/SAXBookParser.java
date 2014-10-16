package com.aiteu.dailyreading.book;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.aiteu.http.inteface.ArticleParser;
import com.aiteu.http.xml.XmlDocument;


public class SAXBookParser implements ArticleParser {

	@Override
	public XmlDocument readXML(InputStream is) throws Exception {
		// TODO Auto-generated method stub
		XmlDocument documents = null;
		try {
			// 取得SAXParserFactory实例
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// 从factory获取SAXParser实例
			SAXParser saxParser = factory.newSAXParser();
			 //设置解析器的相关特性，true表示开启命名空间特性
//            saxParser.setProperty("http://xml.org/sax/features/namespaces",true);
			// 实例化自定义Handler
			XMLContentHandler handler = new XMLContentHandler();
			saxParser.parse(is, handler);
			is.close();
			return documents;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String serialize(List<XmlDocument> books) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
