package com.aiteu.dailyreading.reader;

import android.graphics.Bitmap;

import com.aiteu.http.inteface.IBookReader;

/**
 * 在线书籍阅读器
 * @author liwei
 *
 */
public class OnlineBookReader implements IBookReader{

	@Override
	public boolean openbook(String url) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BookPage readNextPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookPage readPrevPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closebook() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBgBitmap(Bitmap BG) {
		// TODO Auto-generated method stub
		
	}

}
