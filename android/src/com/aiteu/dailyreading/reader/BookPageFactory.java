package com.aiteu.dailyreading.reader;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

/**
 * 定义页面工程：产生页面模型,字体，大小
 * @author liwei
 *
 */
public class BookPageFactory {
	
	private static BookPageFactory mFactory = null;
	private Paint mTextPaint = null;
	private int mPageWidth ;// = 800;
	private int mPageHeight;
	private int textSize = 24;
	private int mLineCount; // 每页可以显示的行数
	private int lineHeight;	//每行的高度
	private int marginHeight = 15; // 上下与边缘的距离
	private int marginWidth = 15; // 左右与边缘的距离
	private int mVisibleHeight; // 绘制内容的高
	private int mVisibleWidth; // 绘制内容的宽
	private int mTextColor = Color.rgb(0x43, 0x43, 0x43);
	private int lines = 3; //每页包含的行数
	
	private BookPageFactory(){
		mTextPaint = new Paint();
		mTextPaint.setTextSize(textSize);
		mTextPaint.setTextAlign(Align.LEFT);
		mTextPaint.setColor(mTextColor);
		lineHeight = textSize + 6;
		mTextPaint.setAntiAlias(true);
		mVisibleWidth = mPageWidth - marginWidth * 2;
		mVisibleHeight = mPageHeight - marginHeight * 2;
		mLineCount = mVisibleHeight / lineHeight -2;
	}
	
	private BookPageFactory(int screenWidth, int screenHeight){
		mTextPaint = new Paint();
		mTextPaint.setTextSize(textSize);
		mTextPaint.setTextAlign(Align.LEFT);
		mTextPaint.setColor(mTextColor);
		lineHeight = textSize + 6;
		mTextPaint.setAntiAlias(true);
		this.mPageWidth = screenWidth;
		this.mPageHeight = screenHeight;
		mVisibleWidth = mPageWidth - marginWidth * 2;
		mVisibleHeight = mPageHeight - marginHeight * 2;
		mLineCount = mVisibleHeight / lineHeight -2;
	}
	
	public static BookPageFactory create(int w, int h){
		if(mFactory == null){
			synchronized(BookPageFactory.class){
				if (mFactory == null) {
					mFactory = new BookPageFactory(w,h);
				}
			}
		}
		return mFactory;
	}
	
	public BookPage createPage(){
		BookPage mPage = new BookPage();
		
		return mPage;
	}

	
	public int getMarginHeight() {
		return marginHeight;
	}

	public void setMarginHeight(int marginHeight) {
		this.marginHeight = marginHeight;
	}

	public int getMarginWidth() {
		return marginWidth;
	}

	public void setMarginWidth(int marginWidth) {
		this.marginWidth = marginWidth;
	}

	public int getLines() {
		return mLineCount;
	}

	public void setLines(int lines) {
		this.mLineCount = lines;
	}
	
	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}
	
	public int getTextColor(){
		return this.mTextColor;
	}
	
	public void setTextColor(int color){
		this.mTextColor = color;
	}

	public int spiltParagraphToLine(String paragraph){
		return mTextPaint.breakText(paragraph, true, mVisibleWidth, null);
	}
}
