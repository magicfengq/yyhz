package com.yyhz.sc.data.dao;

import java.util.List;
import java.util.Map;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.ShowComment;
/**
 * 数据访问接口
 *
 */
public interface ShowCommentDao extends BaseDao<ShowComment>{
	public String sqlNameSpace=ShowCommentDao.class.getName();

	public List<ShowComment> selectShowCommentNum(Map<String, Object> commentParams);
}