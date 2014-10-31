package com.aiteu.dailyreading.handler;

import com.aiteu.dailyreading.MainActivity;

import android.os.Handler;
import android.os.Message;

public class HomeHandler extends Handler{
	
	private MainActivity mActivity = null;
	
	public HomeHandler(MainActivity main){
		this.mActivity = main;
	}

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
	}
}
