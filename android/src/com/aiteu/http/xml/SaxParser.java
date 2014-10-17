package com.aiteu.http.xml;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.aiteu.dailyreading.book.*;
import com.aiteu.http.inteface.ArticleParser;

public class SaxParser implements ArticleParser{

	private SAXParserFactory saxParserFactory =null ;
	private SAXParser saxParser = null;
	@Override
	public XmlDocument readXML(InputStream is) throws Exception {
		// TODO Auto-generated method stub
		XmlDocument document = new XmlDocument();
		if (saxParser == null) {
			return document;
		}
		try {
			// 取得SAXParserFactory实例
			saxParserFactory = SAXParserFactory.newInstance();
			// 从factory获取SAXParser实例
			saxParser = saxParserFactory.newSAXParser();
			 //设置解析器的相关特性，true表示开启命名空间特性 
//            saxParser.setProperty("http://xml.org/sax/features/namespaces",true);
            //实例化自定义的Handler
            XmlDocumentHandler handler = new XmlDocumentHandler();
            saxParser.parse(is, handler);
            return document;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String serialize(List<XmlDocument> books) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
