package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.ServiceInfo;
/**
 * 数据访问接口
 *
 */
public interface ServiceInfoDao extends BaseDao<ServiceInfo>{
	public String sqlNameSpace=ServiceInfoDao.class.getName();
}