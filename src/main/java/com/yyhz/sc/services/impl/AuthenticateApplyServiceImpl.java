package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.AuthenticateApplyDao;
import com.yyhz.sc.data.model.AuthenticateApply;
import com.yyhz.sc.services.AuthenticateApplyService;

@Service
public class AuthenticateApplyServiceImpl extends BaseServiceImpl<AuthenticateApply> implements AuthenticateApplyService{

	@Autowired
	private AuthenticateApplyDao authenticateApplyDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(authenticateApplyDao);
	}
	
}