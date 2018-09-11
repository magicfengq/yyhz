package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.AnnounceInfo;
/**
 * 数据访问接口
 *
 */
public interface AnnounceInfoDao extends BaseDao<AnnounceInfo>{
	public String sqlNameSpace=AnnounceInfoDao.class.getName();
}