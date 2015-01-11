package com.aiteu.reading.core.tools;

public class StringUtil {
	
	public static String escape(String str){
		if(null == str) return "";
		return "'"+str+"'";
	}
}
