package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.SystemUserInfo;
/**
 * 数据访问接口
 *
 */
public interface SystemUserInfoDao extends BaseDao<SystemUserInfo>{
	public String sqlNameSpace=SystemUserInfoDao.class.getName();
}