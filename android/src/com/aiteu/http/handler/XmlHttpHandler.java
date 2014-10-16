package com.aiteu.http.handler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import com.aiteu.http.factory.HttpHandler;
import com.aiteu.http.xml.XmlDocument;

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
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public InputStream doPost(String url, Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public XmlDocument getXml(String url){
		InputStream xmlStream = doGet(url);
	}
}
