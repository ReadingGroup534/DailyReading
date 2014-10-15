package com.aiteu.http.util;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

public class ConvertUtil {
	
	public static String convertStream2String(InputStream in){
		StringBuffer buff = new StringBuffer();
		byte[] bytes = new byte[1024];
		int ch = -1;
		try{
			while((ch = in.read(bytes)) != -1){
				buff.append(new String(bytes, 0, ch));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return buff.toString();
	}
	
	public static JSONObject convert2Json(String status, String message){
		JSONObject json = new JSONObject();
		try {
			json.put("status", status);
			json.put("message", message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
