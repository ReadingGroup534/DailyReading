package com.aiteu.reading.core;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.aiteu.reading.core.tools.Base64Tools;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest {
	public static void main(String[] args) {
		String str = "wo shi liwei asasasa aasasas asasasa a asasasa asasa s";
		String md5Str = Base64Tools.md5Code(Base64Tools.encode(str));
		System.out.println(md5Str);
		System.out.println(md5Str.length());
	}
}
