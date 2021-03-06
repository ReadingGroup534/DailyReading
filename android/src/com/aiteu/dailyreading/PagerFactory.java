package com.aiteu.dailyreading;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.Vector;

import com.aiteu.dailyreading.exception.ReadingIOException;
import com.aiteu.http.util.StreamUtils;
import com.aiteu.http.xml.XmlDocument;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.text.format.Time;
import android.util.Log;

public class PagerFactory {

	private final static String TAG = "pagefactory";
	private URL article_url = null;
	private File book_file = null;
	private int m_backColor = 0xffff9e85; // 背景颜色
	private Bitmap m_article_bg = null;
	private int m_fontSize = 24;
	private boolean m_isfirstPage, m_islastPage;

	private Vector<String> m_lines = new Vector<String>();

	private MappedByteBuffer m_mbBuf = null;// 内存中的图书字符
	private int m_mbBufBegin = 0;// 当前页起始位置
	private int m_mbBufEnd = 0;// 当前页终点位置

	private int m_mbBufLen = 0; // 文章总长度

	private String m_strCharsetCode = "utf-8";

	private int m_textColor = Color.BLACK;// rgb(28,28,28);

	private int marginHeight = 15; // 上下与边缘的距离
	private int marginWidth = 15; // 左右与边缘的距离

	private int mHeight;	// 屏幕高度
	private int mWidth;		// 屏幕宽度

	private int mLineCount; // 每页可以显示的行数
	private Paint mPaint;

	private int mVisibleHeight; // 绘制内容的高
	private int mVisibleWidth; // 绘制内容的宽
	private int lineHeight;	//每行的高度
	
	public PagerFactory(int w, int h) {
		this.mWidth = w;
		this.mHeight = h;
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTextAlign(Align.LEFT);
		mPaint.setTextSize(m_fontSize);
		mPaint.setColor(m_textColor);

		mVisibleWidth = mWidth - marginWidth * 2;
		mVisibleHeight = mHeight - marginHeight * 2;
		lineHeight = m_fontSize + 6;
		mLineCount = mVisibleHeight / m_fontSize - 1; // 可显示的行数
//		mLineCount = mVisibleHeight / lineHeight ;
		m_isfirstPage = true;
		m_islastPage = false;
	}

	public void openbook(String strFilePath) throws IOException {
		book_file = new File(strFilePath);
		long lLen = book_file.length();
		m_mbBufLen = (int) lLen;
		// 内存映射文件,getChannel.map(FileChannel.MapMode mode, long position, long
		// size)将此通道的文件区域直接映射到内存中。
		m_mbBuf = new RandomAccessFile(book_file, "r").getChannel().map(
				FileChannel.MapMode.READ_ONLY, 0, lLen);
	}

	public void openXmlDoc(XmlDocument doc) throws ReadingIOException {
		long lLen = doc.toString().length();
		m_mbBufLen = (int) lLen;
		System.out.println(StreamUtils.String2InputStream(doc.toString()));
	}


