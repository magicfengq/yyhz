package com.yyhz.sc.services;

import com.yyhz.sc.data.model.ActorComment;
import com.yyhz.sc.base.service.BaseService;

public interface ActorCommentService extends BaseService<ActorComment>{
	
	public float getAvgScore(ActorComment comment);
	public float getAvgScore(String id);
}
