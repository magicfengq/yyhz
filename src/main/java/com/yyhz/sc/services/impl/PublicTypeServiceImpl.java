package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.PublicTypeDao;
import com.yyhz.sc.data.model.PublicType;
import com.yyhz.sc.services.PublicTypeService;

@Service
public class PublicTypeServiceImpl extends BaseServiceImpl<PublicType> implements PublicTypeService{

	@Autowired
	private PublicTypeDao publicTypeDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(publicTypeDao);
	}
	
}