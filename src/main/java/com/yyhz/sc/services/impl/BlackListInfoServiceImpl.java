package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.BlackListInfoDao;
import com.yyhz.sc.data.model.BlackListInfo;
import com.yyhz.sc.services.BlackListInfoService;

@Service
public class BlackListInfoServiceImpl extends BaseServiceImpl<BlackListInfo>  implements BlackListInfoService{
	
	
	@Autowired
	private BlackListInfoDao serviceInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(serviceInfoDao);
	}
	
}