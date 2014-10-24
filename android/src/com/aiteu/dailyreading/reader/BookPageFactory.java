package com.aiteu.dailyreading.reader;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * 定义页面工程：产生页面模型,字体，大小
 * @author liwei
 *
 */
public class BookPageFactory {
	
	private static BookPageFactory mFactory = null;
	private Paint mTextPaint = null;
	private int mPageWidth = 100;
	private float textSize = 24;
	private int mTextColor = Color.rgb(0x43, 0x43, 0x43);
	private int lines = 3; //每页包含的行数
	
	private BookPageFactory(){
		mTextPaint = new Paint();
		mTextPaint.setTextSize(textSize);
		mTextPaint.setColor(mTextColor);
		mTextPaint.setAntiAlias(true);
	}
	
	public static synchronized BookPageFactory create(){
		if(mFactory == null){
			mFactory = new BookPageFactory();
		}
		return mFactory;
	}
	
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
	
	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}
	
	public int getTextColor(){
		return this.mTextColor;
	}
	
	public void setTextColor(int color){
		this.mTextColor = color;
	}

	public int spiltParagraphToLine(String paragraph){
		return mTextPaint.breakText(paragraph, true, mPageWidth, null);
	}
}
