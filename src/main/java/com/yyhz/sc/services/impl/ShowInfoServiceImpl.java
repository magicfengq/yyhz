package com.yyhz.sc.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.ShowInfoDao;
import com.yyhz.sc.data.model.ShowInfo;
import com.yyhz.sc.services.ShowInfoService;
import com.yyhz.sc.services.aop.picture.Pictureable;
import com.yyhz.sc.services.aop.picture.VideoEntity;
import com.yyhz.sc.services.aop.picture.VideoList;

@Service
public class ShowInfoServiceImpl extends BaseServiceImpl<ShowInfo> implements ShowInfoService{

	@Autowired
	private ShowInfoDao showInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(showInfoDao);
	}

	@Override
	@Pictureable
	public PageInfo<ShowInfo> selectAll(ShowInfo info, @VideoList PageInfo<ShowInfo> pageInfo) {
		return super.selectAll(info, pageInfo);
	}

	@Override
	@Pictureable
	public ShowInfo selectById(@VideoEntity String id) {
		return super.selectById(id);
	}



	@Override
	public List<ShowInfo> selectTurnList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return showInfoDao.selectTurnList(param);
	}
	
	
}