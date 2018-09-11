package com.yyhz.sc.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.SystemMenuActDao;
import com.yyhz.sc.data.dao.SystemMenuActUrlDao;
import com.yyhz.sc.data.dao.SystemMenuInfoDao;
import com.yyhz.sc.data.model.SystemMenuAct;
import com.yyhz.sc.data.model.SystemMenuActUrl;
import com.yyhz.sc.data.model.SystemMenuInfo;
import com.yyhz.sc.services.SystemMenuInfoService;
import com.yyhz.utils.UUIDUtil;

@Service
public class SystemMenuInfoServiceImpl extends BaseServiceImpl<SystemMenuInfo> implements SystemMenuInfoService{

    @Autowired
    private SystemMenuInfoDao dao;

    @Autowired
    private SystemMenuActDao systemMenuActDao;

    @Autowired
    private SystemMenuActUrlDao systemMenuActUrlDao;

    @Override
    public List<SystemMenuInfo> selectAll() {
        return dao.selectAll();
    }

    @Override
    public List<SystemMenuInfo> selectAll(SystemMenuInfo info) {
        return dao.selectAll(info);
    }

    @Override
    public List<SystemMenuInfo> selectAll(Map<String, Object> info) {
        return dao.selectAll(info);
    }

    @Override
    public List<SystemMenuInfo> selectAll(SystemMenuInfo info, int page, int pageSize) {
        return dao.selectAll(info, page, pageSize);
    }

    @Override
    public List<SystemMenuInfo> selectAll(Map<String, Object> info, int page, int pageSize) {
        return dao.selectAll(info, page, pageSize);
    }

    @Override
    public PageInfo<SystemMenuInfo> selectAll(SystemMenuInfo info, PageInfo<SystemMenuInfo> pageInfo) {
        return dao.selectAll(info, pageInfo);
    }

    @Override
    public int selectCount(SystemMenuInfo info) {
        return dao.selectCount(info);
    }

    @Override
    public int selectCount(Map<String, Object> info) {
        return dao.selectCount(info);
    }

    @Override
    public SystemMenuInfo selectById(String id) {
        return dao.selectById(id);
    }

    @Override
    public SystemMenuInfo selectById(Integer id) {
        return dao.selectById(id);
    }

    @Transactional
    @Override
    public int insert(SystemMenuInfo info) {
        SystemMenuAct systemMenuAct = new SystemMenuAct();
        systemMenuAct.setId(UUIDUtil.getUUID());
        systemMenuAct.setActCode("menu");
        systemMenuAct.setActName("菜单");
        systemMenuAct.setMenuId(info.getId());
        systemMenuAct.setPosition("0");
        systemMenuActDao.insert(systemMenuAct);
        return dao.insert(info);
    }

    @Override
    public int insert(Map<String, Object> info) {
        return dao.insert(info);
    }

    @Override
    public int update(SystemMenuInfo info) {
        return dao.update(info);
    }

    @Override
    public int update(Map<String, Object> info) {
        return dao.update(info);
    }

