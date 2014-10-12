package com.aiteu.dailyreading;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	//"情感治愈" ,"经典散文" ,"联系我们", 
	public static final String[] TITLES = {"情感治愈" ,"经典散文","每日一文", "心灵鸡汤", "设置"};
	private DrawerLayout mDrawerLayout;
	private RelativeLayout mLeftLayout;
	private ListView mLeftListView;
	private Boolean isNetworkOpen = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		isNetworkOpen = isNetworkAvailable(getApplicationContext());
		if (isNetworkOpen == false) {
			Toast.makeText(getApplication(), "网络没有打开，请先打开您的网络！", Toast.LENGTH_LONG).show();
		}
		findViewById();
		mLeftListView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, TITLES));

		// 监听菜单 左
		mLeftListView.setOnItemClickListener(new DrawerItemClickListenerLeft());
	}

	private void findViewById() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mLeftLayout = (RelativeLayout) findViewById(R.id.menu_layout_left);
		mLeftListView = (ListView) findViewById(R.id.menu_listView_l);
	}

	/**
	 * the clicklistener in left side
	 * 
	 * @author liyangchao
	 * 
	 */
	public class DrawerItemClickListenerLeft implements OnItemClickListener {

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

	}
	
	public static boolean isNetworkAvailable(Context context) { 
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
        NetworkInfo[] info = mgr.getAllNetworkInfo(); 
        if (info != null) { 
            for (int i = 0; i < info.length; i++) { 
                if (info[i].getState() == NetworkInfo.State.CONNECTED) { 
                    return true; 
                } 
            } 
        } 
        return false; 
    }
	

}
