package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.ShowInfoPicturesDao;
import com.yyhz.sc.data.model.ShowInfoPictures;
/**
 * 
 * @author mew
 *
 */
@Component
public class ShowInfoPicturesDaoImpl extends BaseDaoImpl<ShowInfoPictures> implements ShowInfoPicturesDao{
	public ShowInfoPicturesDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}