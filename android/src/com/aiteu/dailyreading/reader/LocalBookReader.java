package com.aiteu.dailyreading.reader;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;

import com.aiteu.http.util.FileUtils;

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
	
	private long fileLength = 0;
	
	
	public LocalBookReader(){
		mPageFactory = BookPageFactory.create();
		mReadingIndex = new ReadingIndex();
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
			mMemBuffer = mFileHandler.getChannel().map(MapMode.READ_ONLY, 0, mFileHandler.length());
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
		BookPage nextPage = mPageFactory.createPage();
		final int lines = mPageFactory.getLines();
		final int words = mPageFactory.getWordsOfLine();
		for(int i=0;i < lines;i++){
			nextPage.addOneLines(this.readNextLine(words).toString());
		}
		return nextPage;
	}

	@Override
	public BookPage readPrevPage() {
		// TODO Auto-generated method stub
		BookPage prevPage = mPageFactory.createPage();
		final int lines = mPageFactory.getLines();
		final int words = mPageFactory.getWordsOfLine();
		for(int i=0;i < lines;i++){
			prevPage.addOneLines(this.readPrevLine(words).reverse().toString());
		}
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

	class ReadingIndex{
		int start;
		int end;
		
		public ReadingIndex(){
			this.start = this.end = 0;
		}
	}
	
	/**
	 * 一行读取多少个字符
	 * @param words
	 * @return
	 */
	private StringBuffer readNextLine(int words){
		StringBuffer sb = new StringBuffer();
		int sIndex = mReadingIndex.end;
		byte b0;
		while(sIndex < fileLength){
			b0 = mMemBuffer.get(sIndex++);
			if(b0 == 0x0a){
				break;
			}
		}
		int nParaSize = sIndex - mReadingIndex.start;
		byte[] buf = new byte[nParaSize];
		for(int i=0 ;i < nParaSize;i++){
			buf[i] = mMemBuffer.get(mReadingIndex.start+i);
		}
		
		mReadingIndex.end = sIndex+nParaSize;
		Log.d(TAG, "end position: "+mReadingIndex.end);
		try {
			sb.append(new String(buf, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d(TAG, "------------"+sb.toString());
		return sb;
	}
	
	private StringBuffer readPrevLine(int words){
		StringBuffer sb = new StringBuffer();
		
		return sb;
	}

}
