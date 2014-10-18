package com.aiteu.reading.web.view;

import java.util.Locale;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

public class JsonViewResolver extends AbstractCachingViewResolver implements Ordered{
	
	private int order = Integer.MAX_VALUE;
	private View jsonView = null;
	
	@Override
	protected View loadView(String arg0, Locale arg1) throws Exception {
		// TODO Auto-generated method stub
		if(jsonView != null){
			return this.jsonView;
		}
		return null;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public View getJsonView() {
		return jsonView;
	}

	public void setJsonView(View jsonView) {
		this.jsonView = jsonView;
	}
}
