package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.SystemRoleInfoDao;
import com.yyhz.sc.data.model.SystemRoleInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemRoleInfoDaoImpl extends BaseDaoImpl<SystemRoleInfo> implements SystemRoleInfoDao{
	public SystemRoleInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}