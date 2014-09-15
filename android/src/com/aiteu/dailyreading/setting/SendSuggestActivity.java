package com.aiteu.dailyreading.setting;


import com.aiteu.dailyreading.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SendSuggestActivity extends Activity {
	private EditText content;
	private Button send,return_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendsuggest_view);
		Log.i("lyc","Already launched Send MSG");
		content = (EditText) findViewById(R.id.suggest_et);
		send = (Button) findViewById(R.id.sendsuggest);
		return_btn=(Button) findViewById(R.id.send_return_btn);
		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String strEmailBody = content.getText().toString();
				Intent intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				String[] strEmailReciver = new String[]{"lycyz123@163.com"};
				intent.putExtra(android.content.Intent.EXTRA_EMAIL,strEmailReciver);
				intent.putExtra(android.content.Intent.EXTRA_TEXT, strEmailBody);
				startActivity(Intent.createChooser(intent,"发送"));
			}
		});
		
		return_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
