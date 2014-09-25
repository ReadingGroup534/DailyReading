package com.aiteu.reading.base;

import java.util.HashMap;
import java.util.Map;

public class ContentValues {
	
	private Map<String, Object> values;
	
	public ContentValues(){
		values = new HashMap<String, Object>();
	}
	
	public void put(String key, int value){
		this.values.put(key, value);
	}
	
	public void put(String key, boolean value){
		this.values.put(key, value);
	}
	
	public void put(String key, long value){
		this.values.put(key, value);
	}
	
	public void put(String key, double value){
		this.values.put(key, value);
	}
	
	public void put(String key, String value){
		this.values.put(key, value);
	}
}
