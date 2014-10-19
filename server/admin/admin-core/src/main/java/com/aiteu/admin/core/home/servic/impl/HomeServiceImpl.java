package com.aiteu.admin.core.home.servic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.stereotype.Service;

import com.aiteu.admin.core.home.service.HomeService;

@Service("homeService")
public class HomeServiceImpl implements HomeService{

	public List<Map<String, String>> getIndexList() {
		// TODO Auto-generated method stub
		ResourceBundle config = ResourceBundle
				.getBundle("config/index-config");
		String names = config.getString("index_name");
		String values = config.getString("index_url_of_name");
		String[] nameAry = names.split(",");
		String[] urlAry = values.split(",");
		List<Map<String, String>> indexList = new ArrayList<Map<String,String>>();
		for(int i=0;i < nameAry.length;i ++){
			Map<String, String> item = new HashMap<String, String>();
			item.put("name", nameAry[i]);
			item.put("url", urlAry[i]);
			indexList.add(item);
		}
		return indexList;
	}

}
