package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.Feedback;
/**
 * 数据访问接口
 *
 */
public interface FeedbackDao extends BaseDao<Feedback>{
	public String sqlNameSpace=FeedbackDao.class.getName();
}