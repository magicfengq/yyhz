package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.SiteInfoDao;
import com.yyhz.sc.data.model.SiteInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class SiteInfoDaoImpl extends BaseDaoImpl<SiteInfo> implements SiteInfoDao{
	public SiteInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}