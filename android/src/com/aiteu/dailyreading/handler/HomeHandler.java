package com.aiteu.dailyreading.handler;

import com.aiteu.dailyreading.MainActivity;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

public class HomeHandler extends Handler{
	
	private MainActivity mActivity = null;
	
	public HomeHandler(MainActivity main, Looper looper){
		this.mActivity = main;
	}

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
	}
}
