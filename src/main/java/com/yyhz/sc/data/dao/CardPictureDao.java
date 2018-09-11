package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.CardPicture;
/**
 * 数据访问接口
 *
 */
public interface CardPictureDao extends BaseDao<CardPicture>{
	public String sqlNameSpace=CardPictureDao.class.getName();
}