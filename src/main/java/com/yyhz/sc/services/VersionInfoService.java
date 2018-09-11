package com.yyhz.sc.services;

import com.yyhz.sc.data.model.VersionInfo;
import com.yyhz.utils.stream.util.StreamVO;
import com.yyhz.sc.base.service.BaseService;

public interface VersionInfoService extends BaseService<VersionInfo>{

	public int insertWithFile(VersionInfo info, StreamVO streamVO);

	public int updateWithFile(VersionInfo info, StreamVO streamVO);

}
