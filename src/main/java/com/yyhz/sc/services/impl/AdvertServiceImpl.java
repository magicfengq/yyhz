package com.yyhz.sc.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.AdvertDao;
import com.yyhz.sc.data.model.Advert;
import com.yyhz.sc.services.AdvertService;
import com.yyhz.sc.services.aop.picture.PictureKey;
import com.yyhz.sc.services.aop.picture.PictureList;
import com.yyhz.sc.services.aop.picture.Pictureable;
import com.yyhz.utils.UUIDUtil;
import com.yyhz.utils.stream.util.StreamVO;

@Service
public class AdvertServiceImpl extends BaseServiceImpl<Advert> implements AdvertService{

	@Autowired
	private AdvertDao advertDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(advertDao);
	}

	@Override
	@Pictureable
	public PageInfo<Advert> selectAll(Advert info, @PictureList PageInfo<Advert> pageInfo) {
		return super.selectAll(info, pageInfo);
	}

	@Override
	@Pictureable
	public int insertWithImage(Advert info, @PictureKey StreamVO streamVO) {
		info.setId(UUIDUtil.getUUID());
		info.setCreateTime(new Date());
		info.setOperateTime(new Date());
		return advertDao.insert(info);
	}

	@Override
	@Pictureable
	public int updateWithImage(Advert info, @PictureKey StreamVO streamVO) {
		return advertDao.update(info);
	}

	@Override
	public List<Advert> selectEntityByIds(List<String> idList) {
		return advertDao.selectEntityByIds(idList);
	}
	
	
	
}