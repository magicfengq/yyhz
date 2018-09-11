package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.SystemCityRegionDao;
import com.yyhz.sc.data.model.SystemCityRegion;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemCityRegionDaoImpl extends BaseDaoImpl<SystemCityRegion> implements SystemCityRegionDao{
	public SystemCityRegionDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}