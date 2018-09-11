package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.ActorCollectDao;
import com.yyhz.sc.data.model.ActorCollect;
import com.yyhz.sc.services.ActorCollectService;

@Service
public class ActorCollectServiceImpl extends BaseServiceImpl<ActorCollect> implements ActorCollectService{

	@Autowired
	private ActorCollectDao actorCollectDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(actorCollectDao);
	}
	
}