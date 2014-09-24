package com.aiteu.dailyreading;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ReadPager extends Activity implements OnClickListener,
		OnSeekBarChangeListener {

	private static final String TAG = "ReadTest";
	private Context mContext = null;
	private Bitmap mCurPageBitmap, mNextPageBitmap; // 當前頁面和下一個頁面的畫布
	private Canvas mCurCanvas, mNextCanvas;
	private PageWidget mPageWidget;
	private PopupWindow mPopupWindow, mToolPopupWindow1, mToolPopupWindow2,
			mToolPopupWindow3, mToolPopupWindow4, mToolPopupWindow5;
	private View popupwindwow, toolpop1, toolpop2, toolpop3, toolpop4,
			toolpop5;
	private ImageButton imageBtn2, imageBtn3_1, imageBtn3_2, imageBtn4_1,
			imageBtn4_2;
	private static int begin = 0; // 记录书籍的开始位置
	private static String word = "";// 记录当前页面的文字
	private int a = 0, b = 0;

	int screenHeight;
	int screenWidth;
	int readHeight; // 电子书显示高度
	int defaultSize = 0;
	private Boolean show = false;// popwindow是否显示
	private SharedPreferences sp;
	private int size = 30; // 字体大小
	private int light; // 亮度值
	private Boolean isNight; // 亮度模式,白天和晚上

	private TextView bookBtn1, bookBtn2, bookBtn3, bookBtn4;
	private SeekBar seekBar1, seekBar2, seekBar3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		mContext = getBaseContext();

		WindowManager manager = getWindowManager();
		Display display = manager.getDefaultDisplay();
		screenHeight = display.getHeight();
		screenWidth = display.getWidth();

		defaultSize = (screenWidth * 20) / 320;
		readHeight = screenHeight;
		// setContentView(R.layout.read_view);
		mCurPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight,
				Config.ARGB_8888);
		mNextPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight,
				Config.ARGB_8888);
		mCurCanvas = new Canvas(mCurPageBitmap);
		mNextCanvas = new Canvas(mNextPageBitmap);

		mPageWidget = new PageWidget(this, screenWidth, readHeight);// 页面
		setContentView(R.layout.read_view);
		RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.readlayout);
		rLayout.addView(mPageWidget);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bookBtn1:
			a = 1;
			setToolPop(a);
			break;
		case R.id.bookBtn2:
			a = 2;
			setToolPop(2);
			break;
		case R.id.bookBtn3:
			a = 3;
			setToolPop(3);
			break;
		case R.id.bookBtn4:
			a = 4;
			break;
		// 夜间模式按钮
		case R.id.imageBtn2:
			if (isNight) {
				
			}
		default:
			break;
		}
	}

	/**
	 * 记录数据，并清空popupwindow
	 */
	public void clear() {
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		show = false;
		mPopupWindow.dismiss();
		popToolsDismiss();
	}

	/**
	 * 读取配置文件中亮度值
	 */
	private void getLight() {
		light = sp.getInt("light", 5);
		isNight = sp.getBoolean("night", false);
	}

	/**
	 * 读取配置文件中字体大小
	 */
	private void getSize() {
		size = sp.getInt("size", defaultSize);
	}

	/**
	 * 关闭所有的弹出菜单窗口
	 */
	public void popToolsDismiss() {
		// TODO Auto-generated method stub
		mToolPopupWindow1.dismiss();
		mToolPopupWindow2.dismiss();
		mToolPopupWindow3.dismiss();
		mToolPopupWindow4.dismiss();
		mToolPopupWindow5.dismiss();
	}

	/**
	 * popupwindow的弹出,工具栏
	 */
	public void pop() {

		mPopupWindow.showAtLocation(mPageWidget, Gravity.BOTTOM, 0, 0);
		bookBtn1 = (TextView) popupwindwow.findViewById(R.id.bookBtn1);
		bookBtn2 = (TextView) popupwindwow.findViewById(R.id.bookBtn2);
		bookBtn3 = (TextView) popupwindwow.findViewById(R.id.bookBtn3);
		bookBtn4 = (TextView) popupwindwow.findViewById(R.id.bookBtn4);
		bookBtn1.setOnClickListener(this);
		bookBtn2.setOnClickListener(this);
		bookBtn3.setOnClickListener(this);
		bookBtn4.setOnClickListener(this);
	}

	/**
	 * 初始化所有POPUPWINDOW
	 */
	private void setPop() {
		popupwindwow = this.getLayoutInflater().inflate(R.layout.bookpop, null);
		toolpop1 = this.getLayoutInflater().inflate(R.layout.toolpop, null);
		mPopupWindow = new PopupWindow(popupwindwow, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		mToolPopupWindow1 = new PopupWindow(toolpop1, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		toolpop2 = this.getLayoutInflater().inflate(R.layout.tool11, null);
		mToolPopupWindow2 = new PopupWindow(toolpop2, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		toolpop3 = this.getLayoutInflater().inflate(R.layout.tool22, null);
		mToolPopupWindow3 = new PopupWindow(toolpop3, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		toolpop4 = this.getLayoutInflater().inflate(R.layout.tool33, null);
		mToolPopupWindow4 = new PopupWindow(toolpop4, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		toolpop5 = this.getLayoutInflater().inflate(R.layout.tool44, null);
		mToolPopupWindow5 = new PopupWindow(toolpop5, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
	}

	/**
	 * 设置popupwindow的显示与隐藏
	 * 
	 * @param a
	 */
	public void setToolPop(int a) {
		if (a == b && a != 0) {
			if (mToolPopupWindow1.isShowing()) {
				popToolsDismiss();
			} else {
				mToolPopupWindow1.showAtLocation(mPageWidget, Gravity.BOTTOM,
						0, screenWidth * 45 / 320);

				// 当点击字体按钮
				if (a == 1) {
					mToolPopupWindow2.showAtLocation(mPageWidget,
							Gravity.BOTTOM, 0, screenWidth * 45 / 320);
					seekBar1 = (SeekBar) toolpop1.findViewById(R.id.seekBar1);
					size = sp.getInt("size", 20);
					seekBar1.setProgress((size - 15));
					seekBar1.setOnSeekBarChangeListener(this);
				}

				// 当点击亮度按钮
				if (a == 2) {
					mToolPopupWindow3.showAtLocation(mPageWidget,
							Gravity.BOTTOM, 0, screenWidth * 45 / 300);
					seekBar2 = (SeekBar) toolpop2.findViewById(R.id.seekBar2);
					imageBtn2 = (ImageButton) toolpop2
							.findViewById(R.id.imageBtn2);
					getLight();
					imageBtn2.setOnClickListener(this);
					seekBar2.setOnSeekBarChangeListener(this);
					seekBar2.setProgress(light);
					if (isNight) {
						imageBtn2.setImageResource(R.drawable.on);
					} else {
						imageBtn2.setImageResource(R.drawable.off);
					}

					// 当点击收藏按钮
					if (a == 3) {
						mToolPopupWindow4.showAtLocation(mPageWidget,
								Gravity.BOTTOM, 0, screenWidth * 45 / 300);
						imageBtn3_1 = (ImageButton) toolpop3
								.findViewById(R.id.imageBtn3_1);
						imageBtn3_2 = (ImageButton) toolpop3
								.findViewById(R.id.imageBtn3_2);
						imageBtn3_1.setOnClickListener(this);
						imageBtn3_2.setOnClickListener(this);
					}

					// 当点击跳转跳转按钮
					if (a == 4) {
						mToolPopupWindow5.showAtLocation(mPageWidget,
								Gravity.BOTTOM, 0, screenWidth * 45 / 300);
						imageBtn4_1 = (ImageButton) toolpop4
								.findViewById(R.id.imageBtn4_1);
						imageBtn4_2 = (ImageButton) toolpop4
								.findViewById(R.id.imageBtn4_2);
						seekBar3 = (SeekBar) toolpop4
								.findViewById(R.id.seekBar4);

					}
				}
			}
		}
	}

	/**
	 * 添加对menu按钮的监听
	 */
	/*
	 * @Override public boolean onKeyUp(int keyCode, KeyEvent event) { // TODO
	 * Auto-generated method stub if (keyCode == KeyEvent.KEYCODE_MENU) { if
	 * (show) {
	 * getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
	 * ); show = false; mPopupWindow.dismiss(); popToolsDismiss(); }else {
	 * getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	 * getWindow
	 * ().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN); show =
	 * true; pop(); } } return super.onKeyUp(keyCode, event); }
	 */

}
