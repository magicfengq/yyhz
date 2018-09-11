package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.SiteListPictureDao;
import com.yyhz.sc.data.model.SiteListPicture;
/**
 * 
 * @author mew
 *
 */
@Component
public class SiteListPictureDaoImpl extends BaseDaoImpl<SiteListPicture> implements SiteListPictureDao{
	public SiteListPictureDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}