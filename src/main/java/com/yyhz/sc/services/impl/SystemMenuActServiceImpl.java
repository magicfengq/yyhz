package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.SystemMenuActDao;
import com.yyhz.sc.data.model.SystemMenuAct;
import com.yyhz.sc.services.SystemMenuActService;

@Service
public class SystemMenuActServiceImpl extends BaseServiceImpl<SystemMenuAct> implements SystemMenuActService{

	@Autowired
	private SystemMenuActDao systemMenuActDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemMenuActDao);
	}
	
}