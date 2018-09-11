package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.AnnouncePictureDao;
import com.yyhz.sc.data.model.AnnouncePicture;
/**
 * 
 * @author mew
 *
 */
@Component
public class AnnouncePictureDaoImpl extends BaseDaoImpl<AnnouncePicture> implements AnnouncePictureDao{
	public AnnouncePictureDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}