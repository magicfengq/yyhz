package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.ShowInfoDao;
import com.yyhz.sc.data.model.ShowInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class ShowInfoDaoImpl extends BaseDaoImpl<ShowInfo> implements ShowInfoDao{
	public ShowInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}