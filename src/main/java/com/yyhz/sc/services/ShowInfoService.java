package com.yyhz.sc.services;

import com.yyhz.sc.data.model.ShowInfo;

import java.util.List;
import java.util.Map;

import com.yyhz.sc.base.service.BaseService;

public interface ShowInfoService extends BaseService<ShowInfo>{

	List<ShowInfo> selectTurnList(Map<String, Object> param);

}
