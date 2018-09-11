package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.MessageInfoDao;
import com.yyhz.sc.data.model.MessageInfo;
import com.yyhz.sc.services.MessageInfoService;

@Service
public class MessageInfoServiceImpl extends BaseServiceImpl<MessageInfo> implements MessageInfoService{

	@Autowired
	private MessageInfoDao messageInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(messageInfoDao);
	}
	
}