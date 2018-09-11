package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.AnnouceEnrollDao;
import com.yyhz.sc.data.model.AnnouceEnroll;
/**
 * 
 * @author mew
 *
 */
@Component
public class AnnouceEnrollDaoImpl extends BaseDaoImpl<AnnouceEnroll> implements AnnouceEnrollDao{
	public AnnouceEnrollDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}