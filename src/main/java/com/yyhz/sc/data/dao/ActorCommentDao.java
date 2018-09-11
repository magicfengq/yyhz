package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.ActorComment;
/**
 * 数据访问接口
 *
 */
public interface ActorCommentDao extends BaseDao<ActorComment>{
	public String sqlNameSpace=ActorCommentDao.class.getName();
	float selectAvgScore(ActorComment comment);
}