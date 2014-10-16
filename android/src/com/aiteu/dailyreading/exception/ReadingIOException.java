package com.aiteu.dailyreading.exception;

/**
 * IO 异常类
 * @author liyangchao
 *
 */
public class ReadingIOException extends ReadingException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReadingIOException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
		this.errorCode = CONNECTED_ERROR;
	}
	
	public ReadingIOException(String msg, Exception cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
		this.errorCode = CONNECTED_ERROR;
	}

}
