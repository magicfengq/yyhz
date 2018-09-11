package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.PublicTypeDao;
import com.yyhz.sc.data.model.PublicType;
/**
 * 
 * @author mew
 *
 */
@Component
public class PublicTypeDaoImpl extends BaseDaoImpl<PublicType> implements PublicTypeDao{
	public PublicTypeDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}