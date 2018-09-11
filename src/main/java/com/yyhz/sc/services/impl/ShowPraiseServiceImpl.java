package com.yyhz.sc.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.ShowPraiseDao;
import com.yyhz.sc.data.model.ShowPraise;
import com.yyhz.sc.services.ShowPraiseService;

@Service
public class ShowPraiseServiceImpl extends BaseServiceImpl<ShowPraise> implements ShowPraiseService{

	@Autowired
	private ShowPraiseDao showPraiseDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(showPraiseDao);
	}

	@Override
	public List<ShowPraise> selectShowPraiseNum(Map<String, Object> commentParams) {
		return showPraiseDao.selectShowPraiseNum(commentParams);
	}
	
}