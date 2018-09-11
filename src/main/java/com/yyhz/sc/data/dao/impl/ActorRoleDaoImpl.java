package com.yyhz.sc.data.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.ActorRoleDao;
import com.yyhz.sc.data.model.ActorRole;
/**
 * 
 * @author mew
 *
 */
@Component
public class ActorRoleDaoImpl extends BaseDaoImpl<ActorRole> implements ActorRoleDao{
	public ActorRoleDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}

	@Override
	public List<ActorRole> selectActorRoleInfo(String roleName) {
		return dao.getSqlSessionTemplate().selectList("selectActorRoleInfo", roleName);
	}

	@Override
	public List<ActorRole> selectActorRoleInfoByActorIds(List<String> actorIdList) {
		return dao.getSqlSessionTemplate().selectList("selectActorRoleInfoByActorIds", actorIdList);
	}
}