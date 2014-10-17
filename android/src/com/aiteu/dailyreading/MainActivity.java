package com.aiteu.dailyreading;

import java.io.IOException;

import org.json.JSONObject;

import com.aiteu.http.factory.HttpFactory;
import com.aiteu.http.factory.JsonHttpFactory;
import com.aiteu.http.factory.XmlHttpFactory;
import com.aiteu.http.handler.JsonHttpHandler;
import com.aiteu.http.handler.XmlHttpHandler;
import com.aiteu.http.inteface.HttpHandler;
import com.aiteu.http.util.NetWorkHelper;
import com.aiteu.http.xml.XmlDocument;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	//"情感治愈" ,"经典散文" ,"联系我们", 
	public static final String[] TITLES = {"情感治愈" ,"经典散文","每日一文", "心灵鸡汤", "帮助"};
	private DrawerLayout mDrawerLayout;
	private RelativeLayout mLeftLayout;
	private ListView mLeftListView;
	private Boolean isNetworkOpen = false;
	private Boolean isWifi, isMoblile;
	private long exitTime = 0;
	private ActionBar actionBar;
	private NetWorkHelper netWorkHelper;

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
		new Thread(testSaxParser).start();
	}
	
//	final Runnable testApiRunnable = new Runnable() {
//		
//		@Override
//		public void run() {
//			JsonHttpFactory jsonFactory = new JsonHttpFactory();
//			JsonHttpHandler jsonHandler = (JsonHttpHandler) jsonFactory.create();
//			//FIXME :替换成自己本机的ip,json就是返回的数据，根据对应的数据格式
//			JSONObject json = jsonHandler.getJson("http://192.168.2.101:8080/reading-web/api/browse.json", null);
//			System.out.println(json.toString());
//		}
//	};
	
	final Runnable testSaxParser = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			XmlHttpFactory xmlHttpFactory = new XmlHttpFactory();
			XmlHttpHandler xmlHttpHandler = (XmlHttpHandler) xmlHttpFactory.create();
			try {
				XmlDocument xmlDocument = xmlHttpHandler.getPullXml(getAssets().open("detail.xml"));
				System.out.println(xmlDocument.toString());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	};
	
	final Runnable testXmlParse = new Runnable() {
		
		@Override
		public void run() {
			XmlHttpFactory xmlFactory = new XmlHttpFactory();
			XmlHttpHandler xmlHandler = (XmlHttpHandler)xmlFactory.create();
			try {
//				XmlDocument xmlDoc = xmlHandler.getXml(getAssets().open("detail.xml"));
//				Log.d("test", xmlDoc.toString());
				XmlDocument xmlDoc = xmlHandler.getPullXml(getAssets().open("books.xml"));
				System.out.println(xmlDoc.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	final Runnable testApiRunnable = new Runnable() {
		
		@Override
		public void run() {
			JsonHttpFactory jsonFactory = new JsonHttpFactory();
			JsonHttpHandler jsonHandler = (JsonHttpHandler) jsonFactory.create();
			//FIXME :替换成自己本机的ip,json就是返回的数据，根据对应的数据格式
			JSONObject json = jsonHandler.getJson("http://192.168.2.101:8080/reading-web/api/browse.json", null);
			System.out.println(json.toString());
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
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_LONG).show();
				exitTime = System.currentTimeMillis();
			}else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	

}
