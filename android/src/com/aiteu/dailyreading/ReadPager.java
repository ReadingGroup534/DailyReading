package com.aiteu.dailyreading;


import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ReadPager extends Activity implements OnClickListener, OnSeekBarChangeListener{

	private static final String TAG = "ReadTest";
	private Context mContext = null;
	private Bitmap mCurPageBitmap, mNextPageBitmap; //當前頁面和下一個頁面的畫布
	private Canvas mCurCanvas,mNextCanvas;
	private PageWidget mPageWidget;
	private static int begin = 0;	//记录书籍的开始位置
	private int a = 0,b = 0;
	
	int screenHeight;
	int screenWidth;
	int readHeight; // 电子书显示高度
	int defaultSize = 0; 
	
	private TextView bookBtn1, bookBtn2, bookBtn3, bookBtn4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		mContext = getBaseContext();
		
		WindowManager manager = getWindowManager();
		Display display = manager.getDefaultDisplay();
		screenHeight = display.getHeight();
		screenWidth = display.getWidth();
		
		defaultSize = (screenWidth*20)/320;
		readHeight = screenHeight;
		
		mCurPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Config.ARGB_8888);
		mNextPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight,Config.ARGB_8888);
		mCurCanvas = new Canvas(mCurPageBitmap);
		mNextCanvas = new Canvas(mNextPageBitmap);
		
		
		mPageWidget = new PageWidget(this, screenWidth, readHeight);// 页面
		setContentView(R.layout.read_view);
		RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.readlayout);
		rLayout.addView(mPageWidget);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
