package com.aiteu.dailyreading.dealer;

import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;

import com.aiteu.dailyreading.MainActivity;
import com.aiteu.dailyreading.R;
import com.aiteu.dailyreading.handler.HomeHandler;
import com.aiteu.dailyreading.helper.NetworkHelper;
import com.aiteu.http.factory.HttpFactory;
import com.aiteu.http.factory.JsonHttpFactory;
import com.aiteu.http.handler.JsonHttpHandler;

public class HomeDealer {
	private static final String TAG = HomeDealer.class.getSimpleName();
	
	private MainActivity activity = null;
	private HomeHandler mHandler = null;
	
	public HomeDealer(MainActivity main, HomeHandler handler){
		this.activity = main;
		this.mHandler = handler;
	}
	
	/**
	 * 加载当天的数据
	 */
	public void loadDailyData(){
		
	}
	
	/**
	 * 检测是否有更新
	 */
	public void checkUpdate(){
		if(!NetworkHelper.isNetworkAvailable(activity)){
			Toast.makeText(activity, activity.getText(R.string.msg_net_unavailable), Toast.LENGTH_SHORT).show();
			return;
		}
		if(!NetworkHelper.isWifiNetwork(activity)){
			Log.d(TAG, "don't update check not wifi");
			Toast.makeText(activity, activity.getText(R.string.msg_not_wifi), Toast.LENGTH_LONG).show();
			return;
		}
		//check self update
		
	}
	
	
}
