package com.yyhz.sc.services;

import java.util.Map;

import com.yyhz.sc.base.service.BaseService;
import com.yyhz.sc.data.model.AnnounceInfo;

public interface AnnounceInfoService extends BaseService<AnnounceInfo>{
	int addAnnouncement(AnnounceInfo announceInfo, String[] publicTypeIds, String[] imageIds);
	int deleteAnnouncement(AnnounceInfo announceInfo);
	AnnounceInfo getAnnouncementDetails(Map<String, Object> condition);
}
