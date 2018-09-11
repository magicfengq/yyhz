package com.yyhz.sc.data.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.SystemPictureInfoDao;
import com.yyhz.sc.data.model.SystemPictureInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemPictureInfoDaoImpl extends BaseDaoImpl<SystemPictureInfo> implements SystemPictureInfoDao{
	public SystemPictureInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}

	@Override
	public List<SystemPictureInfo> selectByUuids(List<String> imageIdList) {
		return dao.getSqlSessionTemplate().selectList(sql_name_space + ".selectByUuids", imageIdList);
	}
}