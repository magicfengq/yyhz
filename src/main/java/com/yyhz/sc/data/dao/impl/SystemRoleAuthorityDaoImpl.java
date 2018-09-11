package com.yyhz.sc.data.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.yyhz.sc.base.BaseDaoImpl;
import com.yyhz.sc.data.dao.SystemRoleAuthorityDao;
import com.yyhz.sc.data.model.SystemRoleAuthority;
import com.yyhz.sc.data.model.SystemUserInfo;

@Component
public class SystemRoleAuthorityDaoImpl extends BaseDaoImpl<SystemRoleAuthority> implements SystemRoleAuthorityDao {
    public SystemRoleAuthorityDaoImpl() {
        setSql_name_space(sqlNameSpace);
    }

    @Override
    public List<Map<String, Object>> selectPermissUrl(SystemUserInfo info) {
        return dao.getSqlSessionTemplate().selectList(sql_name_space + ".selectPermissUrl", info);
    }
}