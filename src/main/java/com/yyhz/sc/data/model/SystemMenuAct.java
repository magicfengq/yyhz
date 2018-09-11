package com.yyhz.sc.data.model;

import java.io.Serializable;
import java.util.List;

import com.yyhz.sc.base.entity.BaseEntity;

/**
 * system_menu_act实体表()
 * @author lipeng
 * @date 2017-02-25 10:57:14
 * @project 
 */
 @SuppressWarnings("serial")
public class SystemMenuAct extends BaseEntity implements Serializable {
		
	    private java.lang.String id; //
	    private java.lang.String menuId; // 菜单编号
	    private java.lang.String actName; // 动作名称
	    private java.lang.String actCode; // 动作代码
	    private java.lang.String position; // 位置
	    private java.lang.Integer isCheck; // 是否选择
	    private java.lang.String orderList; // 排序
	    private List<SystemMenuActUrl> systemMenuActUrls;
	    private List<String> menuIds;

	    public java.lang.String getId() {
	        return id;
	    }

	    public void setId(java.lang.String id) {
	        this.id = id;
	    }

	    public java.lang.String getMenuId() {
	        return menuId;
	    }

	    public void setMenuId(java.lang.String menuId) {
	        this.menuId = menuId;
	    }

	    public java.lang.String getActName() {
	        return actName;
	    }

	    public void setActName(java.lang.String actName) {
	        this.actName = actName;
	    }

	    public java.lang.String getPosition() {
	        return position;
	    }

	    public void setPosition(java.lang.String position) {
	        this.position = position;
	    }

	    public java.lang.Integer getIsCheck() {
	        return isCheck;
	    }

	    public void setIsCheck(java.lang.Integer isCheck) {
	        this.isCheck = isCheck;
	    }

	    public java.lang.String getActCode() {
	        return actCode;
	    }

	    public void setActCode(java.lang.String actCode) {
	        this.actCode = actCode;
	    }

	    public List<SystemMenuActUrl> getSystemMenuActUrls() {
	        return systemMenuActUrls;
	    }

	    public void setSystemMenuActUrls(List<SystemMenuActUrl> systemMenuActUrls) {
	        this.systemMenuActUrls = systemMenuActUrls;
	    }

	    public List<String> getMenuIds() {
	        return menuIds;
	    }

	    public void setMenuIds(List<String> menuIds) {
	        this.menuIds = menuIds;
	    }

	    public java.lang.String getOrderList() {
	        return orderList;
	    }

	    public void setOrderList(java.lang.String orderList) {
	        this.orderList = orderList;
	    }

}