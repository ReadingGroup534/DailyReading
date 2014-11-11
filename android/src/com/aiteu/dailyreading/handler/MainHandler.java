package com.aiteu.dailyreading.handler;

import com.aiteu.dailyreading.MainActivity;
import com.aiteu.dailyreading.R;
import com.aiteu.dailyreading.dealer.LoadDataThread;
import com.aiteu.dailyreading.helper.NetworkHelper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * 实现主线程的ui显示，并且和子线程进行通信
 * @author liwei
 *
 */
public class MainHandler extends Handler{

	private MainActivity activity;
	private LoadDataThread mLoadDataThread;
	
	public MainHandler(MainActivity activity){
		this.activity = activity;
		mLoadDataThread = new LoadDataThread(activity);
		mLoadDataThread.start();
	}
	
	@Override
	public void handleMessage(Message msg) {
		switch(msg.what){
		case R.id.msg_show:
			activity.showDailyList();
			break;
		case R.id.msg_error:
			Bundle d = msg.getData();
			Toast.makeText(activity, activity.getString(R.string.msg_error, d.getString("status")+":"+d.getString("message")), Toast.LENGTH_LONG).show();
			activity.showError();
			break;
		case R.id.msg_empty:
			activity.showEmpty();
			break;
		}
	}
	
	public void initData(){
		if(checkNetwork()){
			//开启自动更新线程
			
			//开启加载远程数据线程
			Message loadMsg = mLoadDataThread.getHandler().obtainMessage(R.id.msg_load);
			loadMsg.sendToTarget();
		}else{
			activity.showNetworkUnavailable();
		}
	}
	
	/**
	 * 检查网络状态
	 */
	private boolean checkNetwork(){
		if(NetworkHelper.isNetworkAvailable(activity)){
			//非wifi网络提醒用户数据流量
			if(!NetworkHelper.isWifiNetwork(activity)){
				Toast.makeText(activity, activity.getText(R.string.msg_not_wifi), Toast.LENGTH_SHORT).show();
			}
			return true;
		}else{
			//没有可用的网络则显示网络链接失败页面
			return false;
		}
	}
}
