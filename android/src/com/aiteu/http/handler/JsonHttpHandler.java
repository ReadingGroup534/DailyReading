package com.aiteu.http.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.aiteu.http.inteface.HttpHandler;
import com.aiteu.http.util.ConvertUtil;

public class JsonHttpHandler implements HttpHandler{
	
	public JsonHttpHandler(){
		
	}

	@Override
	public InputStream doGet(String url) {
		// TODO Auto-generated method stub
		InputStream in = null;
		HttpGet getRequest = new HttpGet(url);
		getRequest.addHeader("charset", HTTP.UTF_8);
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 8000);
		HttpResponse response = null;
		try {
			response = client.execute(getRequest);
			if(null != response && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				in = response.getEntity().getContent();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return in;
	}

	@Override
	public InputStream doPost(String url, Map<String, String> params) {
		// TODO Auto-generated method stub
		InputStream in = null;
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("accept", "*/*");
		httpPost.setHeader("accept-language", "zh-CN");
		httpPost.setHeader("accept-encoding", "utf-8, deflate");
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 8000);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		Iterator<String> iter = params.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			String value = params.get(key);
			pairs.add(new BasicNameValuePair(key, value));
		}
		HttpResponse response = null;
		try{
			httpPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
			response = client.execute(httpPost);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				in = response.getEntity().getContent();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return in;
	}
	
	/**
	 * change stream to json object
	 * @param url
	 * @param params
	 * @return
	 */
	public JSONObject getJson(String url, Map<String, String> params){
		InputStream in = null;
		JSONObject json = null;
		if(params == null){
			in = doGet(url);
		}else{
			in = doPost(url, params);
		}
		if(in == null){
			json = ConvertUtil.convert2Json("error", "network inavailable");
			return json;
		}
		String jsonStr = ConvertUtil.convertStream2String(in);
		try {
			json = new JSONObject(jsonStr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = ConvertUtil.convert2Json("error", "build json error");
		}
		return json;
	}
}
