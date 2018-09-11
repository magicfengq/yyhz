package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;

import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.SystemMenuActDao;
import com.yyhz.sc.data.model.SystemMenuAct;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemMenuActDaoImpl extends BaseDaoImpl<SystemMenuAct> implements SystemMenuActDao{
	public SystemMenuActDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}