package com.aiteu.dailyreading.dealer;

import java.util.List;

import org.json.JSONObject;

import com.aiteu.dailyreading.MainActivity;
import com.aiteu.dailyreading.book.ItemDaily;
import com.aiteu.dailyreading.book.PageSplitor;
import com.aiteu.http.factory.JsonHttpFactory;
import com.aiteu.http.handler.JsonHttpHandler;

import android.os.AsyncTask;
import android.util.Log;

public class LoadDailyDataTask extends AsyncTask<PageSplitor, Integer, PageSplitor> {
	private static final String TAG = LoadDailyDataTask.class.getSimpleName();
	
	private MainActivity mainActivity = null;
	
	public LoadDailyDataTask(MainActivity homeActivity) {
		mainActivity = homeActivity;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected PageSplitor doInBackground(PageSplitor... params) {
		PageSplitor mPageSplitor = params[0];
		final String url = "http://192.168.2.103:8080/reading-web/api/list.json?limit="
				+ PageSplitor.LIMIT + "&offset=" + mPageSplitor.getStart();
		Log.d(TAG, url);
		JsonHttpHandler mHandler = (JsonHttpHandler) new JsonHttpFactory()
				.create();
		JSONObject json = mHandler.getJson(url, null);
		if (json == null) {
			return mPageSplitor;
		}
		final int count = DataParser.parseDailyCount(json);
		mPageSplitor.setCount(count);
		final List<ItemDaily> dailyList = DataParser.parseDailyData(json);

		if (dailyList == null) {
			Log.d(TAG, "data empty");
			return mPageSplitor;
		}
		Log.d(TAG, "list data size : " + dailyList.size());
		mPageSplitor.addDailyList(dailyList);
		return mPageSplitor;
	}

	@Override
	protected void onPostExecute(PageSplitor result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		//mainActivity.showDailyList(result);
	}

}
