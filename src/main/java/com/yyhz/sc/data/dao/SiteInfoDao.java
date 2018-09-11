package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.SiteInfo;
/**
 * 数据访问接口
 *
 */
public interface SiteInfoDao extends BaseDao<SiteInfo>{
	public String sqlNameSpace=SiteInfoDao.class.getName();
}