package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.VersionInfo;
/**
 * 数据访问接口
 *
 */
public interface VersionInfoDao extends BaseDao<VersionInfo>{
	public String sqlNameSpace=VersionInfoDao.class.getName();
}