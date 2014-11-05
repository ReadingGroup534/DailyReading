package com.aiteu.dailyreading;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_lay);
		
		Handler handler = new Handler();
		handler.postDelayed(new splashHandler() , 1500);
				
	}
	
	
	class splashHandler implements Runnable{
		

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Intent intent = new Intent(SplashActivity.this,MainActivity.class);
			startActivity(intent);
			SplashActivity.this.finish();
		}
	
	}

}
