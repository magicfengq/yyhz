package com.yyhz.sc.data.dao;

import java.util.List;
import java.util.Map;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.ShowInfo;
/**
 * 数据访问接口
 *
 */
public interface ShowInfoDao extends BaseDao<ShowInfo>{
	public String sqlNameSpace=ShowInfoDao.class.getName();

	List<ShowInfo> selectTurnList(Map<String, Object> param);
}