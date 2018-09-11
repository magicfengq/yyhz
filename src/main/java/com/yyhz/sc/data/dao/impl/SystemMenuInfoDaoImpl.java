package com.yyhz.sc.data.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.SystemMenuInfoDao;
import com.yyhz.sc.data.model.SystemMenuInfo;

/**
 * 
 * @author mew
 *
 */
@Component
public class SystemMenuInfoDaoImpl extends BaseDaoImpl<SystemMenuInfo> implements SystemMenuInfoDao {

	public SystemMenuInfoDaoImpl() {
		setSql_name_space(sqlNameSpace);
	}

	@Override
	public List<SystemMenuInfo> selectAllByPid(String pid) {
		return dao.getSqlSessionTemplate().selectList(sql_name_space + ".selectAllByPid", pid);
	}

	@Override
	public List<SystemMenuInfo> selectAllByPid(Integer pid) {
		return dao.getSqlSessionTemplate().selectList(sql_name_space + ".selectAllByPid", pid);
	}

	@Override
	public int selectMaxOrderListByPid(SystemMenuInfo info) {
		int count = 0;
		SqlSession session = null;
		try {
			session = dao.getSqlSessionTemplate().getSqlSessionFactory().openSession();
			Object obj = session.selectOne(sql_name_space + "." + "selectMaxOrderListByPid", info);
			if (obj != null) {
				count = (Integer) obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			session.close();
		}
		return count;
	}

	@Override
	public int selectMaxOrderListByPid(Map<String, Object> info) {
		int count = 0;
		SqlSession session = null;
		try {
			session = dao.getSqlSessionTemplate().getSqlSessionFactory().openSession();
			count = (Integer) session.selectOne(sql_name_space + "." + "selectMaxOrderListByPid", info);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			session.close();
		}
		return count;
	}

	@Override
	public int updateOrderListDown(SystemMenuInfo info) {
		return dao.getSqlSessionTemplate().update(sql_name_space + ".updateOrderListDown", info);
	}

	@Override
	public int updateOrderListUp(SystemMenuInfo info) {
		return dao.getSqlSessionTemplate().update(sql_name_space + ".updateOrderListUp", info);
	}

	@Override
	public List<SystemMenuInfo> selectAllByRole(SystemMenuInfo info) {
		return dao.getSqlSessionTemplate().selectList(sql_name_space + ".selectAllByRole", info);
	}

	@Override
	public List<SystemMenuInfo> selectAllByRoleLogin(SystemMenuInfo info) {
		return dao.getSqlSessionTemplate().selectList(sql_name_space + ".selectAllByRoleLogin", info);
	}
}