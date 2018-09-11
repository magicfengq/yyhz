package com.yyhz.sc.data.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.AdvertDao;
import com.yyhz.sc.data.model.Advert;
/**
 * 
 * @author mew
 *
 */
@Component
public class AdvertDaoImpl extends BaseDaoImpl<Advert> implements AdvertDao{
	public AdvertDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}

	@Override
	public List<Advert> selectEntityByIds(List<String> idList) {
		return dao.getSqlSessionTemplate().selectList("selectByIds", idList);
	}
}