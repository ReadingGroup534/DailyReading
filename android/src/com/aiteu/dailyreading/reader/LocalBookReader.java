package com.aiteu.dailyreading.reader;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Vector;

import com.aiteu.http.inteface.IBookReader;
import com.aiteu.http.util.FileUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.format.Time;
import android.util.Log;

/**
 * 本地类文章的阅读
 * @author liwei
 *
 */
public class LocalBookReader implements IBookReader{
	private static final String TAG = "Reader";
	
	private RandomAccessFile mFileHandler = null; // 随机的读取文件内容句柄
	private MappedByteBuffer mMemBuffer = null; // 内存都写缓冲区
	private BookPageFactory mPageFactory = null;
	private ReadingIndex mReadingIndex = null; //都写开始和结束的标致位
	private BookPage page = null;
	private Bitmap m_article_bg = null;
	private int m_backColor = 0xffff9e85; // 背景颜色
	
	private long fileLength = 0;
	private String charset = "utf-8";
	private boolean isLastPage = false;
	private boolean isFirstPage = true;
	private float mReadPercent = 0;
	
	private int screenWidth;
	private int screenHeight;
	
	private Paint mPaint;
	
	public LocalBookReader(int w, int h){
		this.screenWidth = w;
		this.screenHeight = h;
		mPageFactory = BookPageFactory.create(screenWidth,screenHeight);
		mReadingIndex = new ReadingIndex();
		page = new BookPage();
		mPaint = new Paint();
	}
	
	

