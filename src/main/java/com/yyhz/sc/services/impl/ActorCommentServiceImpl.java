package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.ActorCommentDao;
import com.yyhz.sc.data.model.ActorComment;
import com.yyhz.sc.services.ActorCommentService;

@Service
public class ActorCommentServiceImpl extends BaseServiceImpl<ActorComment> implements ActorCommentService{

	@Autowired
	private ActorCommentDao actorCommentDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(actorCommentDao);
	}

	@Override
	public float getAvgScore(ActorComment comment) {
		return actorCommentDao.selectAvgScore(comment);
	}

	@Override
	public float getAvgScore(String id) {
		ActorComment condition = new ActorComment();
		condition.setActorId(id);
		
		return actorCommentDao.selectAvgScore(condition);
	}
	
}