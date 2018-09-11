package com.yyhz.sc.data.model;

import java.io.Serializable;
import com.yyhz.sc.base.entity.BaseEntity;

/**
 * actor_collect实体表()
 * @author lipeng
 * @date 2017-03-18 15:19:54
 * @project 
 */
 @SuppressWarnings("serial")
public class ActorCollect extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String creater; // 创建人；即主动添加关注的用户
	private java.util.Date createTime; // 关注时间
	private java.lang.String actorId; // 被关注的用户
	
	private ActorInfo actorInfo;
	
	private String headImgUrl; // 被关注人头像url
	private String roleInfos;  // 被关注人角色信息
	private Integer authenticateLevel;// 被关注人认证等级
	private String name; // // 被关注人角色姓名
	private String keyword;
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getAuthenticateLevel() {
		return authenticateLevel;
	}

	public void setAuthenticateLevel(Integer authenticateLevel) {
		this.authenticateLevel = authenticateLevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getRoleInfos() {
		return roleInfos;
	}

	public void setRoleInfos(String roleInfos) {
		this.roleInfos = roleInfos;
	}

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
     * 获取创建人；即主动添加关注的用户属性
     *
     * @return creater
     */
	public java.lang.String getCreater() {
		return creater;
	}
	
	/**
	 * 设置创建人；即主动添加关注的用户属性
	 *
	 * @param creater
	 */
	public void setCreater(java.lang.String creater) {
		this.creater = creater;
	}
	
	/**
     * 获取关注时间属性
     *
     * @return createTime
     */
	public java.util.Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置关注时间属性
	 *
	 * @param createTime
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	/**
     * 获取被关注的用户属性
     *
     * @return actorId
     */
	public java.lang.String getActorId() {
		return actorId;
	}
	
	/**
	 * 设置被关注的用户属性
	 *
	 * @param actorId
	 */
	public void setActorId(java.lang.String actorId) {
		this.actorId = actorId;
	}

	public ActorInfo getActorInfo() {
		return actorInfo;
	}

	public void setActorInfo(ActorInfo actorInfo) {
		this.actorInfo = actorInfo;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ActorCollect");
        sb.append("{id=").append(id);
        sb.append(", creater=").append(creater);
        sb.append(", createTime=").append(createTime);
        sb.append(", actorId=").append(actorId);
		sb.append('}');
        return sb.toString();
    }
    
}