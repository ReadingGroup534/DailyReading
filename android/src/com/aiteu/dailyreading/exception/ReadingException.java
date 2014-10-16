package com.aiteu.dailyreading.exception;

/**
 * 自定义异常类
 * @author liyangchao
 *
 */

public class ReadingException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//未知错误
	public static final String UNKNOWEN_ERROR = "-1";
	//链接超时错误
	public static final String CONNECTED_ERROR = "-1001";
	//解析JSON错误
	public static final String JSON_ERROR = "-1002";
	
	protected String errorCode = UNKNOWEN_ERROR;
	
	public ReadingException(String msg) {
		super(msg);
	}
	
	public ReadingException(String msg,Exception cause) {
		super(msg, cause);
	}
	
	public String getErrorCode(){
		return errorCode;
	}
}
