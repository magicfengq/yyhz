package com.yyhz.sc.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.ShowInfoPicturesDao;
import com.yyhz.sc.data.model.ShowInfoPictures;
import com.yyhz.sc.services.ShowInfoPicturesService;
import com.yyhz.sc.services.aop.picture.PictureList;
import com.yyhz.sc.services.aop.picture.Pictureable;

@Service
public class ShowInfoPicturesServiceImpl extends BaseServiceImpl<ShowInfoPictures> implements ShowInfoPicturesService{

	@Autowired
	private ShowInfoPicturesDao showInfoPicturesDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(showInfoPicturesDao);
	}

	@Override
	@Pictureable
	public List<ShowInfoPictures> selectAll(@PictureList ShowInfoPictures info) {
		return super.selectAll(info);
	}
	
	@Override
	@Pictureable
	public List<ShowInfoPictures> selectAll(@PictureList Map<String,Object> params) {
		return super.selectAll(params);
	}
	
}