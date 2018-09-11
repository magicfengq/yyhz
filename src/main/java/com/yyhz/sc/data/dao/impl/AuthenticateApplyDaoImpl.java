package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.AuthenticateApplyDao;
import com.yyhz.sc.data.model.AuthenticateApply;
/**
 * 
 * @author mew
 *
 */
@Component
public class AuthenticateApplyDaoImpl extends BaseDaoImpl<AuthenticateApply> implements AuthenticateApplyDao{
	public AuthenticateApplyDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}