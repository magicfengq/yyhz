package com.yyhz.sc.data.model;

import java.io.Serializable;
import com.yyhz.sc.base.entity.BaseEntity;

/**
 * actor_role实体表()
 * @author lipeng
 * @date 2017-03-04 17:23:53
 * @project 
 */
 @SuppressWarnings("serial")
public class ActorRole extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String actorId; // 艺人id
	private java.lang.String roleId; // 角色id；-来源于role_info表，不是系统角色表
	
	private String roleName;
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
     * 获取艺人id属性
     *
     * @return actorId
     */
	public java.lang.String getActorId() {
		return actorId;
	}
	
	/**
	 * 设置艺人id属性
	 *
	 * @param actorId
	 */
	public void setActorId(java.lang.String actorId) {
		this.actorId = actorId;
	}
	
	/**
     * 获取角色id；-来源于role_info表，不是系统角色表属性
     *
     * @return roleId
     */
	public java.lang.String getRoleId() {
		return roleId;
	}
	
	/**
	 * 设置角色id；-来源于role_info表，不是系统角色表属性
	 *
	 * @param roleId
	 */
	public void setRoleId(java.lang.String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ActorRole");
        sb.append("{id=").append(id);
        sb.append(", actorId=").append(actorId);
        sb.append(", roleId=").append(roleId);
		sb.append('}');
        return sb.toString();
    }
    
}