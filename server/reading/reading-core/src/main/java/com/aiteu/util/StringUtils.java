package com.aiteu.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
	
	public static String fileExtension(String filename){
		int index = filename.lastIndexOf('.');
		if(index == -1){
			return null;
		}
		return filename.substring(index+1);
	}
	
	public static String filePrefix(String filename){
		int index = filename.lastIndexOf('.');
		if(index == -1){
			return null;
		}
		return filename.substring(0, index);
	}
	
	public static String dateToString(long date){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return df.format(new Date(date));
	}
}
