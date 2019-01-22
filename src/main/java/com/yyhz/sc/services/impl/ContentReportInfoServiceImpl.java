package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.ContentReportInfoDao;
import com.yyhz.sc.data.model.ContentReportInfo;
import com.yyhz.sc.services.ContentReportInfoService;

@Service
public class ContentReportInfoServiceImpl extends BaseServiceImpl<ContentReportInfo> implements ContentReportInfoService{
	@Autowired
	private ContentReportInfoDao contentReportInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(contentReportInfoDao);
	}
	
}