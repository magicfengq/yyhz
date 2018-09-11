package com.yyhz.sc.data.model;

import java.io.Serializable;

import com.yyhz.sc.base.entity.BaseEntity;

/**
 * system_role_authority实体表()
 *
 * @author William
 * @date 2016-11-22 11:11:20
 * @project
 */
@SuppressWarnings("serial")
public class SystemRoleAuthority extends BaseEntity implements Serializable {
    private java.lang.String id; //
    private java.lang.String roleId; // 角色变换
    private java.lang.String menuId; // 菜单编号
    private java.lang.String actId; // 动作编号
    private java.lang.String ids; //

    /**
     * 获取属性
     *
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }

    /**
     * 设置属性
     *
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    /**
     * 获取属性
     *
     * @return roleId
     */
    public java.lang.String getRoleId() {
        return roleId;
    }

    /**
     * 设置属性
     *
     * @param roleId
     */
    public void setRoleId(java.lang.String roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取菜单编号属性
     *
     * @return menuId
     */
    public java.lang.String getMenuId() {
        return menuId;
    }

    /**
     * 设置菜单编号属性
     *
     * @param menuId
     */
    public void setMenuId(java.lang.String menuId) {
        this.menuId = menuId;
    }

    /**
     * 获取动作编号属性
     *
     * @return actId
     */
    public java.lang.String getActId() {
        return actId;
    }

    /**
     * 设置动作编号属性
     *
     * @param actId
     */
    public void setActId(java.lang.String actId) {
        this.actId = actId;
    }

    public java.lang.String getIds() {
        return ids;
    }

    public void setIds(java.lang.String ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SystemRoleAuthority");
        sb.append("{id=").append(id);
        sb.append(", roleId=").append(roleId);
        sb.append(", menuId=").append(menuId);
        sb.append(", actId=").append(actId);
        sb.append('}');
        return sb.toString();
    }

}