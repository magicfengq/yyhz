package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.RoleInfoDao;
import com.yyhz.sc.data.model.RoleInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class RoleInfoDaoImpl extends BaseDaoImpl<RoleInfo> implements RoleInfoDao{
	public RoleInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}