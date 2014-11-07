package com.aiteu.dailyreading.setting;

import com.aiteu.dailyreading.R;
import com.aiteu.dailyreading.R.id;
import com.aiteu.dailyreading.R.layout;
import com.aiteu.dailyreading.update.AppUpdate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SettingActivity extends Activity {

	private RelativeLayout check_version, send_suggest, about;
	private Button setting_return;
	private AppUpdate mAppUpdate = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		findViewById();
		
		mAppUpdate = new AppUpdate(this);

		send_suggest.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("lyc", "start to launch Send MSG");
				Intent intent_sendsuggest = new Intent(SettingActivity.this,
						SendSuggestActivity.class);
				startActivity(intent_sendsuggest);
			}
		});
		about.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent_sendsuggest = new Intent(SettingActivity.this,
						AboutActivity.class);
				startActivity(intent_sendsuggest);
				Toast.makeText(getApplication(), "這是about頁面", Toast.LENGTH_SHORT).show();
			}
		});

		check_version.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mAppUpdate.check();
				Toast.makeText(getApplication(), "已經是最新版本", Toast.LENGTH_SHORT).show();
			}
		});

		setting_return.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SettingActivity.this.finish();
			}
		});

	}

	private void findViewById() {
		// TODO Auto-generated method stub
		check_version = (RelativeLayout) findViewById(R.id.check_version);
		about = (RelativeLayout) findViewById(R.id.about);
		send_suggest = (RelativeLayout) findViewById(R.id.send_suggest);
		setting_return = (Button) findViewById(R.id.setting_return_btn);
	}

	/*
	 * @Override protected void onCreate(Bundle savedInstanceState) { // TODO
	 * Auto-generated method stub super.onCreate(savedInstanceState);
	 * setContentView(R.layout.setting);
	 * 
	 * findViewById();
	 * 
	 * send_suggest.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View arg0) { // TODO Auto-generated method
	 * stub Log.i("lyc","start to launch Send MSG"); Intent intent_sendsuggest =
	 * new Intent(SettingActivity.this,SendSuggestActivity.class);
	 * startActivity(intent_sendsuggest); } });
	 * 
	 * about.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View arg0) { // TODO Auto-generated method
	 * stub Intent intent_sendsuggest = new
	 * Intent(mFragmentActivity,AboutActivity.class);
	 * startActivity(intent_sendsuggest); Toast.makeText(getApplication(),
	 * "這是about頁面", 1).show(); } });
	 * 
	 * check_version.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View arg0) { // TODO Auto-generated method
	 * stub Toast.makeText(getApplication(), "已經是最新版本", 1).show(); } });
	 * 
	 * setting_return.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View arg0) { // TODO Auto-generated method
	 * stub finish(); } }); }
	 * 
	 * private void findViewById() { // TODO Auto-generated method stub
	 * check_version = (RelativeLayout)findViewById(R.id.check_version); about =
	 * (RelativeLayout) findViewById(R.id.about); send_suggest =
	 * (RelativeLayout)findViewById(R.id.send_suggest); setting_return =
	 * (Button) findViewById(R.id.setting_return_btn); }
	 */

	/*
	 * @Override public void onActivityCreated(Bundle savedInstanceState) { //
	 * TODO Auto-generated method stub
	 * super.onActivityCreated(savedInstanceState); mParentView = getView();
	 * mFragmentActivity = getActivity();
	 * 
	 * // night_model = (RelativeLayout)
	 * mParentView.findViewById(R.id.night_model); check_version =
	 * (RelativeLayout) mParentView.findViewById(R.id.check_version); about =
	 * (RelativeLayout) mParentView.findViewById(R.id.about); send_suggest =
	 * (RelativeLayout) mParentView.findViewById(R.id.send_suggest); //
	 * setting_return = (Button)
	 * mParentView.findViewById(R.id.setting_return_btn);
	 * 
	 * send_suggest.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View arg0) { // TODO Auto-generated method
	 * stub Log.i("lyc","start to launch Send MSG"); Intent intent_sendsuggest =
	 * new Intent(mFragmentActivity,SendSuggestActivity.class);
	 * startActivity(intent_sendsuggest); } });
	 * 
	 * about.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View arg0) { // TODO Auto-generated method
	 * stub Intent intent_sendsuggest = new
	 * Intent(mFragmentActivity,AboutActivity.class);
	 * startActivity(intent_sendsuggest); Toast.makeText(getActivity(),
	 * "這是about頁面", 1).show(); } });
	 * 
	 * check_version.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View arg0) { // TODO Auto-generated method
	 * stub Toast.makeText(getActivity(), "已經是最新版本", 1).show(); } });
	 * 
	 * // setting_return.setOnClickListener(new View.OnClickListener() { // //
	 * @Override // public void onClick(View arg0) { // // TODO Auto-generated
	 * method stub // getActivity().finish(); // } // }); }
	 */

}
