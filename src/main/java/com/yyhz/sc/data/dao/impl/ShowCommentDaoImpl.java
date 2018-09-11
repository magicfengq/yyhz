package com.yyhz.sc.data.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.ShowCommentDao;
import com.yyhz.sc.data.model.ShowComment;
/**
 * 
 * @author mew
 *
 */
@Component
public class ShowCommentDaoImpl extends BaseDaoImpl<ShowComment> implements ShowCommentDao{
	public ShowCommentDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}

	@Override
	public List<ShowComment> selectShowCommentNum(Map<String, Object> commentParams) {
		return dao.getSqlSessionTemplate().selectList("selectShowCommentNum", commentParams);
	}
}