	protected byte[] readParagraphBack(int nFromPos) {
		int nEnd = nFromPos;
		int i;
		byte b0, b1;
		if (m_strCharsetCode.equals("UTF-16LE")) {
			i = nEnd - 2;
			while (i > 0) {
				b0 = m_mbBuf.get(i);
				b1 = m_mbBuf.get(i + 1);
				if (b0 == 0x0a && b1 == 0x00 && i != nEnd - 2) {
					i += 2;
					break;
				}
				i--;
			}

		} else if (m_strCharsetCode.equals("UTF-16BE")) {
			i = nEnd - 2;
			while (i > 0) {
				b0 = m_mbBuf.get(i);
				b1 = m_mbBuf.get(i + 1);
				if (b0 == 0x00 && b1 == 0x0a && i != nEnd - 2) {
					i += 2;
					break;
				}
				i--;
			}
		} else {
			i = nEnd - 1;
			while (i > 0) {
				b0 = m_mbBuf.get(i);
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
			buf[j] = m_mbBuf.get(i + j);
		}
		return buf;
	}

	// 读取上一段落
	protected byte[] readParagraphForward(int nFromPos) {
		int nStart = nFromPos;
		int i = nStart;
		byte b0, b1; // 根据编码格式判断换行
		if (m_strCharsetCode.equals("UTF-16LE")) {
			while (i < m_mbBufLen - 1) {
				b0 = m_mbBuf.get(i++);
				b1 = m_mbBuf.get(i++);
				if (b0 == 0x0a && b1 == 0x00) {
					break;
				}
			}
		} else if (m_strCharsetCode.equals("UTF-16BE")) {
			while (i < m_mbBufLen - 1) {
				b0 = m_mbBuf.get(i++);
				b1 = m_mbBuf.get(i++);
				if (b0 == 0x00 && b1 == 0x0a) {
					break;
				}
			}
		} else {
			while (i < m_mbBufLen) {
				b0 = m_mbBuf.get(i++);
				if (b0 == 0x0a) {
					break;
				}
			}
		}
		int nParaSize = i - nStart;
		byte[] buf = new byte[nParaSize];
		for (i = 0; i < nParaSize; i++) {
			buf[i] = m_mbBuf.get(nFromPos + i);
		}
		return buf;
	}

	protected Vector<String> pageDown() {
		mPaint.setTextSize(m_fontSize);
		mPaint.setColor(m_textColor);
		String strParagraph = "";
		Vector<String> lines = new Vector<String>();
		while (lines.size() < mLineCount && m_mbBufEnd < m_mbBufLen) {
			byte[] paraBuf = readParagraphForward(m_mbBufEnd); // 读取一个段落
			m_mbBufEnd += paraBuf.length;// 每次读取后，记录结束点位置，该位置是段落结束位置
			try {
				strParagraph = new String(paraBuf, m_strCharsetCode);
			} catch (UnsupportedEncodingException e) {
				// TODO: handle exception
				Log.e(TAG, "字符转换失败！");
			}
			String strReturn = "";
			// 替换掉回车换行
			if (strParagraph.indexOf("\r\n") != -1) {
				strReturn = "\r\n";
				strParagraph = strParagraph.replaceAll("\r\n", "");
			} else if (strParagraph.indexOf("\n") != -1) {
				strReturn = "\n";
				strParagraph = strParagraph.replaceAll("\n", "");
			}

			if (strParagraph.length() == 0) {
				lines.add(strParagraph);
			}

			while (strParagraph.length() > 0) {
				// 画一行文字
				int mLines = mPaint.breakText(strParagraph, true,
						mVisibleWidth, null);
				lines.add(strParagraph.substring(0, mLines));
				// 得到剩余的文字
				strParagraph = strParagraph.substring(mLines);
				// 超出最大行数就不在话
				if (lines.size() >= mLineCount) {
					break;
				}
			}
			// 如果该页最后一段只显示了一部分，则从新定位结束点位置
			if (strParagraph.length() != 0) {
				try {
					m_mbBufEnd -= (strParagraph + strReturn)
							.getBytes(m_strCharsetCode).length;
				} catch (UnsupportedEncodingException e) {
					Log.e(TAG, "pageDown->记录结束点位置失败", e);
				}
			}
		}
		return lines;
	}

	protected void pageUp() {
		// TODO Auto-generated method stub
		if (m_mbBufBegin < 0)
			m_mbBufBegin = 0;
		Vector<String> lines = new Vector<String>();
		String strParagraph = "";
		while (lines.size() < mLineCount && m_mbBufBegin > 0) {
			Vector<String> paraLines = new Vector<String>();
			byte[] paraBuf = readParagraphBack(m_mbBufBegin);
			m_mbBufBegin -= paraBuf.length;// 每次读取一段后,记录开始点位置,是段首开始的位置
			try {
				strParagraph = new String(paraBuf, m_strCharsetCode);
			} catch (UnsupportedEncodingException e) {
				Log.e(TAG, "pageUp->转换编码失败", e);
			}
			strParagraph = strParagraph.replaceAll("\r\n", "");
			strParagraph = strParagraph.replaceAll("\n", "");
			// 如果是空白行，直接添加
			if (strParagraph.length() == 0) {
				paraLines.add(strParagraph);
			}
			while (strParagraph.length() > 0) {
				// 画一行文字
				int nSize = mPaint.breakText(strParagraph, true, mVisibleWidth,
						null);
				paraLines.add(strParagraph.substring(0, nSize));
				strParagraph = strParagraph.substring(nSize);
			}
			lines.addAll(0, paraLines);
		}

		while (lines.size() > mLineCount) {
			try {
				m_mbBufBegin += lines.get(0).getBytes(m_strCharsetCode).length;
				lines.remove(0);
			} catch (UnsupportedEncodingException e) {
				Log.e(TAG, "pageUp->记录起始点位置失败", e);
			}
		}
		m_mbBufEnd = m_mbBufBegin;// 上上一页的结束点等于上一页的起始点
		return;
	}

	protected void prePage() throws IOException {
		if (m_mbBufBegin <= 0) {
			m_mbBufBegin = 0;
			m_isfirstPage = true;
			return;
		} else
			m_isfirstPage = false;
			m_lines.clear();
			pageUp();
			m_lines = pageDown();
	}

	public void nextPage() throws IOException {
		if (m_mbBufEnd >= m_mbBufLen) {
			m_islastPage = true;
			return;
		} else
			m_islastPage = false;
			m_lines.clear();
			m_mbBufBegin = m_mbBufEnd;
			m_lines = pageDown();
	}

	public void onDraw(Canvas c) {
		if (m_lines.size() == 0)
			m_lines = pageDown();
		if (m_lines.size() > 0) {
			if (m_article_bg == null)
				c.drawColor(m_backColor);
			else
				c.drawBitmap(m_article_bg, 0, 0, null);
			int y = marginHeight;
			for (String strLine : m_lines) {
				y += m_fontSize;
				c.drawText(strLine, marginWidth, y, mPaint);
			}
		}
		float fPercent = (float) (m_mbBufBegin * 1.0 / m_mbBufLen);
		DecimalFormat df = new DecimalFormat("#0.0");
		String strPercent = df.format(fPercent * 100) + "%";
		
		Time time = new Time();
		time.setToNow();
		String timeString;
		if (time.minute < 10) {
			timeString = "" + time.hour + " : 0" + time.minute;
		}else {
			timeString = "" + time.hour + ": " + time.minute;
		}
		int nPercentWidth = (int) mPaint.measureText("999.9%") + 2;
		
		c.drawText(timeString, marginWidth/2, mHeight - 2, mPaint);
		c.drawText(strPercent, mWidth - nPercentWidth, mHeight - 2, mPaint);
	}

	public void setBgBitmap(Bitmap BG) {
		m_article_bg = Bitmap.createScaledBitmap(BG, mWidth, mHeight, true);
	}

	public boolean isM_firstPage() {
		return m_isfirstPage;
	}

	public boolean isM_lastPage() {
		return m_islastPage;
	}

	public void setM_fontSize(int m_fontSize) {
		this.m_fontSize = m_fontSize;
		mLineCount = mVisibleHeight / m_fontSize - 1;
	}

	// 设置页面起始点
	public void setM_mbBufBegin(int m_mbBufBegin) {
		this.m_mbBufBegin = m_mbBufBegin;
	}

	// 设置页面结束点
	public void setM_mbBufEnd(int m_mbBufEnd) {
		this.m_mbBufEnd = m_mbBufEnd;
	}

	public int getM_mbBufBegin() {
		return m_mbBufBegin;
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

	public int getM_mbBufLen() {
		return m_mbBufLen;
	}

	public int getM_mbBufEnd() {
		return m_mbBufEnd;
	}

	public int getM_fontSize() {
		return m_fontSize;
	}

	public int getmLineCount() {
		return mLineCount;
	}

	public void currentPage() throws IOException {
		m_lines.clear();
		m_lines = pageDown();
	}
}
