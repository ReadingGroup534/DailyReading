package com.aiteu.dailyreading;

import java.util.Timer;
import java.util.TimerTask;

import com.aiteu.dailyreading.book.PageSplitor;
import com.aiteu.dailyreading.handler.MainHandler;
import com.aiteu.dailyreading.update.AppUpdate;
import com.aiteu.dailyreading.view.drawer.SlidingDrawer;
import com.aiteu.dailyreading.view.list.XListView;
import com.aiteu.dailyreading.view.list.XListView.IXListViewListener;

import android.os.Bundle;
import android.os.Handler;

import com.aiteu.http.util.NetWorkHelper;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements IXListViewListener{
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private AppUpdate mAppUpdate = null;
	private SlidingDrawer mMenuDrawer = null;
	private View mMenuView = null;
	private View mContentView = null;
	private View splashLay = null;
	private XListView mListView = null;
	private DailyAdapter mAdapter = null;
	//private LoadDailyDataTask mDailyDataTask = null;
	private PageSplitor mPageSplitor = null;
	private NetWorkHelper netWorkHelper;

	private Boolean isWifi;
	private boolean isDoubleClick = false; // 点击两次返回键推出程序

	private RelativeLayout sanwenLayout, qingganLayout, xiaohuaLayout,
			otherLayout, settingLayout;
	
	//定义数据处理逻辑
	private MainHandler mHandler = null;
	
	
	public Handler getHandler(){
		return mHandler;
	}
	
	public PageSplitor getPageSplitor(){
		return mPageSplitor;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews(); //初始化控件
		mPageSplitor = new PageSplitor();
		mHandler = new MainHandler(this);
		mHandler.initData(); //初始化数据

//		settingLayout.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(MainActivity.this,
//						SettingActivity.class);
//				startActivity(intent);
//			}
//		});
	}

	private void initViews() {
		splashLay = findViewById(R.id.welcome_lay_id);
		mListView = (XListView) findViewById(R.id.article_listview);
		mListView.setXListViewListener(this);
		mListView.setPullRefreshEnable(true);
		mListView.setPullLoadEnable(true);
		mAdapter = new DailyAdapter(this);
		mListView.setAdapter(mAdapter);

		// LayoutInflater mInflater = getLayoutInflater();
		// mMenuView = mInflater.inflate(R.layout.main_menu, null);
		// mContentView = mInflater.inflate(R.layout.main_content, null);
		// mMenuDrawer = (SlidingDrawer)findViewById(R.id.drawer_menu);
		// mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_BEZEL);
		// mMenuDrawer.setContentView(mContentView);
		// mMenuDrawer.setMenuView(mMenuView);
		// mMenuDrawer.setDropShadow(R.drawable.shadow);
		// mMenuDrawer.setDropShadowSize((int) getResources().getDimension(
		// R.dimen.shadow_width));
		// mMenuDrawer.setMaxAnimationDuration(3000);
		// mMenuDrawer.setHardwareLayerEnabled(false);
		// mMenuDrawer.setMenuSize((int) getResources().getDimension(
		// R.dimen.slidingmenu_offset));
		// mMenuDrawer.setTouchBezelSize(50);

//		sanwenLayout = (RelativeLayout) findViewById(R.id.sanwenLayout);
//		qingganLayout = (RelativeLayout) findViewById(R.id.qingganLayout);
//		xiaohuaLayout = (RelativeLayout) findViewById(R.id.xiaohualayout);
//		otherLayout = (RelativeLayout) findViewById(R.id.otherlayout);
//		settingLayout = (RelativeLayout) findViewById(R.id.settinglayout);
	}
	
	public void showNetworkUnavailable(){
		splashLay.setVisibility(View.GONE);
	}
	
	public void showEmpty(){
		mListView.stopRefresh();
		mListView.stopLoadMore();
		splashLay.setVisibility(View.GONE);
	}
	
	public void showError(){
		splashLay.setVisibility(View.GONE);
		mListView.stopRefresh();
		mListView.stopLoadMore();
	}

	public void showDailyList() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		splashLay.setVisibility(View.GONE);
		mAdapter.setData(mPageSplitor.getDailyList());
		mAdapter.notifyDataSetChanged();
	}
	
	//加载最新的数据
	@Override
	public void onRefresh() {
		Log.d(TAG, "onRefresh");
		mPageSplitor.setLoadType(PageSplitor.LOAD_TYPE_REFRESH);
		mPageSplitor.setStart(0);
		mHandler.initData();
	}
	
	//加载更早的数据
	@Override
	public void onLoadMore() {
		Log.d(TAG, "onLoadMore");
		mPageSplitor.setLoadType(PageSplitor.LOAD_DATA_MORE);
		mHandler.initData();
	}

	Timer mQuitTimer = new Timer();

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				if (!isDoubleClick) {
					isDoubleClick = true;
					Toast.makeText(this, getText(R.string.msg_quit),
							Toast.LENGTH_LONG).show();
					mQuitTimer.schedule(new TimerTask() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							isDoubleClick = false;
						}
					}, 1500);
				} else {
					exit();
				}
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
