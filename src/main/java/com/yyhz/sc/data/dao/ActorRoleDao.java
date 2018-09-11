package com.yyhz.sc.data.dao;

import java.util.List;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.ActorRole;
/**
 * 数据访问接口
 *
 */
public interface ActorRoleDao extends BaseDao<ActorRole>{
	public String sqlNameSpace=ActorRoleDao.class.getName();

	
	public List<ActorRole> selectActorRoleInfo(String roleName);
	
	public List<ActorRole> selectActorRoleInfoByActorIds(List<String> actorIdList);
	
	
}