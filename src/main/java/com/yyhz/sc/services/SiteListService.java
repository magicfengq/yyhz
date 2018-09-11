package com.yyhz.sc.services;

import com.yyhz.sc.data.model.SiteList;
import com.yyhz.utils.stream.util.StreamVO;
import com.yyhz.sc.base.service.BaseService;

public interface SiteListService extends BaseService<SiteList>{
	 
    public int insertWithImage(SiteList info, StreamVO streamVO);

	public int updateWithImage(SiteList info, StreamVO streamVO);
}
