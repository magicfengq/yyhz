package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.CardInfo;
/**
 * 数据访问接口
 *
 */
public interface CardInfoDao extends BaseDao<CardInfo>{
	public String sqlNameSpace=CardInfoDao.class.getName();
}