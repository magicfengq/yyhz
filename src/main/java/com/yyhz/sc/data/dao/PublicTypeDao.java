package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.PublicType;
/**
 * 数据访问接口
 *
 */
public interface PublicTypeDao extends BaseDao<PublicType>{
	public String sqlNameSpace=PublicTypeDao.class.getName();
}