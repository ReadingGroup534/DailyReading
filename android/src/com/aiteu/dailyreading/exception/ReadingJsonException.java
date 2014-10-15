package com.aiteu.dailyreading.exception;

/**
 * 其他异常类
 * @author liyangchao
 *
 */
public class ReadingJsonException extends ReadingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReadingJsonException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
		this.errorCode = JSON_ERROR;
	}

	public ReadingJsonException(String msg, Exception cause) {
		super(msg, cause);
		this.errorCode = JSON_ERROR;
	}
	
    
}
