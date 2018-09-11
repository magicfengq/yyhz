package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.ActorInfo;
/**
 * 数据访问接口
 *
 */
public interface ActorInfoDao extends BaseDao<ActorInfo>{
	public String sqlNameSpace=ActorInfoDao.class.getName();
}