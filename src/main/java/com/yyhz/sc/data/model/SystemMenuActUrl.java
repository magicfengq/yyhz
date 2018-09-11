package com.yyhz.sc.data.model;

import java.io.Serializable;
import java.util.List;

import com.yyhz.sc.base.entity.BaseEntity;

/**
 * system_menu_act_url实体表()
 *
 * @author William
 * @date 2016-11-22 11:11:20
 * @project
 */
@SuppressWarnings("serial")
public class SystemMenuActUrl extends BaseEntity implements Serializable {
    private java.lang.String id; //
    private java.lang.String actId; //
    private java.lang.String url; //
    private java.lang.String name; //
    private List<String> menuIds;

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
     * @return actId
     */
    public java.lang.String getActId() {
        return actId;
    }

    /**
     * 设置属性
     *
     * @param actId
     */
    public void setActId(java.lang.String actId) {
        this.actId = actId;
    }

    /**
     * 获取属性
     *
     * @return url
     */
    public java.lang.String getUrl() {
        return url;
    }

    /**
     * 设置属性
     *
     * @param url
     */
    public void setUrl(java.lang.String url) {
        this.url = url;
    }

    /**
     * 获取属性
     *
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * 设置属性
     *
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SystemMenuActUrl");
        sb.append("{id=").append(id);
        sb.append(", actId=").append(actId);
        sb.append(", url=").append(url);
        sb.append(", name=").append(name);
        sb.append('}');
        return sb.toString();
    }

    public List<String> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<String> menuIds) {
        this.menuIds = menuIds;
    }

}