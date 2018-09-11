package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.RecommendsiteDao;
import com.yyhz.sc.data.model.Recommendsite;
/**
 * 
 * @author mew
 *
 */
@Component
public class RecommendsiteDaoImpl extends BaseDaoImpl<Recommendsite> implements RecommendsiteDao{
	public RecommendsiteDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}