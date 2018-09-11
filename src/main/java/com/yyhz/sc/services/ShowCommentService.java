package com.yyhz.sc.services;

import com.yyhz.sc.data.model.ShowComment;

import java.util.List;
import java.util.Map;

import com.yyhz.sc.base.service.BaseService;

public interface ShowCommentService extends BaseService<ShowComment>{

	public List<ShowComment> selectShowCommentNum(Map<String, Object> commentParams);

}
