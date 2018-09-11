package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.SiteList;
/**
 * 数据访问接口
 *
 */
public interface SiteListDao extends BaseDao<SiteList>{
	public String sqlNameSpace=SiteListDao.class.getName();
}