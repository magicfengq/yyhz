package com.yyhz.sc.services;

import com.yyhz.sc.data.model.ShowPraise;

import java.util.List;
import java.util.Map;

import com.yyhz.sc.base.service.BaseService;

public interface ShowPraiseService extends BaseService<ShowPraise>{

	public List<ShowPraise> selectShowPraiseNum(Map<String, Object> commentParams);

}
