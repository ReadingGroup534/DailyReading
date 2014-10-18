package com.aiteu.util;

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
}
