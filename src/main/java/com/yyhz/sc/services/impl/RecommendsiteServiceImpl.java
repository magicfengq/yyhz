package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.RecommendsiteDao;
import com.yyhz.sc.data.model.Recommendsite;
import com.yyhz.sc.services.RecommendsiteService;

@Service
public class RecommendsiteServiceImpl extends BaseServiceImpl<Recommendsite> implements RecommendsiteService{

	@Autowired
	private RecommendsiteDao recommendsiteDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(recommendsiteDao);
	}
	
}