package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.SystemRoleInfo;
/**
 * 数据访问接口
 *
 */
public interface SystemRoleInfoDao extends BaseDao<SystemRoleInfo>{
	public String sqlNameSpace=SystemRoleInfoDao.class.getName();
}