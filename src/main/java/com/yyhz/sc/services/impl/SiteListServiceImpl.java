package com.yyhz.sc.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.SiteListDao;
import com.yyhz.sc.data.model.SiteList;
import com.yyhz.sc.services.SiteListService;
import com.yyhz.sc.services.aop.picture.PictureKey;
import com.yyhz.sc.services.aop.picture.PictureList;
import com.yyhz.sc.services.aop.picture.Pictureable;
import com.yyhz.utils.UUIDUtil;
import com.yyhz.utils.stream.util.StreamVO;

@Service
public class SiteListServiceImpl extends BaseServiceImpl<SiteList> implements SiteListService{

	@Autowired
	private SiteListDao siteListDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(siteListDao);
	}
	
	@Override
	@Pictureable
	public PageInfo<SiteList> selectAll(SiteList info, @PictureList PageInfo<SiteList> pageInfo) {
		return super.selectAll(info, pageInfo);
	}

	@Override
	@Pictureable
	public int insertWithImage(SiteList info, @PictureKey StreamVO streamVO) {
		info.setId(UUIDUtil.getUUID());
		info.setCreateTime(new Date());
		info.setType("0");
		return siteListDao.insert(info);
	}

	@Override
	@Pictureable
	public int updateWithImage(SiteList info, @PictureKey StreamVO streamVO) {
		return siteListDao.update(info);
	}
	
}