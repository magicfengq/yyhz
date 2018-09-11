package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.ServiceInfoDao;
import com.yyhz.sc.data.model.ServiceInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class ServiceInfoDaoImpl extends BaseDaoImpl<ServiceInfo> implements ServiceInfoDao{
	public ServiceInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}