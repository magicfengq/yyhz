package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.AuthenticateApply;
/**
 * 数据访问接口
 *
 */
public interface AuthenticateApplyDao extends BaseDao<AuthenticateApply>{
	public String sqlNameSpace=AuthenticateApplyDao.class.getName();
}