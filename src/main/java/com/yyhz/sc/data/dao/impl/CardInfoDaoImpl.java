package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.CardInfoDao;
import com.yyhz.sc.data.model.CardInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class CardInfoDaoImpl extends BaseDaoImpl<CardInfo> implements CardInfoDao{
	public CardInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}