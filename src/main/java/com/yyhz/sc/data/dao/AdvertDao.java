package com.yyhz.sc.data.dao;

import java.util.List;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.Advert;
/**
 * 数据访问接口
 *
 */
public interface AdvertDao extends BaseDao<Advert>{
	
	public String sqlNameSpace=AdvertDao.class.getName();
	
	public List<Advert> selectEntityByIds(List<String> idList);
}