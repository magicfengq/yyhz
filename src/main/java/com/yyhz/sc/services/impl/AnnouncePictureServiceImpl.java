package com.yyhz.sc.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.AnnouncePictureDao;
import com.yyhz.sc.data.model.AnnouncePicture;
import com.yyhz.sc.services.AnnouncePictureService;
import com.yyhz.sc.services.aop.picture.PictureList;
import com.yyhz.sc.services.aop.picture.Pictureable;

@Service
public class AnnouncePictureServiceImpl extends BaseServiceImpl<AnnouncePicture> implements AnnouncePictureService{

	@Autowired
	private AnnouncePictureDao announcePictureDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(announcePictureDao);
	}

	@Override
	@Pictureable
	public List<AnnouncePicture> selectAll(@PictureList AnnouncePicture info) {
		return super.selectAll(info);
	}
	
	
	
}