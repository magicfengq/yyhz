package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.VersionInfoDao;
import com.yyhz.sc.data.model.VersionInfo;
import com.yyhz.sc.services.VersionInfoService;
import com.yyhz.sc.services.aop.picture.ConfigFileKey;
import com.yyhz.sc.services.aop.picture.Pictureable;
import com.yyhz.utils.stream.util.StreamVO;

@Service
public class VersionInfoServiceImpl extends BaseServiceImpl<VersionInfo> implements VersionInfoService{

	@Autowired
	private VersionInfoDao versionInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(versionInfoDao);
	}

	@Override
	@Pictureable
	public int insertWithFile(VersionInfo info, @ConfigFileKey StreamVO streamVO) {
		return versionInfoDao.insert(info);
	}

	@Override
	@Pictureable
	public int updateWithFile(VersionInfo info, @ConfigFileKey StreamVO streamVO) {
		return versionInfoDao.update(info);
	}
	
}