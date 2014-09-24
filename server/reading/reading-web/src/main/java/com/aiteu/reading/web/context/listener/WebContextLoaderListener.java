package com.aiteu.reading.web.context.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.aiteu.reading.web.WebConstants;

/**
 * 项目启动监听
 * @author liwei
 *
 */
public class WebContextLoaderListener extends ContextLoaderListener{
	
	public void contextInitialized(ServletContextEvent event){
		super.contextInitialized(event);
		this.initApplication(event);
	} 
	
	/**
	 * 初始化application环境。<br/>
	 * 
	 * @param event	event
	 */
	private void initApplication(ServletContextEvent event) {
		if (null != event) {
			ServletContext sc = event.getServletContext();
			/* 初始化application级别的变量。 */
			sc.setAttribute("mainServer", WebConstants.MAIN_SERVER);
		}
	}
}
