package com.yyhz.sc.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.ShowCommentPraiseDao;
import com.yyhz.sc.data.model.ShowCommentPraise;
import com.yyhz.sc.services.ShowCommentPraiseService;

@Service
public class ShowCommentPraiseServiceImpl extends BaseServiceImpl<ShowCommentPraise> implements ShowCommentPraiseService{

	@Autowired
	private ShowCommentPraiseDao showCommentPraiseDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(showCommentPraiseDao);
	}

	@Override
	public List<ShowCommentPraise> selectShowCommentPraiseNum(Map<String, Object> commentParams) {
		return showCommentPraiseDao.selectShowCommentPraiseNum(commentParams);
	}
	
}