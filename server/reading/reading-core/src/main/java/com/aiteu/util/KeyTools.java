package com.aiteu.util;

import java.security.MessageDigest;

public class KeyTools {
	public static final String ALGORITHM_SHA1 = "SHA1";
	public static final String ALGORITHM_MD5 = "MD5";
	
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String encode(String algorithm, String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(algorithm.getBytes());
			return getFormattedText(md.digest());
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}
	
	/**
	 * packagename+privatekey
	 * @return
	 */
	public static String createKey(String str){
		return encode(ALGORITHM_SHA1, str);
	}
	
	public static void main(String[] args) {
		System.out.println(encode("MD5", "12345678"));
		System.out.println(encode("SHA1", "12345678"));
	}
}
