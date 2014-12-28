package com.aiteu.reading.web.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
	
	public Map<String, String> initForm(HttpServletRequest req){
		Map<String, String> form = new HashMap<String, String>();
		Map<String, Object> reqParam = req.getParameterMap();
		Iterator<Map.Entry<String, Object>> entries = reqParam.entrySet().iterator();
		String name = "";
		String value = "";
		Map.Entry<String, Object> entry = null;
		while(entries.hasNext()){
			entry = entries.next();
			name = entry.getKey();
			Object valueObj = entry.getValue();
			if(valueObj == null){
				value = "";
			}else if(valueObj instanceof String[]){
				String[] values = (String[])valueObj;
	            for(int i=0;i<values.length;i++){
	                value = values[i] + ",";
	            }
	            value = value.substring(0, value.length()-1);
			}else{
				value = valueObj.toString();
			}
			form.put(name, value);
		}
		
		return form;
	}
}
