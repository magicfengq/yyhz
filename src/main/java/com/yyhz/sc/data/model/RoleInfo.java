package com.yyhz.sc.data.model;

import java.io.Serializable;
import java.util.Date;

import com.yyhz.sc.base.entity.BaseEntity;

/**
 * role_info实体表()
 * @author lipeng
 * @date 2017-03-04 17:23:53
 * @project 
 */
 @SuppressWarnings("serial")
public class RoleInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String name; // 角色名称
	private java.lang.Integer status; // 状态：0正常；1已删除
	private Date createTime;
	private java.lang.Integer power;
	
	
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
     * 获取角色名称属性
     *
     * @return name
     */
	public java.lang.String getName() {
		return name;
	}
	
	/**
	 * 设置角色名称属性
	 *
	 * @param name
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	/**
     * 获取状态：0正常；1已删除属性
     *
     * @return status
     */
	public java.lang.Integer getStatus() {
		return status;
	}
	
	/**
	 * 设置状态：0正常；1已删除属性
	 *
	 * @param status
	 */
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("RoleInfo");
        sb.append("{id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", status=").append(status);
		sb.append('}');
        return sb.toString();
    }

	public java.lang.Integer getPower() {
		return power;
	}

	public void setPower(java.lang.Integer power) {
		this.power = power;
	}
    
}