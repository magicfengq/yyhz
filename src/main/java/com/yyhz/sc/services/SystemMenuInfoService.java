package com.yyhz.sc.services;

import java.util.List;
import java.util.Map;

import com.yyhz.sc.base.service.BaseService;
import com.yyhz.sc.data.model.SystemMenuInfo;

public interface SystemMenuInfoService extends BaseService<SystemMenuInfo>{
	
	public List<SystemMenuInfo> selectAllByPid(String pid);

    public List<SystemMenuInfo> selectAllByPid(Integer pid);

    public int systemMenuDrag(String id, String pid, String moveType);

    public int selectMaxOrderListByPid(SystemMenuInfo info);

    public int selectMaxOrderListByPid(Map<String, Object> info);

    public List<SystemMenuInfo> selectAllByRole(SystemMenuInfo info);

    public List<SystemMenuInfo> selectAllByRoleLogin(SystemMenuInfo info);

}
