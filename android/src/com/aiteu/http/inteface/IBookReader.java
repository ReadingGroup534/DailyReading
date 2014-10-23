package com.aiteu.http.inteface;

import com.aiteu.dailyreading.reader.BookPage;

/**
 * 阅读器所具有的功能
 * @author liwei
 *
 */
public interface IBookReader {
	
	/**
	 * 打开书
	 * @param filePath
	 */
	public boolean openbook(String url);
	
	/**
	 * 从上次读取的末尾向下读取一页
	 * @return
	 */
	public BookPage readNextPage();
	
	/**
	 * 从上次读取的位置向下读取一页
	 * @return
	 */
	public BookPage readPrevPage();
	
	/**
	 * 关闭书：清空资源
	 */
	public void closebook();
}
