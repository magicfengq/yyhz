package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.SiteListDao;
import com.yyhz.sc.data.model.SiteList;
/**
 * 
 * @author mew
 *
 */
@Component
public class SiteListDaoImpl extends BaseDaoImpl<SiteList> implements SiteListDao{
	public SiteListDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}