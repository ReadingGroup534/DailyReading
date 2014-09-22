package com.aiteu.reading.web;

import java.util.ResourceBundle;

public class WebConstants {
	private static ResourceBundle config = ResourceBundle
			.getBundle("config/web-constants");
	
	/**
	 * 主server名，即首页。
	 */
	public static final String MAIN_SERVER = config
			.getString("main.server");
}
