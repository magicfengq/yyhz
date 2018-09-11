package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.SystemUserInfoDao;
import com.yyhz.sc.data.model.SystemUserInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemUserInfoDaoImpl extends BaseDaoImpl<SystemUserInfo> implements SystemUserInfoDao{
	public SystemUserInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}