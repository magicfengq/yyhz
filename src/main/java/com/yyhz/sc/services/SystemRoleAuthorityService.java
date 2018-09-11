package com.yyhz.sc.services;

import java.util.List;
import java.util.Map;

import com.yyhz.sc.base.service.BaseService;
import com.yyhz.sc.data.model.SystemRoleAuthority;
import com.yyhz.sc.data.model.SystemUserInfo;

public interface SystemRoleAuthorityService extends BaseService<SystemRoleAuthority> {
	
    int saveAuthority(SystemRoleAuthority info) throws Exception;

    List<Map<String, Object>> selectPermissUrl(SystemUserInfo info);
}