    @Transactional
    @Override
    public int delete(SystemMenuInfo info) {
        int result = 0;
        try {
            SystemMenuInfo SystemMenuInfo = selectById(info.getId());
            List<String> ids = new ArrayList<String>();
            getIds(ids, SystemMenuInfo);
            SystemMenuInfo condition = new SystemMenuInfo();
            condition.setIds(ids);

            // 删除资源
            SystemMenuActUrl systemMenuActUrl = new SystemMenuActUrl();
            systemMenuActUrl.setMenuIds(ids);
            systemMenuActUrlDao.delete(systemMenuActUrl);
            // 删除动作
            SystemMenuAct systemMenuAct = new SystemMenuAct();
            systemMenuAct.setMenuIds(ids);
            systemMenuActDao.delete(systemMenuAct);
            // 删除菜单
            result = dao.delete(condition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(Map<String, Object> info) {
        return dao.delete(info);
    }

    @Override
    public List<SystemMenuInfo> selectAllByPid(String pid) {
        return dao.selectAllByPid(pid);
    }

    @Override
    public List<SystemMenuInfo> selectAllByPid(Integer pid) {
        return dao.selectAllByPid(pid);
    }

    @Override
    public SystemMenuInfo selectEntity(SystemMenuInfo info) {
        return dao.selectEntity(info);
    }

    @Override
    public SystemMenuInfo selectEntity(Map<String, Object> info) {
        return dao.selectEntity(info);
    }

    @Override
    public int systemMenuDrag(String id, String pid, String moveType) {
        int result = 0;
        System.out.println("id:" + id);
        System.out.println("pid:" + pid);
        System.out.println("moveType:" + moveType);
        SystemMenuInfo menu = new SystemMenuInfo();
        menu.setId(id);
        if (moveType.equals("inner")) {
            if (pid.equals("root")) {
                menu.setPid("0");
            } else {
                menu.setPid(pid);
            }
            SystemMenuInfo own = dao.selectById(id);
            List<SystemMenuInfo> menus = dao.selectAllByPid(own.getPid());
            List<String> ids = getListIds(menus);
            own.setIds(ids);
            dao.updateOrderListUp(own);

            int max = dao.selectMaxOrderListByPid(menu);
            menu.setOrderList(max + 1);
            result = dao.update(menu);

        }
        // target下方
        if (moveType.equals("next")) {
            SystemMenuInfo own = null;
            SystemMenuInfo target = null;
            List<String> ids = null;
            List<SystemMenuInfo> menus = null;
            int ownOrderList = 0;
            target = dao.selectById(pid);
            own = dao.selectById(id);
            ownOrderList = own.getOrderList();
            if (target.getPid().equals(own.getPid())) {
                // 同一级
                menus = dao.selectAllByPid(own.getPid());
                ids = getListIds(menus);
                // own在target下方
                if (own.getOrderList() > target.getOrderList()) {
                    // 先上移在下移
                    // 上移
                    own.setIds(ids);
                    dao.updateOrderListUp(own);
                    // 下移
                    target.setIds(ids);
                    dao.updateOrderListDown(target);
                    // 更新own
                    own.setOrderList(target.getOrderList() + 1);
                    dao.update(own);
                } else {
                    // 先下移在上移
                    // 下移
                    target.setIds(ids);
                    dao.updateOrderListDown(target);
                    // 更新own
                    own.setOrderList(target.getOrderList() + 1);
                    dao.update(own);
                    // 上移
                    own.setOrderList(ownOrderList);
                    own.setIds(ids);
                    dao.updateOrderListUp(own);
                }
            } else {
                // 不同级
                // 下移
                menus = dao.selectAllByPid(target.getPid());
                ids = getListIds(menus);
                target.setIds(ids);
                String ownPid = own.getPid();
                dao.updateOrderListDown(target);
                // 更新own
                own.setOrderList(target.getOrderList() + 1);
                own.setPid(target.getPid());
                dao.update(own);
                // 上移
                own.setPid(ownPid);
                menus = dao.selectAllByPid(own.getPid());
                ids = getListIds(menus);
                own.setIds(ids);
                own.setOrderList(ownOrderList);
                dao.updateOrderListUp(own);
            }

        }
        if (moveType.equals("prev")) {
            SystemMenuInfo own = null;
            SystemMenuInfo target = null;
            List<String> ids = null;
            List<SystemMenuInfo> menus = null;
            target = dao.selectById(pid);
            own = dao.selectById(id);
            int ownOrderList = 0;
            int targetOrderList = 0;
            ownOrderList = own.getOrderList();
            targetOrderList = target.getOrderList();
            if (target.getPid().equals(own.getPid())) {
                // 同一级
                menus = dao.selectAllByPid(own.getPid());
                ids = getListIds(menus);
                // own在target下方
                if (own.getOrderList() > target.getOrderList()) {
                    // 先上移在下移
                    // 上移
                    own.setIds(ids);
                    dao.updateOrderListUp(own);
                    // 下移
                    target.setIds(ids);
                    target.setOrderList(target.getOrderList() - 1);
                    dao.updateOrderListDown(target);
                    // 更新own
                    own.setOrderList(targetOrderList);
                    dao.update(own);
                } else {
                    // 先下移在上移
                    // 下移
                    target.setIds(ids);
                    target.setOrderList(target.getOrderList() - 1);
                    dao.updateOrderListDown(target);
                    // 更新own
                    own.setOrderList(targetOrderList);
                    dao.update(own);
                    // 上移
                    own.setIds(ids);
                    own.setOrderList(ownOrderList);
                    dao.updateOrderListUp(own);
                }
            } else {
                // 不同级
                // 下移
                String ownPid = own.getPid();
                menus = dao.selectAllByPid(target.getPid());
                ids = getListIds(menus);
                target.setIds(ids);
                target.setOrderList(target.getOrderList() - 1);
                dao.updateOrderListDown(target);
                // 更新own
                own.setOrderList(targetOrderList);
                own.setPid(target.getPid());
                dao.update(own);
                // 上移
                own.setPid(ownPid);
                menus = dao.selectAllByPid(own.getPid());
                ids = getListIds(menus);
                own.setIds(ids);
                own.setOrderList(ownOrderList);
                dao.updateOrderListUp(own);
            }
        }
        return result;
    }

    @Override
    public int selectMaxOrderListByPid(SystemMenuInfo info) {
        return dao.selectMaxOrderListByPid(info);
    }

    @Override
    public int selectMaxOrderListByPid(Map<String, Object> info) {
        return dao.selectMaxOrderListByPid(info);
    }

    private List<String> getIds(List<String> ids, SystemMenuInfo menu) {
        ids.add(menu.getId());
        if (menu.getChildren() != null && menu.getChildren().size() > 0) {
            for (SystemMenuInfo item : menu.getChildren()) {
                getIds(ids, item);
            }
        }

        return ids;
    }

    private List<String> getListIds(List<SystemMenuInfo> menus) {
        List<String> ids = new ArrayList<String>();
        for (SystemMenuInfo item : menus) {
            ids.add(item.getId());
        }
        return ids;
    }

    @Override
    public List<SystemMenuInfo> selectAllByRole(SystemMenuInfo info) {
        return dao.selectAllByRole(info);
    }

    @Override
    public List<SystemMenuInfo> selectAllByRoleLogin(SystemMenuInfo info) {
        return dao.selectAllByRoleLogin(info);
    }

}