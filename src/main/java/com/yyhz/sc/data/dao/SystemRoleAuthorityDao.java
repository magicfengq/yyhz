package com.yyhz.sc.data.dao;

import java.util.List;
import java.util.Map;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.data.model.SystemRoleAuthority;
import com.yyhz.sc.data.model.SystemUserInfo;

/**
 * 数据访问接口
 */
public interface SystemRoleAuthorityDao extends BaseDao<SystemRoleAuthority> {

    public String sqlNameSpace = SystemRoleAuthorityDao.class.getName();

    public List<Map<String, Object>> selectPermissUrl(SystemUserInfo info);
}