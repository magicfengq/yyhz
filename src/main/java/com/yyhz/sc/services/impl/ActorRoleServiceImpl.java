package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.ActorRoleDao;
import com.yyhz.sc.data.model.ActorRole;
import com.yyhz.sc.services.ActorRoleService;

@Service
public class ActorRoleServiceImpl extends BaseServiceImpl<ActorRole> implements ActorRoleService{

	@Autowired
	private ActorRoleDao actorRoleDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(actorRoleDao);
	}
	
}