package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.SystemRoleInfoDao;
import com.yyhz.sc.data.model.SystemRoleInfo;
import com.yyhz.sc.services.SystemRoleInfoService;

@Service
public class SystemRoleInfoServiceImpl extends BaseServiceImpl<SystemRoleInfo> implements SystemRoleInfoService{

	@Autowired
	private SystemRoleInfoDao systemRoleInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemRoleInfoDao);
	}
	
}