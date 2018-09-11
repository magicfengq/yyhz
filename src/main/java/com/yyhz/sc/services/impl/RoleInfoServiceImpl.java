package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.RoleInfoDao;
import com.yyhz.sc.data.model.RoleInfo;
import com.yyhz.sc.services.RoleInfoService;

@Service
public class RoleInfoServiceImpl extends BaseServiceImpl<RoleInfo> implements RoleInfoService{

	@Autowired
	private RoleInfoDao roleInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(roleInfoDao);
	}
	
}