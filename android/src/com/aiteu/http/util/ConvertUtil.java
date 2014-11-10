package com.aiteu.http.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

public class ConvertUtil {
	
	public static String convertStream2String(InputStream in){
		String content = "";
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] bytes = new byte[1024];
		int ch = -1;
		try{
			while((ch = in.read(bytes)) != -1){
				bos.write(bytes, 0, ch);
			}
			content = bos.toString("utf8");
		}catch(IOException e){
			e.printStackTrace();
		}
		return content;
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
