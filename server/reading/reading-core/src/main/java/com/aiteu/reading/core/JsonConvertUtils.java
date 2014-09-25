package com.aiteu.reading.core;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


public class JsonConvertUtils {
	
	public static void outJson(HttpServletResponse res, Object data){
		try {
			res.getWriter().write(new Gson().toJson(data));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
