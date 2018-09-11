package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.FeedbackDao;
import com.yyhz.sc.data.model.Feedback;
/**
 * 
 * @author mew
 *
 */
@Component
public class FeedbackDaoImpl extends BaseDaoImpl<Feedback> implements FeedbackDao{
	public FeedbackDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}