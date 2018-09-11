package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.SiteInfoDao;
import com.yyhz.sc.data.model.SiteInfo;
import com.yyhz.sc.services.SiteInfoService;
import com.yyhz.sc.services.aop.picture.PictureList;
import com.yyhz.sc.services.aop.picture.Pictureable;

@Service
public class SiteInfoServiceImpl extends BaseServiceImpl<SiteInfo> implements SiteInfoService{

	@Autowired
	private SiteInfoDao siteInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(siteInfoDao);
	}
	
	@Override
	@Pictureable
	public PageInfo<SiteInfo> selectAll(SiteInfo info, @PictureList PageInfo<SiteInfo> pageInfo) {
		return super.selectAll(info, pageInfo);
	}
	
}