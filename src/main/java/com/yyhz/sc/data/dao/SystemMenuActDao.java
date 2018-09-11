package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.SystemMenuAct;
/**
 * 数据访问接口
 *
 */
public interface SystemMenuActDao extends BaseDao<SystemMenuAct>{
	public String sqlNameSpace=SystemMenuActDao.class.getName();
}