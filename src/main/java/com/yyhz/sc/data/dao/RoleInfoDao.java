package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.RoleInfo;
/**
 * 数据访问接口
 *
 */
public interface RoleInfoDao extends BaseDao<RoleInfo>{
	public String sqlNameSpace=RoleInfoDao.class.getName();
}