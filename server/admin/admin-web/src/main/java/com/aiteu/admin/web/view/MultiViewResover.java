package com.aiteu.admin.web.view;
import java.util.Locale;
import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

import com.aiteu.admin.core.util.StringUtils;


/**
 * 自定义多视图解析：能根据url后缀展示相应的试图
 * @author liwei
 *
 */
public class MultiViewResover extends AbstractCachingViewResolver implements Ordered{
	
	private Map<String,ViewResolver> resolvers = null;
	private int order = Integer.MAX_VALUE;

	@Override
	protected View loadView(String viewName, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		String fileExtension = StringUtils.fileExtension(viewName);
		//如果自定义试图没办法解析请求的view,那么就让其他的解析器去解析
		if(null == fileExtension){
			return null;
		}
		//System.out.println("extension : "+fileExtension+", name: "+StringUtils.filePrefix(viewName));
		ViewResolver resolver = resolvers.get(fileExtension);
		return resolver == null ? null : resolver.resolveViewName(StringUtils.filePrefix(viewName), locale);
	}

	public Map<String, ViewResolver> getResolvers() {
		return resolvers;
	}

	public void setResolvers(Map<String, ViewResolver> resolvers) {
		this.resolvers = resolvers;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
