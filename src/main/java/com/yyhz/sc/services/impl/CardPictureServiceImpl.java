package com.yyhz.sc.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.CardPictureDao;
import com.yyhz.sc.data.model.CardPicture;
import com.yyhz.sc.services.CardPictureService;
import com.yyhz.sc.services.aop.picture.PictureList;
import com.yyhz.sc.services.aop.picture.Pictureable;

@Service
public class CardPictureServiceImpl extends BaseServiceImpl<CardPicture> implements CardPictureService{

	@Autowired
	private CardPictureDao cardPictureDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(cardPictureDao);
	}

	@Override
	@Pictureable
	public List<CardPicture> selectAll(@PictureList CardPicture info) {
		return super.selectAll(info);
	}
	
	
	
}