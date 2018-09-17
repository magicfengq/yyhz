package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.AudioInfo;

/**
 * 数据访问接口
 *
 */
public interface AudioInfoDao extends BaseDao<AudioInfo>{
	public String sqlNameSpace=AudioInfoDao.class.getName();
}