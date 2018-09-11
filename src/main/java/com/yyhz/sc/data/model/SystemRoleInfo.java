package com.yyhz.sc.data.model;

import java.io.Serializable;
import com.yyhz.sc.base.entity.BaseEntity;

/**
 * system_role_info实体表()
 * @author lipeng
 * @date 2017-02-25 11:05:57
 * @project 
 */
 @SuppressWarnings("serial")
public class SystemRoleInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String name; // 角色名称
	private java.lang.String description; // 备注
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
     * 获取备注属性
     *
     * @return description
     */
	public java.lang.String getDescription() {
		return description;
	}
	
	/**
	 * 设置备注属性
	 *
	 * @param description
	 */
	public void setDescription(java.lang.String description) {
		this.description = description;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SystemRoleInfo");
        sb.append("{id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
		sb.append('}');
        return sb.toString();
    }
    
}