package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.AudioInfoDao;
import com.yyhz.sc.data.model.AudioInfo;
import com.yyhz.sc.data.model.VersionInfo;
import com.yyhz.sc.services.AudioInfoService;
import com.yyhz.sc.services.aop.picture.ConfigFileKey;
import com.yyhz.sc.services.aop.picture.Pictureable;
import com.yyhz.utils.stream.util.StreamVO;


@Service
public class AudioInfoServiceImpl extends BaseServiceImpl<AudioInfo> implements AudioInfoService{

	@Autowired
	private AudioInfoDao dao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(dao);
	}

	@Override
	@Pictureable
	public int insertWithFile(AudioInfo info, @ConfigFileKey StreamVO streamVO) {
		return dao.insert(info);
	}
}