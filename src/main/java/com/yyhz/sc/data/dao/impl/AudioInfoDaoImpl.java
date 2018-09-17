package com.yyhz.sc.data.dao.impl;
import org.springframework.stereotype.Component;

import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.AudioInfoDao;
import com.yyhz.sc.data.model.AudioInfo;

/**
 * 
 * @author mew
 *
 */
@Component
public class AudioInfoDaoImpl extends BaseDaoImpl<AudioInfo> implements AudioInfoDao{
	public AudioInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}