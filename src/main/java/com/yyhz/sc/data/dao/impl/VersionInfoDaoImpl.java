package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.VersionInfoDao;
import com.yyhz.sc.data.model.VersionInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class VersionInfoDaoImpl extends BaseDaoImpl<VersionInfo> implements VersionInfoDao{
	public VersionInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}