package com.yyhz.sc.data.dao;

import java.util.List;
import java.util.Map;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.ShowCommentPraise;
/**
 * 数据访问接口
 *
 */
public interface ShowCommentPraiseDao extends BaseDao<ShowCommentPraise>{
	public String sqlNameSpace=ShowCommentPraiseDao.class.getName();

	public List<ShowCommentPraise> selectShowCommentPraiseNum(Map<String, Object> commentParams);
}