package com.yyhz.sc.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.ShowCommentDao;
import com.yyhz.sc.data.model.ShowComment;
import com.yyhz.sc.services.ShowCommentService;

@Service
public class ShowCommentServiceImpl extends BaseServiceImpl<ShowComment> implements ShowCommentService{

	@Autowired
	private ShowCommentDao showCommentDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(showCommentDao);
	}

	@Override
	public List<ShowComment> selectShowCommentNum(Map<String, Object> commentParams) {
		return showCommentDao.selectShowCommentNum(commentParams);
	}
	
}