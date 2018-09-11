package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.SystemMenuActUrlDao;
import com.yyhz.sc.data.model.SystemMenuActUrl;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemMenuActUrlDaoImpl extends BaseDaoImpl<SystemMenuActUrl> implements SystemMenuActUrlDao{
	public SystemMenuActUrlDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}