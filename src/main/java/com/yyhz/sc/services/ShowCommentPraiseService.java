package com.yyhz.sc.services;

import com.yyhz.sc.data.model.ShowCommentPraise;

import java.util.List;
import java.util.Map;

import com.yyhz.sc.base.service.BaseService;

public interface ShowCommentPraiseService extends BaseService<ShowCommentPraise>{

	public List<ShowCommentPraise> selectShowCommentPraiseNum(Map<String, Object> commentParams);

}
