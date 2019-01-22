package com.yyhz.sc.data.model;

import java.io.Serializable;

import com.yyhz.sc.base.entity.BaseEntity;

/**
 * black_list_info实体表()
 * @author fengq
 * @date 2019-01-18 17:29:19
 * @project app首页后台管理
 */
 @SuppressWarnings("serial")
public class BlackListInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // id
	private java.lang.String actorId; // actorId
	private java.lang.String blackActorId; // blackActorId
	private java.lang.String createTime; // createTime
	/**
     * 获取id属性
     *
     * @return id
     */
	public java.lang.String getId() {
		return id;
	}
	
	/**
	 * 设置id属性
	 *
	 * @param id
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}
	/**
     * 获取actorId属性
     *
     * @return actorId
     */
	public java.lang.String getActorId() {
		return actorId;
	}
	
	/**
	 * 设置actorId属性
	 *
	 * @param actorId
	 */
	public void setActorId(java.lang.String actorId) {
		this.actorId = actorId;
	}
	/**
     * 获取blackActorId属性
     *
     * @return blackActorId
     */
	public java.lang.String getBlackActorId() {
		return blackActorId;
	}
	
	/**
	 * 设置blackActorId属性
	 *
	 * @param blackActorId
	 */
	public void setBlackActorId(java.lang.String blackActorId) {
		this.blackActorId = blackActorId;
	}
	/**
     * 获取createTime属性
     *
     * @return createTime
     */
	public java.lang.String getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置createTime属性
	 *
	 * @param createTime
	 */
	public void setCreateTime(java.lang.String createTime) {
		this.createTime = createTime;
	}
}