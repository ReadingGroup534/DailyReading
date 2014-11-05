package com.aiteu.dailyreading;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.aiteu.dailyreading.book.PageSplitor;
import com.aiteu.dailyreading.dealer.LoadDailyDataTask;
import com.aiteu.dailyreading.handler.HomeHandler;
import com.aiteu.dailyreading.update.AppUpdate;
import com.aiteu.dailyreading.view.drawer.MenuDrawer;
import com.aiteu.dailyreading.view.drawer.SlidingDrawer;
import com.aiteu.dailyreading.view.list.XListView;
import com.aiteu.http.factory.JsonHttpFactory;
import com.aiteu.http.handler.JsonHttpHandler;
import com.aiteu.http.util.NetWorkHelper;

import android.os.Bundle;
import android.os.HandlerThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
	private Boolean isNetworkOpen = false;
	private NetWorkHelper netWorkHelper;
	
	private Boolean isWifi, isMoblile;
	private boolean isDoubleClick = false; //点击两次返回键推出程序

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//set ActionBar's back action
//		actionBar=getActionBar();
//        actionBar.show();
				
		isNetworkOpen = netWorkHelper.isNetAvailable(getApplicationContext());
		if (isNetworkOpen == false) {
			Toast.makeText(getApplication(), "网络没有打开，请先打开您的网络！", Toast.LENGTH_LONG).show();
		}else {
			isWifi = netWorkHelper.isWifi(getApplicationContext());
			if (isWifi) {
				Toast.makeText(getApplication(), "当前使用的是WIFI网络，尽情阅读吧！", Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(getApplication(), "当前使用的是手机移动网络，请注意流量使用情况！", Toast.LENGTH_SHORT).show();
			}
		}
		
		
//		findViewById();
//		mLeftListView.setAdapter(new ArrayAdapter<String>(this,
//				android.R.layout.simple_expandable_list_item_1, TITLES));

		// 监听菜单 左
//		mLeftListView.setOnItemClickListener(new DrawerItemClickListenerLeft());
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment fragment = new EssayFragment();
		ft.replace(R.id.fragment_layout, fragment);
		ft.commit();
		//FIXME 仅供测试使用
//		new Thread(testApiRunnable).start();
		new Thread(testApiRunnable).start();
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
	
	final Runnable testApiRunnable = new Runnable() {
		
		@Override
		public void run() {
			Map<String, Object> map;
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			JsonHttpFactory jsonFactory = new JsonHttpFactory();
			JsonHttpHandler jsonHandler = (JsonHttpHandler) jsonFactory.create();
			//FIXME :替换成自己本机的ip,json就是返回的数据，根据对应的数据格式
			JSONObject json = jsonHandler.getJson("http://localhost:8080/reading-web/api/list.json?limit=3&offset=0", null);
			System.out.println(json.toString());
			Log.i("json",json.toString() + " ");
			JSONArray array;
			try {
				array = json.getJSONArray("list");
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject2 = (JSONObject) array.opt(i);
					map = new HashMap<String, Object>();
					map.put("text", jsonObject2.get("abstracts"));
					map.put("title", jsonObject2.get("title"));
					Log.i("json", jsonObject2.get("abstracts").toString());
					Log.i("json", jsonObject2.get("title").toString());
					list.add(map);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	/*private void findViewById() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mLeftLayout = (RelativeLayout) findViewById(R.id.menu_layout_left);
		mLeftListView = (ListView) findViewById(R.id.menu_listView_l);
	}*/

	/**
	 * the clicklistener in left side
	 * 
	 * @author liyangchao
	 * 
	 */
	/*public class DrawerItemClickListenerLeft implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			Fragment fragment = null;
			
			//according to the click row number decide to start which fragment;
			switch (position) {
			case 0:
				fragment = new EmotionalFragment();
				break;
			case 1:
				fragment = new EssayFragment();
				break;
			case 2:
			case 3:
			case 4:
				fragment = new SettingActivity();
				break;
			default:
				break;
			}
			ft.replace(R.id.fragment_layout, fragment);
			ft.commit();
			mDrawerLayout.closeDrawer(mLeftLayout);
		}

	}*/
	
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
