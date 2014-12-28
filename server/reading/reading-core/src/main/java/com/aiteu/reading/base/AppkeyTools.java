package com.aiteu.reading.base;

import java.security.MessageDigest;

public final class AppkeyTools {
	public static final String ALGORITHM_MD5 = "MD5";
	public static final String ALGORITHM_SHA1 = "SHA1";

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String encode(String str) {
		return encode(ALGORITHM_SHA1, str);
	}

	public static String encode(String algorithm, String str){
		if(str == null){
			return null;
		}
		try{
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(str.getBytes());
			return getFormattedText(md.digest());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}
}
