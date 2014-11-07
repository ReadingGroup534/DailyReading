package com.aiteu.dailyreading.dealer;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.aiteu.dailyreading.MainActivity;
import com.aiteu.dailyreading.R;
import com.aiteu.dailyreading.book.ItemDaily;
import com.aiteu.dailyreading.book.PageSplitor;
import com.aiteu.http.factory.JsonHttpFactory;
import com.aiteu.http.handler.JsonHttpHandler;

public class LoadDataThread extends Thread{
	private static final String TAG = LoadDataThread.class.getSimpleName();
	
	private MainActivity activity;
	private CountDownLatch countDownLatch; //线程同步机制
	private Handler loadHandler;
	
	public LoadDataThread(MainActivity activity){
		this.activity = activity;
		countDownLatch = new CountDownLatch(1);
		
	}

	@SuppressLint("HandlerLeak")
	@Override
	public void run() {
		Looper.prepare();
		loadHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				switch(msg.what){
				case R.id.msg_quit:
					Looper.myLooper().quit();
					break;
				case R.id.msg_load:
					loadData();
					break;
				}
			}
			
		};
		countDownLatch.countDown();
		Looper.loop();
	}
	
	public Handler getHandler(){
		try{
			countDownLatch.await();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		return loadHandler;
	}
	
	public void loadData(){
		PageSplitor pageSplitor = activity.getPageSplitor();
		final String url = "http://192.168.1.192:8080/reading-web/api/list.json?limit="
				+ PageSplitor.LIMIT + "&offset=" + pageSplitor.getStart();
		Log.d(TAG, url);
		JsonHttpHandler mHandler = (JsonHttpHandler) new JsonHttpFactory()
				.create();
		JSONObject json = mHandler.getJson(url, null);
		if (json == null) {
			Message errorMsg = activity.getHandler().obtainMessage();
			errorMsg.what = R.id.msg_error;
			errorMsg.sendToTarget();
			return;
		}
		final int count = DataParser.parseDailyCount(json);
		pageSplitor.setCount(count);
		final List<ItemDaily> dailyList = DataParser.parseDailyData(json);

		if (dailyList == null) {
			Log.d(TAG, "data empty");
			Message emptyMsg = activity.getHandler().obtainMessage();
			emptyMsg.what = R.id.msg_empty;
			return;
		}
		Log.d(TAG, "list data size : " + dailyList.size());
		pageSplitor.addDailyList(dailyList);
		Message show = activity.getHandler().obtainMessage();
		show.what = R.id.msg_show;
		show.sendToTarget();
		return;
	}
}
