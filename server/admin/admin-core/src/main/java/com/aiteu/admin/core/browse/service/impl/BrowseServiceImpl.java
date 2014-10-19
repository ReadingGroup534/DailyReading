package com.aiteu.admin.core.browse.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aiteu.admin.core.browse.dao.BrowseDao;
import com.aiteu.admin.core.browse.service.BrowseService;

@Service("browseService")
public class BrowseServiceImpl implements BrowseService{

	@Autowired
	private BrowseDao browseDao;
	
	public List<Map<String, Object>> getBrowses() {
		// TODO Auto-generated method stub
		return browseDao.getBrowses();
	}

}
