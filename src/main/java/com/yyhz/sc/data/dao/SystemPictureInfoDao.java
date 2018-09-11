package com.yyhz.sc.data.dao;

import java.util.List;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.SystemPictureInfo;
/**
 * 数据访问接口
 *
 */
public interface SystemPictureInfoDao extends BaseDao<SystemPictureInfo>{
	public String sqlNameSpace=SystemPictureInfoDao.class.getName();

	public List<SystemPictureInfo> selectByUuids(List<String> imageUuidList);
}