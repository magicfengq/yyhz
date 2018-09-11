package com.yyhz.sc.data.dao;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.ActorCollect;
/**
 * 数据访问接口
 *
 */
public interface ActorCollectDao extends BaseDao<ActorCollect>{
	public String sqlNameSpace=ActorCollectDao.class.getName();
}