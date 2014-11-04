package com.aiteu.dailyreading;

import java.util.Timer;
import java.util.TimerTask;

import com.aiteu.dailyreading.book.PageSplitor;
import com.aiteu.dailyreading.dealer.LoadDailyDataTask;
import com.aiteu.dailyreading.handler.HomeHandler;
import com.aiteu.dailyreading.update.AppUpdate;
import com.aiteu.dailyreading.view.drawer.MenuDrawer;
import com.aiteu.dailyreading.view.drawer.SlidingDrawer;
import com.aiteu.dailyreading.view.list.XListView;

import android.os.Bundle;
import android.os.HandlerThread;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private HomeHandler mHomeHandler = null;
	private HandlerThread mHandlerThread = null;
	private AppUpdate mAppUpdate = null;
	private SlidingDrawer mMenuDrawer = null;
	private View mMenuView = null;
	private View mContentView = null;
	private XListView mListView = null;
	private DailyAdapter mAdapter = null;
	private LoadDailyDataTask mDailyDataTask = null;
	private PageSplitor mPageSplitor = null;
	
	private boolean isDoubleClick = false; //点击两次返回键推出程序

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mHandlerThread = new HandlerThread("homeHandler");
		mHomeHandler = new HomeHandler(this, mHandlerThread.getLooper());
		initViews();
		mAppUpdate = new AppUpdate(this);
		mAppUpdate.check();
		mPageSplitor = new PageSplitor();
		mDailyDataTask = new LoadDailyDataTask(this);
		mDailyDataTask.execute(mPageSplitor);
	}
	
	private void initViews(){
		LayoutInflater mInflater = getLayoutInflater();
		mMenuView = mInflater.inflate(R.layout.main_menu, null);
		mContentView = mInflater.inflate(R.layout.main_content, null);
		mMenuDrawer = (SlidingDrawer)findViewById(R.id.drawer_menu);
		mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_BEZEL);
		mMenuDrawer.setContentView(mContentView);
		mMenuDrawer.setMenuView(mMenuView);
		mMenuDrawer.setDropShadow(R.drawable.shadow);
		mMenuDrawer.setDropShadowSize((int) getResources().getDimension(
				R.dimen.shadow_width));
		mMenuDrawer.setMaxAnimationDuration(3000);
		mMenuDrawer.setHardwareLayerEnabled(false);
		mMenuDrawer.setMenuSize((int) getResources().getDimension(
				R.dimen.slidingmenu_offset));
		mMenuDrawer.setTouchBezelSize(50);
		mListView = (XListView)mContentView.findViewById(R.id.article_listview);
		mListView.setPullRefreshEnable(true);
		mListView.setPullLoadEnable(true);
		mAdapter = new DailyAdapter(this);
		mListView.setAdapter(mAdapter);
		
	}
	
	public void showDailyList(PageSplitor pageSplitor){
		this.mPageSplitor = pageSplitor;
		mAdapter.setData(mPageSplitor.getDailyList());
		mAdapter.notifyDataSetChanged();
	}
	
	
	Timer mQuitTimer =  new Timer();

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction() == KeyEvent.ACTION_DOWN){
			if(keyCode == KeyEvent.KEYCODE_BACK){
				if(!isDoubleClick){
					isDoubleClick = true;
					Toast.makeText(this, getText(R.string.msg_quit), Toast.LENGTH_LONG).show();
					mQuitTimer.schedule(new TimerTask() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							isDoubleClick = false;
						}
					}, 1500);
				}else{
					exit();
				}
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
