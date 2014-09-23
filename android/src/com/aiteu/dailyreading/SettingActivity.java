package com.aiteu.dailyreading;

import com.aiteu.dailyreading.setting.AboutActivity;
import com.aiteu.dailyreading.setting.SendSuggestActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SettingActivity extends Fragment {
	
	private RelativeLayout night_model,check_version,send_suggest,about;
	private View mParentView;
	private FragmentActivity mFragmentActivity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.setting, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mParentView = getView();
		mFragmentActivity = getActivity();
		
		night_model = (RelativeLayout) mParentView.findViewById(R.id.night_model);
		check_version = (RelativeLayout) mParentView.findViewById(R.id.check_version);
		about = (RelativeLayout) mParentView.findViewById(R.id.about);
		send_suggest = (RelativeLayout) mParentView.findViewById(R.id.send_suggest);
		
		send_suggest.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("lyc","start to launch Send MSG");
				Intent intent_sendsuggest = new Intent(mFragmentActivity,SendSuggestActivity.class);
				startActivity(intent_sendsuggest);
			}
		});
		
		about.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent_sendsuggest = new Intent(mFragmentActivity,AboutActivity.class);
				startActivity(intent_sendsuggest);
				Toast.makeText(getActivity(), "這是about頁面", 1).show();
			}
		});
		
		check_version.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "已經是最新版本", 1).show();
			}
		});
	}

}
