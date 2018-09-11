package com.yyhz.sc.data.dao;

import java.util.List;
import java.util.Map;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.SystemMenuInfo;
/**
 * 数据访问接口
 *
 */
public interface SystemMenuInfoDao extends BaseDao<SystemMenuInfo>{
	
	public String sqlNameSpace=SystemMenuInfoDao.class.getName();
	
	public List<SystemMenuInfo> selectAllByPid(String pid);

    public List<SystemMenuInfo> selectAllByPid(Integer pid);

    public int selectMaxOrderListByPid(SystemMenuInfo info);

    public int selectMaxOrderListByPid(Map<String, Object> info);

    public int updateOrderListDown(SystemMenuInfo info);

    public int updateOrderListUp(SystemMenuInfo info);

    public List<SystemMenuInfo> selectAllByRole(SystemMenuInfo info);

    public List<SystemMenuInfo> selectAllByRoleLogin(SystemMenuInfo info);
}