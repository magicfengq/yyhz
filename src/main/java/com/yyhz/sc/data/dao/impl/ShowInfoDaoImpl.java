package com.yyhz.sc.data.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.ShowInfoDao;
import com.yyhz.sc.data.model.ShowInfo;
import com.yyhz.sc.data.model.ShowPraise;
/**
 * 
 * @author mew
 *
 */
@Component
public class ShowInfoDaoImpl extends BaseDaoImpl<ShowInfo> implements ShowInfoDao{
	public ShowInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}

	@Override
	public List<ShowInfo> selectTurnList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return dao.getSqlSessionTemplate().selectList("selectTurnList", param);
	}
}