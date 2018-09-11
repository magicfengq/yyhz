package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.AnnounceInfoDao;
import com.yyhz.sc.data.model.AnnounceInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class AnnounceInfoDaoImpl extends BaseDaoImpl<AnnounceInfo> implements AnnounceInfoDao{
	public AnnounceInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}