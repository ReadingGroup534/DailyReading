package com.aiteu.http.handler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.aiteu.http.inteface.HttpHandler;
import com.aiteu.http.xml.XmlDocument;
import com.aiteu.http.xml.XmlParser;
import com.aiteu.http.xml.XmlParser.ParserType;

public class XmlHttpHandler implements HttpHandler{

	@Override
	public InputStream doGet(String url) {
		// TODO Auto-generated method stub
		URL xmlUrl = null;
		HttpURLConnection conn = null;
		InputStream in = null;
		try{
			xmlUrl = new URL(url);
			conn = (HttpURLConnection)xmlUrl.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("get");
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type", "text/html;charset=UTF-8");
			conn.setRequestProperty("Connection", "Keep-Alive"); 
			if(conn.getResponseCode() == 200){
				in = conn.getInputStream();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return in;
	}

	@Override
	public InputStream doPost(String url, Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 默认使用sax解析
	 * @param url
	 * @return
	 */
	public XmlDocument getXml(String url){
		InputStream xmlStream = doGet(url);
		if(xmlStream == null){
			return null;
		}
		XmlParser parser = XmlParser.getParser(ParserType.SAX_PARSER);
		return parser.getDocument(xmlStream);
	}
	
	public XmlDocument getXml(String url, ParserType pt){
		InputStream xmlStream = doGet(url);
		if(xmlStream == null){
			return null;
		}
		XmlParser parser = XmlParser.getParser(pt);
		return parser.getDocument(xmlStream);
	}
	
	/**
	 * 默认使用sax解析
	 * @param in
	 * @return
	 */
	public XmlDocument getXml(InputStream in){
		if(in == null){
			return null;
		}
		XmlParser parser = XmlParser.getParser(ParserType.SAX_PARSER);
		return parser.getDocument(in);
	}
	
	public XmlDocument getXml(InputStream in,ParserType pt){
		if(in == null){
			return null;
		}
		XmlParser parser = XmlParser.getParser(pt);
		return parser.getDocument(in);
	}
}
