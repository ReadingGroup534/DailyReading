package com.aiteu.http.handler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.aiteu.http.inteface.HttpHandler;
import com.aiteu.http.xml.SaxParser;
import com.aiteu.http.xml.XmlDocument;
import com.aiteu.http.xml.XmlParser;

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
	
	public XmlDocument getPullXml(String url){
		InputStream xmlStream = doGet(url);
		if(xmlStream == null){
			return null;
		}
		XmlParser parser = XmlParser.getParser();
		return parser.getDocument(xmlStream);
	}
	
	public XmlDocument getPullXml(InputStream in){
		if(in == null){
			return null;
		}
		XmlParser parser = XmlParser.getParser();
		return parser.getDocument(in);
	}
	
	public XmlDocument getSaXml(String url) {
		XmlDocument document = null;
		InputStream saxInputStream = doGet(url);
		
		if (saxInputStream == null) {
			return null;
		}
		SaxParser saxParser = new SaxParser();
		try {
			document = saxParser.readXML(saxInputStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}
	
	public XmlDocument getSaXml(InputStream in) {
		XmlDocument document = null;
		if (in == null) {
			return null;
		}
		SaxParser saxParser = new SaxParser();
		try {
			document = saxParser.readXML(in);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}
}
