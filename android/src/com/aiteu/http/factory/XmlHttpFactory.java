package com.aiteu.http.factory;

import com.aiteu.http.handler.XmlHttpHandler;
import com.aiteu.http.inteface.HttpHandler;

public class XmlHttpFactory extends HttpFactory{

	@Override
	public HttpHandler create() {
		// TODO Auto-generated method stub
		return new XmlHttpHandler();
	}

}
