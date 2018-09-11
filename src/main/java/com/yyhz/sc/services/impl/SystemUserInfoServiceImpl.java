package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.SystemUserInfoDao;
import com.yyhz.sc.data.model.SystemUserInfo;
import com.yyhz.sc.services.SystemUserInfoService;

@Service
public class SystemUserInfoServiceImpl extends BaseServiceImpl<SystemUserInfo> implements SystemUserInfoService{

	@Autowired
	private SystemUserInfoDao systemUserInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemUserInfoDao);
	}
	
}