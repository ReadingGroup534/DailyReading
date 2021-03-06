package com.aiteu.dailyreading;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.aiteu.dailyreading.book.BookBean;
import com.aiteu.dailyreading.book.ItemDaily;
import com.aiteu.dailyreading.db.MyStoreHelper;
import com.aiteu.dailyreading.reader.BookPage;
import com.aiteu.dailyreading.reader.LocalBookReader;
import com.aiteu.http.factory.XmlHttpFactory;
import com.aiteu.http.handler.XmlHttpHandler;
import com.aiteu.http.xml.XmlDocument;
import com.aiteu.log.LogTools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Contacts.Intents.Insert;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

@SuppressLint("WrongCall")
public class ReadPager extends Activity implements OnClickListener,
		OnSeekBarChangeListener {

	private static final String TAG = "ReadTest";
	private Context mContext = null;
	private MyStoreHelper myStoreHelper;
	private Bitmap mCurPageBitmap, mNextPageBitmap; // 當前頁面和下一個頁面的畫布
	private Canvas mCurCanvas, mNextCanvas;
	private PageWidget mPageWidget;
//	private Pager mPageWidget ;
	private PopupWindow mPopupWindow, mToolPopupWindow1, mToolPopupWindow2,
			mToolPopupWindow3, mToolPopupWindow4;
//	mToolPopupWindow5;
	private View popupwindwow, toolpop1, toolpop2, toolpop3, toolpop4;
//			toolpop5;
	private ImageButton imageBtn2;
//	imageBtn4_1,imageBtn4_2;
	private Button myStoreButton,addStoreButton;
	private TextView percenTextView;
	private static int begin = 0; // 记录书籍的开始位置
	private static String word = "";// 记录当前页面的文字
	private int a = 0, b = 0;

	int screenHeight;
	int screenWidth;
//	int readHeight; // 电子书显示高度
	int defaultSize = 0;
	private Boolean show = false;// popwindow是否显示
	private SharedPreferences sp;
	private int size = 25; // 字体大小
	private int light; // 亮度值
	private Boolean isNight = false; // 亮度模式,白天和晚上
	private PagerFactory pageFactory;
	private PageFactory pageFactory2;
	private XmlDocument xmlDoc;
	private TextView bookBtn1, bookBtn2, bookBtn3, bookBtn4;
	private SeekBar seekBar1, seekBar2, seekBar3;
	private SharedPreferences.Editor editor;
	private WindowManager.LayoutParams lp;
	private XmlDocument doc;
	private String URL = "https://github.com/ReadingGroup534/DailyReading/blob/master/test/reading/reading123456789000001.txt";
	// 实例化Handler
	public Handler myHandler = new Handler() {
		//接收子线程的消息，同时更新UI
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				begin = msg.arg1;
				pageFactory.setM_mbBufBegin(begin);
				pageFactory.setM_mbBufEnd(begin);
				postInvalidateUI();
				break;
			case 1:
				pageFactory.setM_mbBufBegin(begin);
				pageFactory.setM_mbBufEnd(begin);
				postInvalidateUI();
				break;
			default:
				break;
			}
		}
	};

	@SuppressLint("WrongCall")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		mContext = getBaseContext();
		xmlDoc = new XmlDocument();
		
		DisplayMetrics dm = getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		Log.i("lyc", "screenwidth:"+ screenWidth + "screenheight:"+ screenHeight);

		defaultSize = (screenWidth * 20) / 320;
//		readHeight = screenHeight;
		mCurPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight,
				Config.ARGB_8888);
		mNextPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight,
				Config.ARGB_8888);
		mCurCanvas = new Canvas(mCurPageBitmap);
		mNextCanvas = new Canvas(mNextPageBitmap);
		
