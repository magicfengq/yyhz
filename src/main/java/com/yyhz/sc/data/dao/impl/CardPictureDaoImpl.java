package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.CardPictureDao;
import com.yyhz.sc.data.model.CardPicture;
/**
 * 
 * @author mew
 *
 */
@Component
public class CardPictureDaoImpl extends BaseDaoImpl<CardPicture> implements CardPictureDao{
	public CardPictureDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}