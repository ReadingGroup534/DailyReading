package com.aiteu.http.util;

import java.io.File;

public class FileUtils {
	
	public static boolean fileExists(String filePath){
		File file = new File(filePath);
		return file.exists();
	}
}
