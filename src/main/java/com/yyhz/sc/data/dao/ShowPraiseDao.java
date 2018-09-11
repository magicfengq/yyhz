package com.yyhz.sc.data.dao;

import java.util.List;
import java.util.Map;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.ShowPraise;
/**
 * 数据访问接口
 *
 */
public interface ShowPraiseDao extends BaseDao<ShowPraise>{
	public String sqlNameSpace=ShowPraiseDao.class.getName();

	public List<ShowPraise> selectShowPraiseNum(Map<String, Object> commentParams);
}