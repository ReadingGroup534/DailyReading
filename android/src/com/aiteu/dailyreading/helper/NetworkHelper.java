package com.aiteu.dailyreading.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络诊断和检测
 * @author liwei
 *
 */
public final class NetworkHelper {
	
	public static boolean isNetworkAvailable(Context context){
		ConnectivityManager conn = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = conn.getActiveNetworkInfo();
		if(info != null && info.isConnected()){
			return true;
		}else{
			return false;
		}
	} 
	
	public static boolean isWifiNetwork(Context context){
		ConnectivityManager conn = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(info != null && info.isConnected()){
			return true;
		}else{
			return false;
		}
	}
}