//		pageFactory = new PageFactory(screenWidth, readHeight);
		// 提取记录在sharedpreferences的各种状态
		sp = getSharedPreferences("config", MODE_PRIVATE);
		editor = sp.edit();
		getSize();// 获取配置文件中的size大小
		getLight();// 获取配置文件中的light值
		
		 XmlHttpFactory xmlHttpFactory = new XmlHttpFactory();
		 XmlHttpHandler xmlHttpHandler = (XmlHttpHandler) xmlHttpFactory.create();
		 try {
			doc = xmlHttpHandler.getXml(mContext.getAssets().open("detail.xml"));
			System.out.println("Read XML:"+ doc.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
//		pageFactory2 = new PageFactory(screenHeight, screenWidth, doc);
		 
		
		pageFactory = new PagerFactory(screenWidth, screenHeight);
		if (isNight) {
			pageFactory.setBgBitmap(BitmapFactory.decodeResource(
					getResources(), R.drawable.night_bg));
			pageFactory.setM_textColor(Color.rgb(128, 128, 128));
		} else {
			pageFactory.setBgBitmap(BitmapFactory.decodeResource(
					getResources(), R.drawable.day_bg));
			pageFactory.setM_textColor(Color.rgb(28, 28, 28));
		}
		
//		pageFactory.onDraw(mCurCanvas);
		
		mPageWidget = new PageWidget(this, screenWidth, screenHeight);// 页面
//		setContentView(R.layout.read_view);
//		RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.readlayout);
//		rLayout.addView(mPageWidget);

		setContentView(mPageWidget);

		
		mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
		//关闭GPU 渲染  防止在4.0以上真机翻页错乱
		if (Build.VERSION.SDK_INT >= 14) {
			mPageWidget.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		
		mPageWidget.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				boolean ret = false;
				if (v == mPageWidget) {
					if (!show) {
						if (event.getAction() == MotionEvent.ACTION_DOWN) {
							if (event.getY() > screenHeight) {
								// 超出范围了，则不做翻页
								return false;
							}
							mPageWidget.abortAnimation();
							mPageWidget.calcCornerXY(event.getX(), event.getY());
							pageFactory.onDraw(mCurCanvas);
							if (mPageWidget.DragToRight()) {// 左返
								try {
									pageFactory.prePage();
									begin = pageFactory.getM_mbBufBegin();// 获取当前阅读位置
									word = pageFactory.getFirstLineText();// 获取当前阅读位置的首行文字
								} catch (IOException e) {
									// TODO Auto-generated catch block
									Log.i(TAG, "onTouch->prePage error", e);
								}
								if (pageFactory.isM_firstPage()) {
									Toast.makeText(getApplicationContext(),
											"这已经是第一页了", Toast.LENGTH_SHORT)
											.show();
									return false;
								}
								pageFactory.onDraw(mNextCanvas);
							} else {// 右翻
								try {
									pageFactory.nextPage();
									begin = pageFactory.getM_mbBufBegin();
									word = pageFactory.getFirstLineText();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									Log.i(TAG, "onTouch->nextPager error", e);
								}
								if (pageFactory.isM_lastPage()) {
									Toast.makeText(getApplicationContext(),
											"这已经是最后一页了", Toast.LENGTH_SHORT)
											.show();
									return false;
								}
								pageFactory.onDraw(mNextCanvas);
							}
							mPageWidget.setBitmaps(mCurPageBitmap,
									mNextPageBitmap);
						}

						editor.putInt(xmlDoc.getContent().toString() + "begin", begin)
								.commit();
						ret = mPageWidget.doTouchEvent(event);
						return ret;
					}
				}
				return false;
			}
		});

		setPop();
		//设置亮度
		lp = getWindow().getAttributes();
		lp.screenBrightness = light / 10.0f < 0.01f ? 0.01f : light / 10.0f;
		getWindow().setAttributes(lp);
		
		
		
		/**
		 * 根据传递的路径打开书
		 */
		try {
			Intent intent = getIntent();
//			URL = intent.getStringExtra("URL");
			URL = "https://github.com/ReadingGroup534/DailyReading/blob/master/test/reading/reading123456789000001.txt";
			LogTools.getInstance().info(URL);
//			pageFactory.openbook("/sdcard/test.txt");
//			pageFactory.openbook("/data/data/Notes_KT Day 1.txt");
//			pageFactory.openbook("sdcard/dlna_log.txt");  //测试手机用
			Log.i("System", URL.toString());
			pageFactory.openbook(URL);
			LogTools.getInstance().info(URL.toString());
			pageFactory.onDraw(mCurCanvas);
		} catch (IOException e1) {
			e1.printStackTrace();
			Toast.makeText(this, "文章已不存在！请选择其他美文阅读...", Toast.LENGTH_SHORT).show();
		}
		
		myStoreHelper = new MyStoreHelper(this);
		begin = sp.getInt(xmlDoc.getContent().toString() + "begin", 0);
	}

	/**
	 * 菜单里seekbar的改变的具体实现
	 */
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		switch (seekBar.getId()) {
		case R.id.seekBar1: // // 字体进度条
			size = seekBar1.getProgress() + 15;
			setSize();
			pageFactory.setM_fontSize(size);
			pageFactory.setM_mbBufBegin(begin);
			pageFactory.setM_mbBufEnd(begin);
			postInvalidateUI();
			break;
		case R.id.seekBar2: // 亮度
			light = seekBar2.getProgress();
			setLight();
			lp.screenBrightness = light / 10.0f < 0.01f ? 0.01f : light / 10.0f;
			getWindow().setAttributes(lp);
			break;
		/*case R.id.seekBar4:
			int percent = seekBar3.getProgress();
			percenTextView.setText(percent + "%");
			begin = (pageFactory.getM_mbBufLen()*percent) /100;
			editor.putInt(xmlDoc.getContent().toString()+"begin", begin).commit();
			pageFactory.setM_mbBufBegin(begin);
			pageFactory.setM_mbBufEnd(begin);
			try {
			if (percent == 100) {
					pageFactory.prePage();
					pageFactory.getM_mbBufBegin();
					begin = pageFactory.getM_mbBufEnd();
					pageFactory.setM_mbBufBegin(begin);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "onProgressChanged seekBar4-> IOException error", e);
			}
			postInvalidateUI();
			break;*/
		default:
			break;
		}

	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * 记录配置文件中字体大小
	 */
	private void setSize() {
		try {
			size = seekBar1.getProgress() + 15;
			editor.putInt("size", size);
			editor.commit();
		} catch (Exception e) {
			Log.e(TAG, "setSize-> Exception error", e);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		pageFactory = null;
		mPageWidget = null;
		finish();
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
			setToolPop(a);
			break;
		case R.id.bookBtn3:
			a = 3;
			setToolPop(a);
			break;
		case R.id.bookBtn4:
			popToolsDismiss();
			showShare();
			break;
		// 夜间模式按钮
		case R.id.imageBtn2:
			if (isNight) {
				pageFactory.setM_textColor(Color.rgb(28, 28, 28));
				imageBtn2.setImageResource(R.drawable.off);
				isNight = false;
				pageFactory.setBgBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.day_bg));
			} else {
				pageFactory.setM_textColor(Color.rgb(128, 128, 128));
				imageBtn2.setImageResource(R.drawable.on);
				isNight = true;
				pageFactory.setBgBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.night_bg));
			}
			setLight();
			pageFactory.setM_mbBufBegin(begin);
			pageFactory.setM_mbBufEnd(begin);
			postInvalidateUI();
			break;
		// 添加收藏按钮
		case R.id.storeBtn3_1:
			SQLiteDatabase db = myStoreHelper.getWritableDatabase();
			ItemDaily itemDaily = new ItemDaily();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm ss");
			String time = sdf.format(new Date());
			// db.execSQL("insert into article(article_id,active,show_time,title,author,"
			// +
			// "article_type,abstracts,recommend_star,praise_times,share_times,"
			// +
			// "scan_times,physical_path) values (?,?,?,?,?,?,?,?,?,?,?,?)",new
			// String[]{});
			ContentValues cv = new ContentValues();
			cv.put("article_id", itemDaily.getArticle_id());
			cv.put("active", itemDaily.isActive());
			cv.put("show_time", time);
			cv.put("title", itemDaily.getTitle());
			cv.put("author", itemDaily.getAuthor());
			cv.put("article_type", itemDaily.getArticleType());
			cv.put("abstracts", itemDaily.getAbstracts());
			cv.put("recommend_star", itemDaily.getRecommendStar());
			cv.put("praise_times", time);
			cv.put("share_times", time);
			cv.put("scan_times", time);
			cv.put("physical_path", " ");

			db.insert("article", null, cv);
			db.close();
			mToolPopupWindow1.dismiss();
			mToolPopupWindow4.dismiss();
			break;
		// 我的收藏
		case R.id.storeBtn3_2:
			SQLiteDatabase dbAdd = myStoreHelper.getReadableDatabase();
			
			break;
