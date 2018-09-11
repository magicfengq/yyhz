package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.ServiceInfoDao;
import com.yyhz.sc.data.model.ServiceInfo;
import com.yyhz.sc.services.ServiceInfoService;

@Service
public class ServiceInfoServiceImpl extends BaseServiceImpl<ServiceInfo> implements ServiceInfoService{

	@Autowired
	private ServiceInfoDao serviceInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(serviceInfoDao);
	}
	
}