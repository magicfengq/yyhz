package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.AudioInfoDao;
import com.yyhz.sc.data.model.AudioInfo;
import com.yyhz.sc.services.AudioInfoService;


@Service
public class AudioInfoServiceImpl extends BaseServiceImpl<AudioInfo> implements AudioInfoService{

	@Autowired
	private AudioInfoDao dao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(dao);
	}
}