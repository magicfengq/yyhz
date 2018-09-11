package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.MessageInfo;
/**
 * 数据访问接口
 *
 */
public interface MessageInfoDao extends BaseDao<MessageInfo>{
	public String sqlNameSpace=MessageInfoDao.class.getName();
}