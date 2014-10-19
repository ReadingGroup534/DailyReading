package com.aiteu.http.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * 类说明： Stream 工具类.要是包含图片的话 可以使用
 */
public class StreamUtils {

	/**
	 * InputStream转化为byte数组
	 * 
	 * @param is
	 * @return
	 */
	public static byte[] toByte(InputStream is) throws IOException {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;
	}
	
	/**
	 * String转化为ByteArrayInputStream
	 * @param s
	 * @return
	 */
	public static InputStream String2InputStream(String s) {
	    if (s != null && !s.equals("")) {
	        try {
	            ByteArrayInputStream stringInputStream = new ByteArrayInputStream(
	                    s.getBytes());
	            return stringInputStream;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return null;
	}

	/**
	 * inputStream 转换成String
	 * @param is
	 * @return
	 */
	public static String inputStream2String(InputStream is){
		   BufferedReader in = new BufferedReader(new InputStreamReader(is));
		   StringBuffer buffer = new StringBuffer();
		   String line = "";
		   try {
			while ((line = in.readLine()) != null){
			     buffer.append(line);
			  }
		   } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return buffer.toString();
	}
	
	/**
	 * InputStream --> File
	 * 
	 * @param ins
	 * @param file
	 */
	public void inputstreamtofile(InputStream ins, File file) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = ins.read(buffer, 0, 1024)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				ins.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
	public static void copy(final InputStream in, final OutputStream out) throws IOException {
		final int buffer_size = 1024;
		byte[] buffer = new byte[buffer_size];
		
		int count = 0;
		while ((count = in.read(buffer)) >= 0)
			out.write(buffer, 0, count);
	}

	public static void close(InputStream in) {
		try {
			if (in != null) {
				in.close();
				in = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void close(OutputStream out) {
		try {
			if (out != null) {
				out.close();
				out = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void close(BufferedReader in) {
		try {
			if (in != null) {
				in.close();
				in = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}