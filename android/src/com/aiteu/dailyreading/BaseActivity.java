package com.aiteu.dailyreading;

import java.util.LinkedList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {
	/**
	 * 管理程序所有启动的activity
	 */
	public static LinkedList<Activity> sAllActivitys = new LinkedList<Activity>();

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		sAllActivitys.add(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}
	
	/**
	 * 程序退出时，清空活动栈
	 */
	public static void exit(){
		for(Activity activity : sAllActivitys) {  
            activity.finish();  
        }
		sAllActivitys.clear();
		System.exit(0);
	}
}
