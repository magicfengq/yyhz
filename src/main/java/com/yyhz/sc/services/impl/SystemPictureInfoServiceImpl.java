package com.yyhz.sc.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.SystemPictureInfoDao;
import com.yyhz.sc.data.model.SystemPictureInfo;
import com.yyhz.sc.services.SystemPictureInfoService;

@Service
public class SystemPictureInfoServiceImpl extends BaseServiceImpl<SystemPictureInfo> implements SystemPictureInfoService{

	@Autowired
	private SystemPictureInfoDao systemPictureInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemPictureInfoDao);
	}

	@Override
	public List<SystemPictureInfo> selectByUuids(List<String> imageUuidList) {
		return systemPictureInfoDao.selectByUuids(imageUuidList);
	}
	
}