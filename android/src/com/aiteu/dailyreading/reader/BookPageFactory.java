package com.aiteu.dailyreading.reader;

/**
 * 定义页面工程：产生页面模型
 * @author liwei
 *
 */
public class BookPageFactory {
	
	private static BookPageFactory mFactory = null;
	
	private BookPageFactory(){
		
	}
	
	public static synchronized BookPageFactory create(){
		if(mFactory == null){
			mFactory = new BookPageFactory();
		}
		return mFactory;
	}
	
	private int lines = 3; //每页包含的行数
	private int wordsOfLine = 8; //每行的字数
	
	public BookPage createPage(){
		BookPage mPage = new BookPage();
		
		return mPage;
	}

	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	public int getWordsOfLine() {
		return wordsOfLine;
	}

	public void setWordsOfLine(int wordsOfLine) {
		this.wordsOfLine = wordsOfLine;
	}
}
