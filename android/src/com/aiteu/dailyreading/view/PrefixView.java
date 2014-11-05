package com.aiteu.dailyreading.view;

import com.aiteu.dailyreading.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

public class PrefixView extends TextView{
	
	private String prefix = "";

	public PrefixView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public PrefixView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PrefixView);
		prefix = a.getString(R.styleable.PrefixView_prefix);
		a.recycle();
	}

	public PrefixView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		// TODO Auto-generated method stub
		super.setText(prefix+text, type);
	}
	
	
	
}
