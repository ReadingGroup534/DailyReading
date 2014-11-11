package com.aiteu.http.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceUtil {
	private static final String PREF_READING = "pref_reading";
	public static final String LAST_REFREAHTIME = "last_refresh_time";
	
	public static long getLastRefreshTime(Context context){
		SharedPreferences pref = context.getSharedPreferences(PREF_READING, Context.MODE_PRIVATE);
		return pref.getLong(LAST_REFREAHTIME, 0);
	}
	
	public static void setLastRefreshTime(Context context, long lastRefresh){
		SharedPreferences pref = context.getSharedPreferences(PREF_READING, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putLong(LAST_REFREAHTIME, lastRefresh);
		editor.commit();
	}
}
