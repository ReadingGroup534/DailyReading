package com.aiteu.reading.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aiteu.reading.api.dao.BrowseDao;
import com.aiteu.reading.api.model.Browse;
import com.aiteu.reading.api.service.BrowseService;

@Service("browseService")
public class BrowseServiceIMPL implements BrowseService{
	
	@Autowired
	private BrowseDao browseDao;

	public List<Browse> getBrowses() {
		// TODO Auto-generated method stub
		return browseDao.getBrowses();
	}
	
}
