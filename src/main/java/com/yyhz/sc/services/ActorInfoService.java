package com.yyhz.sc.services;

import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.service.BaseService;

public interface ActorInfoService extends BaseService<ActorInfo>{

	/**
	 * 分页查询用户列表
	 * @param info
	 * @param pageInfo
	 * @param string
	 * @param b 是否关联角色
	 * @return
	 */
	public PageInfo<ActorInfo> selectActorInfoForPage(ActorInfo info, PageInfo<ActorInfo> pageInfo, String string, boolean b);

}
