package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.ActorCollectDao;
import com.yyhz.sc.data.model.ActorCollect;
/**
 * 
 * @author mew
 *
 */
@Component
public class ActorCollectDaoImpl extends BaseDaoImpl<ActorCollect> implements ActorCollectDao{
	public ActorCollectDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}