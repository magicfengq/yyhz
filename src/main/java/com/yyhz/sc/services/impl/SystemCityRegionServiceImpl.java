package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.SystemCityRegionDao;
import com.yyhz.sc.data.model.SystemCityRegion;
import com.yyhz.sc.services.SystemCityRegionService;

@Service
public class SystemCityRegionServiceImpl extends BaseServiceImpl<SystemCityRegion> implements SystemCityRegionService{

	@Autowired
	private SystemCityRegionDao systemCityRegionDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemCityRegionDao);
	}
	
}