package com.aiteu.dailyreading.book;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.util.Log;

public class SAXBookParser implements BookParser {

	@Override
	public List<BookBean> readXML(InputStream is) throws Exception {
		// TODO Auto-generated method stub
		List<BookBean> books = null;
		try {
			// 取得SAXParserFactory实例
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// 从factory获取SAXParser实例
			SAXParser saxParser = factory.newSAXParser();
			// 实例化自定义Handler
			XMLContentHandler handler = new XMLContentHandler();
			saxParser.parse(is, handler);
			is.close();

			books = handler.getBookBeans();
			return books;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String serialize(List<BookBean> books) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	

}
