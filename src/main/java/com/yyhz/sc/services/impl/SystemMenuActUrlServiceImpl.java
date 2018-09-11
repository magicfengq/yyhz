package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.SystemMenuActUrlDao;
import com.yyhz.sc.data.model.SystemMenuActUrl;
import com.yyhz.sc.services.SystemMenuActUrlService;

@Service
public class SystemMenuActUrlServiceImpl extends BaseServiceImpl<SystemMenuActUrl> implements SystemMenuActUrlService{

	@Autowired
	private SystemMenuActUrlDao systemMenuActUrlDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemMenuActUrlDao);
	}
	
}