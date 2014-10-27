package com.aiteu.dailyreading;

import com.aiteu.dailyreading.reader.BookPage;
import com.aiteu.dailyreading.reader.LocalBookReader;
import com.aiteu.log.LogTools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Toast;

public class ReadLocalPager extends Activity{

	private static final String TAG = "ReadTest";
	private Context mContext = null;
	private Bitmap mCurPageBitmap, mNextPageBitmap; // 當前頁面和下一個頁面的畫布
	private Canvas mCurCanvas, mNextCanvas;
	private PageWidget mPageWidget;
	private int screenHeight;
	private int screenWidth;
	
	private LocalBookReader localBookReader; 
	private BookPage page;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		DisplayMetrics dMetrics = getResources().getDisplayMetrics();
		screenHeight = dMetrics.heightPixels;
		screenWidth = dMetrics.widthPixels;
		
		LogTools.getInstance().info("screenHeight:" + screenHeight + "screenWidth:" + screenWidth);
		
		mCurPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight,
				Config.ARGB_8888);
		mNextPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight,
				Config.ARGB_8888);
		mCurCanvas = new Canvas(mCurPageBitmap);
		mNextCanvas = new Canvas(mNextPageBitmap);
		
		localBookReader = new LocalBookReader(screenWidth,screenHeight);
		
		localBookReader.setBgBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.day_bg));
		
		localBookReader.onDraw(mCurCanvas);
		
		mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
		
		//关闭GPU 渲染  防止在4.0以上真机翻页错乱
		if (Build.VERSION.SDK_INT >= 14) {
				mPageWidget.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		
		mPageWidget.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				boolean ret = false;
				if (v == mPageWidget) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						if (event.getY() > screenHeight) {
							return false;
						}
						mPageWidget.abortAnimation();
						mPageWidget.calcCornerXY(event.getX(), event.getY());
						localBookReader.onDraw(mCurCanvas);
						if (mPageWidget.DragToRight()) {
							page = localBookReader.readNextPage();
							if (localBookReader.isLastPage()) {
								Toast.makeText(getApplicationContext(),
										"这已经是最后一页了", Toast.LENGTH_SHORT)
										.show();
								return false;
							}
							localBookReader.onDraw(mNextCanvas);
						}else {
							page = localBookReader.readPrevPage();
							if (localBookReader.isFirstPage()) {
								Toast.makeText(getApplicationContext(),
										"这已经是第一页了", Toast.LENGTH_SHORT)
										.show();
								return false;
							}
							localBookReader.onDraw(mNextCanvas);
						}
						mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
					}
					ret = mPageWidget.doTouchEvent(event);
					return ret;
				}
				return false;
			}
		});
		
	}
	
	
}
