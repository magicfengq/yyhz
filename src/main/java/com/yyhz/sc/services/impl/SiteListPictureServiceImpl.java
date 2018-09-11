package com.yyhz.sc.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.SiteListPictureDao;
import com.yyhz.sc.data.model.SiteListPicture;
import com.yyhz.sc.services.SiteListPictureService;
import com.yyhz.sc.services.aop.picture.PictureList;
import com.yyhz.sc.services.aop.picture.Pictureable;

@Service
public class SiteListPictureServiceImpl extends BaseServiceImpl<SiteListPicture> implements SiteListPictureService{

	@Autowired
	private SiteListPictureDao siteListPictureDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(siteListPictureDao);
	}

	@Override
	@Pictureable
	public List<SiteListPicture> selectAll(@PictureList SiteListPicture info) {
		return super.selectAll(info);
	}
	
	
	
}