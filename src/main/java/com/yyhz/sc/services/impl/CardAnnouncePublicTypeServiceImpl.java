package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.CardAnnouncePublicTypeDao;
import com.yyhz.sc.data.model.CardAnnouncePublicType;
import com.yyhz.sc.services.CardAnnouncePublicTypeService;

@Service
public class CardAnnouncePublicTypeServiceImpl extends BaseServiceImpl<CardAnnouncePublicType> implements CardAnnouncePublicTypeService{

	@Autowired
	private CardAnnouncePublicTypeDao cardAnnouncePublicTypeDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(cardAnnouncePublicTypeDao);
	}
	
}