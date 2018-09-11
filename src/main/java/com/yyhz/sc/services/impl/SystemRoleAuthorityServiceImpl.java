package com.yyhz.sc.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.SystemMenuActDao;
import com.yyhz.sc.data.dao.SystemRoleAuthorityDao;
import com.yyhz.sc.data.model.SystemMenuAct;
import com.yyhz.sc.data.model.SystemRoleAuthority;
import com.yyhz.sc.data.model.SystemUserInfo;
import com.yyhz.sc.services.SystemRoleAuthorityService;
import com.yyhz.utils.UUIDUtil;

@Service
public class SystemRoleAuthorityServiceImpl extends BaseServiceImpl<SystemRoleAuthority>
        implements SystemRoleAuthorityService {

    @Autowired
    private SystemRoleAuthorityDao dao;

    @Autowired
    private SystemMenuActDao systemMenuActDao;

    @Autowired
    public void setBaseDao() {
        super.setBaseDao(dao);
    }

    @Transactional
    @Override
    public int saveAuthority(SystemRoleAuthority info) throws Exception {
        // TODO Auto-generated method stub
        int result = 0;
        SystemRoleAuthority deleteInfo = new SystemRoleAuthority();
        SystemRoleAuthority saveInfo = null;
        deleteInfo.setRoleId(info.getRoleId());
        delete(deleteInfo);
        SystemMenuAct systemMenuAct = new SystemMenuAct();
        for (String id : info.getIds().split(",")) {
            saveInfo = new SystemRoleAuthority();
            saveInfo.setRoleId(info.getRoleId());
            saveInfo.setId(UUIDUtil.getUUID());
            if (id.indexOf("_") == -1) {
                saveInfo.setMenuId(id);
                result += insert(saveInfo);

                systemMenuAct.setMenuId(id);
                systemMenuAct.setActCode("menu");
                SystemMenuAct menuAct = systemMenuActDao.selectEntity(systemMenuAct);
                if (menuAct != null) {
                    saveInfo.setId(UUIDUtil.getUUID());
                    saveInfo.setActId(menuAct.getId());
                    result += insert(saveInfo);
                }

            } else {
                saveInfo.setMenuId(id.substring(0, id.indexOf("_")));
                saveInfo.setActId(id.substring(id.indexOf("_") + 1, id.length()));
                result += insert(saveInfo);
            }
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectPermissUrl(SystemUserInfo info) {
        return dao.selectPermissUrl(info);
    }

}