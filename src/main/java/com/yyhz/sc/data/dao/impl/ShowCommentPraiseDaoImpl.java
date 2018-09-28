package com.yyhz.sc.data.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.ShowCommentPraiseDao;
import com.yyhz.sc.data.model.ShowCommentPraise;
/**
 * 
 * @author mew
 *
 */
@Component
public class ShowCommentPraiseDaoImpl extends BaseDaoImpl<ShowCommentPraise> implements ShowCommentPraiseDao{
	public ShowCommentPraiseDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
	
	@Override
	public List<ShowCommentPraise> selectShowCommentPraiseNum(Map<String, Object> commentParams) {
		return dao.getSqlSessionTemplate().selectList("selectShowCommentPraiseNum", commentParams);
	}
}