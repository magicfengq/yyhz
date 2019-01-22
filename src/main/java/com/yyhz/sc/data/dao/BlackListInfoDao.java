package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.BlackListInfo;
/**
 * 数据访问接口
 *
 */
public interface BlackListInfoDao extends BaseDao<BlackListInfo>{
	public String sqlNameSpace=BlackListInfoDao.class.getName();
}