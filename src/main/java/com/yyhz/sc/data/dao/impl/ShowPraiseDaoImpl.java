package com.yyhz.sc.data.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.ShowPraiseDao;
import com.yyhz.sc.data.model.ShowPraise;
/**
 * 
 * @author mew
 *
 */
@Component
public class ShowPraiseDaoImpl extends BaseDaoImpl<ShowPraise> implements ShowPraiseDao{
	public ShowPraiseDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
	
	@Override
	public List<ShowPraise> selectShowPraiseNum(Map<String, Object> commentParams) {
		return dao.getSqlSessionTemplate().selectList("selectShowPraiseNum", commentParams);
	}
}