package com.yyhz.sc.data.dao.impl;
import org.springframework.stereotype.Component;

import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.BlackListInfoDao;
import com.yyhz.sc.data.model.BlackListInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class BlackListInfoDaoImpl extends BaseDaoImpl<BlackListInfo> implements BlackListInfoDao{
	public BlackListInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}