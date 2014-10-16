package com.aiteu.http.factory;

import com.aiteu.http.handler.JsonHttpHandler;
import com.aiteu.http.inteface.HttpHandler;

public class JsonHttpFactory extends HttpFactory{

	@Override
	public HttpHandler create() {
		// TODO Auto-generated method stub
		return new JsonHttpHandler();
	}

}
