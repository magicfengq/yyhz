package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.CardAnnouncePublicTypeDao;
import com.yyhz.sc.data.model.CardAnnouncePublicType;
/**
 * 
 * @author mew
 *
 */
@Component
public class CardAnnouncePublicTypeDaoImpl extends BaseDaoImpl<CardAnnouncePublicType> implements CardAnnouncePublicTypeDao{
	public CardAnnouncePublicTypeDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}