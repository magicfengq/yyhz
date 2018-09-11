package com.yyhz.sc.data.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.controller.app.AppAnnounceController;
import com.yyhz.sc.data.dao.ActorCommentDao;
import com.yyhz.sc.data.model.ActorComment;
/**
 * 
 * @author mew
 *
 */
@Component
public class ActorCommentDaoImpl extends BaseDaoImpl<ActorComment> implements ActorCommentDao{
	private final Logger logger = LoggerFactory.getLogger(ActorCommentDaoImpl.class);

	public ActorCommentDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}

	@Override
	public float selectAvgScore(ActorComment comment) {
		float avgScore = 0.0f;
        SqlSession session = null;
        try {
            session = dao.getSqlSessionTemplate().getSqlSessionFactory().openSession();
            Object obj = session.selectOne(sql_name_space + "." + "selectAvgScore", comment);
            logger.debug("---------------------------------------------------{}-------------------------",obj);
            if (obj != null) {
            	avgScore = (Float) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            session.close();
        }
        
        return avgScore;
	}
}