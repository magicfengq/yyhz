package com.yyhz.sc.data.dao.impl;

import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.MessageInfoDao;
import com.yyhz.sc.data.model.MessageInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class MessageInfoDaoImpl extends BaseDaoImpl<MessageInfo> implements MessageInfoDao{
	public MessageInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}