package com.yyhz.sc.services;

import com.yyhz.sc.data.model.SystemPictureInfo;

import java.util.List;

import com.yyhz.sc.base.service.BaseService;

public interface SystemPictureInfoService extends BaseService<SystemPictureInfo>{

	public List<SystemPictureInfo> selectByUuids(List<String> imageUuidList);

}
