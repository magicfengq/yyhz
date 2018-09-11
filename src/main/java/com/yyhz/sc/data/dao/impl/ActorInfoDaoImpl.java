package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.ActorInfoDao;
import com.yyhz.sc.data.model.ActorInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class ActorInfoDaoImpl extends BaseDaoImpl<ActorInfo> implements ActorInfoDao{
	public ActorInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}