//		case R.id.imageBtn4_1:
//			clear();
//			pageFactory.setM_mbBufBegin(begin);
//			pageFactory.setM_mbBufEnd(begin);
//			postInvalidateUI();
//			break;
//		case R.id.imageBtn4_2:
//			showShare();
//			break;
		default:
			break;
		}
	}

	/**
	 * 刷新界面
	 */
	private void postInvalidateUI() {
		// TODO Auto-generated method stub
		mPageWidget.abortAnimation();
		pageFactory.onDraw(mCurCanvas);
		try {
			pageFactory.currentPage();
			begin = pageFactory.getM_mbBufBegin(); // 获取当前阅读位置
			word = pageFactory.getFirstLineText();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "postInvalidateUI->IOException error" + e);
		}
		pageFactory.onDraw(mNextCanvas);
		mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
		mPageWidget.postInvalidate();
	}

	/**
	 * 记录配置文件中亮度值和横竖屏
	 */
	private void setLight() {
		try {
			light = seekBar2.getProgress();
			// 当进度小于80时，设置成80，防止太黑看不见的后果。  
			if (light < 80) {
				light = 80;
			}
            // 根据当前进度改变亮度  
            Settings.System.putInt(getContentResolver(),  
                    Settings.System.SCREEN_BRIGHTNESS, light);  
            light = Settings.System.getInt(getContentResolver(),  
                    Settings.System.SCREEN_BRIGHTNESS, -1);  
           //设置亮度
    		lp = getWindow().getAttributes();
    		lp.screenBrightness = light / 10.0f < 0.01f ? 0.01f : light / 10.0f;
    		getWindow().setAttributes(lp);
			editor.putInt("light", light);
			if (isNight) {
				editor.putBoolean("night", true);
			} else {
				editor.putBoolean("night", false);
			}
			editor.commit();
		} catch (Exception e) {
			Log.e(TAG, "setLight-> Exception error", e);
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
//		mToolPopupWindow5.dismiss();
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
//		toolpop5 = this.getLayoutInflater().inflate(R.layout.tool44, null);
//		mToolPopupWindow5 = new PopupWindow(toolpop5, LayoutParams.FILL_PARENT,
//				LayoutParams.WRAP_CONTENT);
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
					seekBar1 = (SeekBar) toolpop2.findViewById(R.id.seekBar1);
					size = sp.getInt("size", 20);
					seekBar1.setProgress((size - 15));
					seekBar1.setOnSeekBarChangeListener(this);
				}

				// 当点击亮度按钮
				if (a == 2) {
					mToolPopupWindow3.showAtLocation(mPageWidget,
							Gravity.BOTTOM, 0, screenWidth * 45 / 300);
					seekBar2 = (SeekBar) toolpop3.findViewById(R.id.seekBar2);
					imageBtn2 = (ImageButton) toolpop3.findViewById(R.id.imageBtn2);
					getLight();
					imageBtn2.setOnClickListener(this);
					seekBar2.setOnSeekBarChangeListener(this);
					seekBar2.setProgress(light);
					if (isNight) {
						imageBtn2.setImageResource(R.drawable.on);
					} else {
						imageBtn2.setImageResource(R.drawable.off);
					}
				}

				// 当点击收藏按钮
				if (a == 3) {
					mToolPopupWindow4.showAtLocation(mPageWidget,
							Gravity.BOTTOM, 0, screenWidth * 45 / 300);
					myStoreButton = (Button) toolpop4
							.findViewById(R.id.storeBtn3_1);
					addStoreButton = (Button) toolpop4
							.findViewById(R.id.storeBtn3_2);
					myStoreButton.setOnClickListener(this);
					addStoreButton.setOnClickListener(this);
				}

				/*// 当点击跳转跳转按钮
				if (a == 4) {
					mToolPopupWindow5.showAtLocation(mPageWidget,
							Gravity.BOTTOM, 0, screenWidth * 45 / 300);
					imageBtn4_1 = (ImageButton) toolpop5
							.findViewById(R.id.imageBtn4_1);
					imageBtn4_2 = (ImageButton) toolpop5
							.findViewById(R.id.imageBtn4_2);
					seekBar3 = (SeekBar) toolpop5.findViewById(R.id.seekBar4);
					percenTextView = (TextView) toolpop5
							.findViewById(R.id.markEdit4);
					float percent = (float) ((begin * 1.0) / pageFactory
							.getM_mbBufLen());
					DecimalFormat decimalFormat = new DecimalFormat("#0");
					String sPercent = decimalFormat.format(percent * 100) + "%";
					percenTextView.setText(sPercent);

					seekBar3.setProgress(Integer.parseInt(decimalFormat
							.format(percent * 100)));
					seekBar3.setOnSeekBarChangeListener(this);
					imageBtn4_1.setOnClickListener(this);
					imageBtn4_2.setOnClickListener(this);
				}*/
			}
		}else {
			if (mToolPopupWindow1.isShowing()) {
				//对数据的纪录
				popToolsDismiss();
			}
			mToolPopupWindow1.showAtLocation(mPageWidget, Gravity.BOTTOM,
					0, screenWidth * 45 / 320);
			// 当点击字体按钮
			if (a == 1) {
				mToolPopupWindow2.showAtLocation(mPageWidget,
						Gravity.BOTTOM, 0, screenWidth * 45 / 320);
				seekBar1 = (SeekBar) toolpop2.findViewById(R.id.seekBar1);
				size = sp.getInt("size", 20);
				seekBar1.setProgress((size - 15));
				seekBar1.setOnSeekBarChangeListener(this);
			}

			// 当点击亮度按钮
			if (a == 2) {
				mToolPopupWindow3.showAtLocation(mPageWidget,
						Gravity.BOTTOM, 0, screenWidth * 45 / 300);
				seekBar2 = (SeekBar) toolpop3.findViewById(R.id.seekBar2);
				imageBtn2 = (ImageButton) toolpop3.findViewById(R.id.imageBtn2);
				getLight();
				seekBar2.setProgress(light);
				
				if(isNight){
					pageFactory.setBgBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.day_bg));
				}else {
					pageFactory.setBgBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.night_bg));
				}
				
				if (isNight) {
					imageBtn2.setImageResource(R.drawable.on);
				} else {
					imageBtn2.setImageResource(R.drawable.off);
				}
				
				imageBtn2.setOnClickListener(this);
				seekBar2.setOnSeekBarChangeListener(this);
				
			}

			// 当点击收藏按钮
			if (a == 3) {
				mToolPopupWindow4.showAtLocation(mPageWidget,
						Gravity.BOTTOM, 0, screenWidth * 45 / 300);
				myStoreButton = (Button) toolpop4
						.findViewById(R.id.storeBtn3_1);
				addStoreButton = (Button) toolpop4
						.findViewById(R.id.storeBtn3_2);
				myStoreButton.setOnClickListener(this);
				addStoreButton.setOnClickListener(this);
			}

		/*	// 当点击跳转跳转按钮
			if (a == 4) {
				mToolPopupWindow5.showAtLocation(mPageWidget,
						Gravity.BOTTOM, 0, screenWidth * 45 / 300);
				imageBtn4_1 = (ImageButton) toolpop5
						.findViewById(R.id.imageBtn4_1);
				imageBtn4_2 = (ImageButton) toolpop5
						.findViewById(R.id.imageBtn4_2);
				seekBar3 = (SeekBar) toolpop5.findViewById(R.id.seekBar4);
				percenTextView = (TextView) toolpop5
						.findViewById(R.id.markEdit4);
				float percent = (float) ((begin * 1.0) / pageFactory
						.getM_mbBufLen());
				DecimalFormat decimalFormat = new DecimalFormat("#0");
				String sPercent = decimalFormat.format(percent * 100) + "%";
				percenTextView.setText(sPercent);

				seekBar3.setProgress(Integer.parseInt(decimalFormat
						.format(percent * 100)));
				seekBar3.setOnSeekBarChangeListener(this);
				imageBtn4_1.setOnClickListener(this);
				imageBtn4_2.setOnClickListener(this);
			}*/
		}
		// 记录上次点击的是哪一个
		b = a;
	}
	
	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字
		oks.setNotification(R.drawable.ic_launcher,
				getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(URL);
		// text是分享文本，所有平台都需要这个字段
		oks.setText("请输入分享文本");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath("/sdcard/test.jpg");
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(URL);
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(URL);

		// 启动分享GUI
		oks.show(this);
	}

	/**
	 * 添加对menu按钮的监听
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (show) {
				getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
				show = false;
				mPopupWindow.dismiss();
				popToolsDismiss();
			} else {
				getWindow()
						.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
				getWindow().addFlags(
						WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
				show = true;
				pop();
			}
		}
		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//			Log.i("lyc", "click the keyback button");
//			mPopupWindow.dismiss();
//			popToolsDismiss();
			clear();
			Log.i("lyc", "click the keyback button");
		}
		return super.onKeyUp(keyCode, event);
	}
	
	

}
