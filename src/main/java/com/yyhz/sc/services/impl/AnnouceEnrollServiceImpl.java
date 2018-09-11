package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.AnnouceEnrollDao;
import com.yyhz.sc.data.model.AnnouceEnroll;
import com.yyhz.sc.services.AnnouceEnrollService;
import com.yyhz.sc.services.aop.picture.PictureList;
import com.yyhz.sc.services.aop.picture.Pictureable;

@Service
public class AnnouceEnrollServiceImpl extends BaseServiceImpl<AnnouceEnroll> implements AnnouceEnrollService{

	@Autowired
	private AnnouceEnrollDao annouceEnrollDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(annouceEnrollDao);
	}
	
	@Override
	@Pictureable
	public PageInfo<AnnouceEnroll> selectAll(AnnouceEnroll info, @PictureList PageInfo<AnnouceEnroll> pageInfo) {
		return super.selectAll(info, pageInfo);
	}
	
}