	@Override
	public boolean openbook(String filePath) {
		// TODO Auto-generated method stub
		Log.d(TAG, "openbook : "+filePath);
		if(!FileUtils.fileExists(filePath)){
			Log.d(TAG, "file not found: "+filePath);
			return false;
		}
		try{
			mFileHandler = new RandomAccessFile(filePath, "r");
			fileLength = mFileHandler.length();
			mMemBuffer = mFileHandler.getChannel().map(MapMode.READ_ONLY, 0, fileLength);
			Charset.forName("utf-8").decode(mMemBuffer);
			mMemBuffer.load();
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public BookPage readNextPage() {
		// TODO Auto-generated method stub
		if(mReadingIndex.end > fileLength){
			isLastPage = true;
			return null;
		}else{
			isLastPage = false;
		}
		mReadingIndex.start = mReadingIndex.end;
		
		BookPage nextPage = mPageFactory.createPage();
		final int lines = mPageFactory.getLines();
		int lineCount = 0;
		String mParagraph = "";
		while(lineCount < lines && mReadingIndex.end < fileLength){
			byte[] paraBuf = readParagraphForward(mReadingIndex.end);
			mReadingIndex.end += paraBuf.length; // 每次读取后，记录结束点位置，该位置是段落结束位置
			try{
				mParagraph = new String(paraBuf, charset);
			}catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
			String strReturn = "";
			// 替换掉回车换行
			if (mParagraph.indexOf("\r\n") != -1) {
				strReturn = "\r\n";
				mParagraph = mParagraph.replaceAll("\r\n", "");
			} else if (mParagraph.indexOf("\n") != -1) {
				strReturn = "\n";
				mParagraph = mParagraph.replaceAll("\n", "");
			}

			if (mParagraph.length() == 0) {
				nextPage.addOneLines(mParagraph);
				lineCount += 1;
			}
			
			while (mParagraph.length() > 0) {
				// 画一行文字
				int mLines = mPageFactory.spiltParagraphToLine(mParagraph);
				nextPage.addOneLines(mParagraph.substring(0, mLines));
				lineCount += 1;
				// 得到剩余的文字
				mParagraph = mParagraph.substring(mLines);
				// 超出最大行数就不在话
				if (lineCount >= lines) {
					break;
				}
			}
			// 如果该页最后一段只显示了一部分，则从新定位结束点位置
			if (mParagraph.length() != 0) {
				try {
					mReadingIndex.end -= (mParagraph + strReturn).getBytes(charset).length;
				} catch (UnsupportedEncodingException e) {
					Log.e(TAG, "pageDown->记录结束点位置失败", e);
				}
			}
		}
		caculatePercent();
		return nextPage;
	}

	@Override
	public BookPage readPrevPage() {
		// TODO Auto-generated method stub
		if(mReadingIndex.start <= 0){
			mReadingIndex.start = 0;
			isFirstPage = true;
			return null;
		}else{
			isFirstPage = false;
		}
		
		BookPage prevPage = mPageFactory.createPage();
		final int lines = mPageFactory.getLines();
		if (mReadingIndex.start < 0)
			mReadingIndex.start = 0;
		String strParagraph = "";
		int lineCount = 0;
		Vector<String> mTempLines = new Vector<String>();
		while (lineCount < lines && mReadingIndex.start > 0) {
			mTempLines.clear();
			byte[] paraBuf = readParagraphBack(mReadingIndex.start);
			mReadingIndex.start -= paraBuf.length;// 每次读取一段后,记录开始点位置,是段首开始的位置
			try {
				strParagraph = new String(paraBuf, charset);
			} catch (UnsupportedEncodingException e) {
				Log.e(TAG, "pageUp->转换编码失败", e);
			}
			strParagraph = strParagraph.replaceAll("\r\n", "");
			strParagraph = strParagraph.replaceAll("\n", "");
			// 如果是空白行，直接添加
			if (strParagraph.length() == 0) {
				mTempLines.add(strParagraph);
				lineCount += 1;
			}
			while (strParagraph.length() > 0) {
				// 画一行文字
				int nSize = mPageFactory.spiltParagraphToLine(strParagraph);
				mTempLines.add(strParagraph.substring(0, nSize));
				lineCount +=1;
				strParagraph = strParagraph.substring(nSize);
			}
			prevPage.getPageContent().addAll(0, mTempLines);
		}

		while (lineCount > lines) {
			try {
				mReadingIndex.start += prevPage.getPageContent().get(0).getBytes(charset).length;
				prevPage.getPageContent().remove(0);
				lineCount -= 1;
			} catch (UnsupportedEncodingException e) {
				Log.e(TAG, "pageUp->记录起始点位置失败", e);
			}
		}
		mReadingIndex.end = mReadingIndex.start;// 上上一页的结束点等于上一页的起始点
		caculatePercent();
		return prevPage;
	}

	@Override
	public void closebook() {
		// TODO Auto-generated method stub
		try{
			mFileHandler.close();
			mMemBuffer.clear();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void onDraw(Canvas c) {
		if (mPageFactory.getLines() == 0) {
			page = this.readNextPage();
		}
		if (mPageFactory.getLines() > 0) {
			if (m_article_bg == null) {
				c.drawColor(m_backColor);
			}else {
				c.drawBitmap(m_article_bg, 0, 0, mPaint);
			}
			int y = mPageFactory.getMarginHeight();
			for (String lineText : page.getPageContent()) {
				y += mPageFactory.getTextSize();
				c.drawText(lineText, mPageFactory.getMarginWidth(), y, mPaint);
			}
		}
		
		DecimalFormat dFormat = new DecimalFormat("#0.0");
		String percent = dFormat.format(this.getCurrentReadPercent() * 100) + "%";
		
		Time time = new Time();
		time.setToNow();
		String timeString;
		if (time.minute < 10) {
			timeString = "" + time.hour + " : 0" + time.minute;
		}else {
			timeString = "" + time.hour + ": " + time.minute;
		}
		int nPercentWidth = (int) mPaint.measureText("999.9%") + 2;
		
		c.drawText(timeString, mPageFactory.getMarginWidth()/2, mPageFactory.getMarginHeight() - 5, mPaint);
		c.drawText(percent, mPageFactory.getMarginWidth() - nPercentWidth, mPageFactory.getMarginHeight() - 5, mPaint);
	}

	class ReadingIndex{
		int start;
		int end;
		
		public ReadingIndex(){
			this.start = this.end = 0;
		}
	}
	
	/**
	 * 读取下一段
	 * @return
	 */
	private byte[] readParagraphForward(int fromPos){
		int nStart = fromPos;
		int i = nStart;
		byte b0, b1; // 根据编码格式判断换行
		if (charset.equals("UTF-16LE")) {
			while (i < fileLength - 1) {
				b0 = mMemBuffer.get(i++);
				b1 = mMemBuffer.get(i++);
				if (b0 == 0x0a && b1 == 0x00) {
					break;
				}
			}
		} else if (charset.equals("UTF-16BE")) {
			while (i < fileLength - 1) {
				b0 = mMemBuffer.get(i++);
				b1 = mMemBuffer.get(i++);
				if (b0 == 0x00 && b1 == 0x0a) {
					break;
				}
			}
		} else {
			while (i < fileLength) {
				b0 = mMemBuffer.get(i++);
				if (b0 == 0x0a) {
					break;
				}
			}
		}
		int nParaSize = i - nStart;
		byte[] buf = new byte[nParaSize];
		for (i = 0; i < nParaSize; i++) {
			buf[i] = mMemBuffer.get(fromPos + i);
		}
		return buf;
	}
	/**
	 * 读取上一段
	 * @param nFromPos
	 * @return
	 */
	protected byte[] readParagraphBack(int nFromPos) {
		int nEnd = nFromPos;
		int i;
		byte b0, b1;
		if (charset.equals("UTF-16LE")) {
			i = nEnd - 2;
			while (i > 0) {
				b0 = mMemBuffer.get(i);
				b1 = mMemBuffer.get(i + 1);
				if (b0 == 0x0a && b1 == 0x00 && i != nEnd - 2) {
					i += 2;
					break;
				}
				i--;
			}

		} else if (charset.equals("UTF-16BE")) {
			i = nEnd - 2;
			while (i > 0) {
				b0 = mMemBuffer.get(i);
				b1 = mMemBuffer.get(i + 1);
				if (b0 == 0x00 && b1 == 0x0a && i != nEnd - 2) {
					i += 2;
					break;
				}
				i--;
			}
		} else {
			i = nEnd - 1;
			while (i > 0) {
				b0 = mMemBuffer.get(i);
				if (b0 == 0x0a && i != nEnd - 1) {
					i++;
					break;
				}
				i--;
			}
		}
		if (i < 0)
			i = 0;
		int nParaSize = nEnd - i;
		int j;
		byte[] buf = new byte[nParaSize];
		for (j = 0; j < nParaSize; j++) {
			buf[j] = mMemBuffer.get(i + j);
		}
		return buf;
	}
	
	public boolean isLastPage(){
		return this.isLastPage;
	}
	
	public boolean isFirstPage(){
		return this.isFirstPage;
	}
	
	private void caculatePercent(){
		if(mReadingIndex.end > fileLength){
			mReadPercent = 0;
		}
		mReadPercent = mReadingIndex.end * 1.0f / fileLength;
	}
	
	public float getCurrentReadPercent(){
		return this.mReadPercent;
	}



	@Override
	public void setBgBitmap(Bitmap BG) {
		// TODO Auto-generated method stub
		m_article_bg = Bitmap.createScaledBitmap(BG, screenWidth, screenHeight, true);
	}
}
