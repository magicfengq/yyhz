package com.yyhz.sc.data.dao.impl;
import org.springframework.stereotype.Component;

import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.ContentReportInfoDao;
import com.yyhz.sc.data.model.ContentReportInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class ContentReportInfoDaoImpl extends BaseDaoImpl<ContentReportInfo> implements ContentReportInfoDao{
	public ContentReportInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}