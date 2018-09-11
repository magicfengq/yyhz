package com.yyhz.sc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.FeedbackDao;
import com.yyhz.sc.data.model.Feedback;
import com.yyhz.sc.services.FeedbackService;

@Service
public class FeedbackServiceImpl extends BaseServiceImpl<Feedback> implements FeedbackService{

	@Autowired
	private FeedbackDao feedbackDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(feedbackDao);
	}
	
}