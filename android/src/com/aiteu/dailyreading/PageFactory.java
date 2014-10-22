package com.aiteu.dailyreading;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import com.aiteu.http.factory.XmlHttpFactory;
import com.aiteu.http.handler.XmlHttpHandler;
import com.aiteu.http.xml.ArticlePart;
import com.aiteu.http.xml.XmlDocument;

import android.R.bool;
import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.Log;

public class PageFactory {

	private final static String TAG = "pagefactory";
	private URL article_url = null;
	private File book_file = null;
	private ArticlePart aPart; //需要处理的文档对象
	private XmlDocument xmlDocument;
	private int m_backColor ;  // 背景颜色
	private Bitmap m_article_bg;
	private int m_fontSize;
	private boolean m_isfirstPage, m_islastPage;
	
	private Vector<String> m_lines ;
	private int lineCount;	//一篇文章在当前配置下有多少行
	private MappedByteBuffer m_mbBuf = null;// 内存中的图书字符
	private int charBegin,charEnd;   //每一页中第一个字符与最后一个字符在文章中的位置

	private int documentLength; // 文章总长度
	private String content;

	private String m_strCharsetCode = "utf-8";

	private int m_textColor;//rgb(28,28,28);

	private int marginHeight = 15; // 上下与边缘的距离
	private int marginWidth = 15; // 左右与边缘的距离

	private int mHeight;
	private int mWidth;

	private int mLineCount; // 每页可以显示的行数
	private Paint mPaint;

	private float mVisibleHeight; // 绘制内容的高
	private float mVisibleWidth; // 绘制内容的宽
	private int mlineHeight ;  //每一行的高度
	
	private Vector<Vector<String>> page;
	int pageNum;
	
	public PageFactory(int h, int w, XmlDocument document) {
		this.mHeight = h;
		this.mWidth = w;
		this.xmlDocument = document;
		initiate();
	}

	
	protected void initiate() {
		// TODO Auto-generated method stub
		m_backColor = 0xffff9e85;
		m_article_bg = null;
		m_fontSize = 20;
		m_isfirstPage = true;
		m_islastPage = false;
		m_textColor = Color.BLACK;
		content = xmlDocument.toString(); //提取文档的内容
		documentLength = content.length();
		
		charBegin = 0;
		charEnd = 0;
		mlineHeight = m_fontSize + 8;
		m_lines =  new Vector<String>();
		
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTextAlign(Align.LEFT);
		mPaint.setTextSize(m_fontSize);
		mPaint.setColor(m_textColor);
		
		mVisibleWidth = mWidth - marginWidth * 2;
		mVisibleHeight = mHeight - marginHeight * 2;
		
		page = new Vector<Vector<String>>();
		pageNum = -1;
	
		pageDown();
		
	}


	protected void pageDown() {
		mPaint.setColor(m_textColor);
		mPaint.setTextSize(m_fontSize);
		page.clear();
		int curPos = 0;
		while (curPos < documentLength) {
			Vector<String> lines = new Vector<String>();
			charBegin = curPos;
			while (lines.size() < lineCount && curPos < documentLength) {
				int i = content.indexOf("\n", curPos);
				String paragraphString = content.substring(curPos, i);
				if (curPos == i) {
					lines.add("");
				}
				while (paragraphString.length() > 0) {
					int horSize = mPaint.breakText(paragraphString, true, mVisibleWidth, null);
					lines.add(paragraphString.substring(0, horSize));
					paragraphString = paragraphString.substring(horSize);
					curPos += horSize;
					if (lines.size() > lineCount) {
						break;
					}
					
				}
				// 如果是把一整段读取完的话，需要给当前位置加1
				if (paragraphString.length() == 0) {
					curPos += "\n".length();
				}
			}
			page.add(lines);
		}
	}
	
	public boolean nextPage() {
		if (isM_lastPage()) {
			if (!nextArticlePart()) {
				return false;
			}
		}
		m_lines = page.get(++pageNum);
		return true;
	}


	public boolean nextArticlePart() {
		content = xmlDocument.toString();
		if (content == null) {
			return false;
		}
		content = xmlDocument.toString();
		
		documentLength = content.length();
		charBegin = 0;
		charEnd = 0;
		pageDown();
		pageNum = -1;
		return true;
	}


	public void draw(Canvas canvas) {
		if (m_lines.size() == 0) {
			
		}
	}
	
	public void setBgBitmap(Bitmap BG) {
		m_article_bg = BG;
	}

	public boolean isM_firstPage() {
		return m_isfirstPage;
	}


	public boolean isM_lastPage() {
		return m_islastPage;
	}

	public void setM_fontSize(int m_fontSize) {
		this.m_fontSize = m_fontSize;
		mLineCount = (int) (mVisibleHeight / m_fontSize) - 1;
	}
	

	public String getFirstLineText() {
		return m_lines.size() > 0 ? m_lines.get(0) : "";
	}

	public int getM_textColor() {
		return m_textColor;
	}

	public void setM_textColor(int m_textColor) {
		this.m_textColor = m_textColor;
	}

	
	public int getM_fontSize() {
		return m_fontSize;
	}

	public int getmLineCount() {
		return mLineCount;
	}

}
