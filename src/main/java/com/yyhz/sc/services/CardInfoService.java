package com.yyhz.sc.services;

import com.yyhz.sc.data.model.CardInfo;
import com.yyhz.sc.base.service.BaseService;

public interface CardInfoService extends BaseService<CardInfo>{
	int addCard(CardInfo cardInfo, String[] imageIds, String[] publicTypeIds);
}
