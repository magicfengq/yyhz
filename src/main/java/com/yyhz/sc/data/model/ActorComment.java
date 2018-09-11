package com.yyhz.sc.data.model;

import java.io.Serializable;
import com.yyhz.sc.base.entity.BaseEntity;

/**
 * actor_comment实体表()
 * @author lipeng
 * @date 2017-03-19 15:32:34
 * @project 
 */
 @SuppressWarnings("serial")
public class ActorComment extends BaseEntity implements Serializable {
	private java.lang.String id; // 主键
	private java.lang.String actorId; // 被评论的用户
	private java.lang.Integer type; // 评论类型：0评论发通告的人；1评论接通告的人
	private java.lang.String creater; // 评论人
	private java.util.Date createTime; // 评论时间
	private java.lang.String announceId; // 通告id
	private java.lang.String content; // 评论文字
	private java.lang.Integer score; // 评分
	private java.lang.Integer status; // 评论状态：0正常；
	
	private ActorInfo actorInfo;
	
	private String announceTitle; // 通告标题
	private Integer announceType; // 通告类型
	private String announcePublicType; // 通告发布类型id
	private String announcePublicTypeName; // 通告发布类型名称
	private String createrName; // 发布者姓名
	private String createrHeadUrl; // 发布者头像
	
	
	public String getAnnouncePublicType() {
		return announcePublicType;
	}

	public void setAnnouncePublicType(String announcePublicType) {
		this.announcePublicType = announcePublicType;
	}

	public String getAnnouncePublicTypeName() {
		return announcePublicTypeName;
	}

	public void setAnnouncePublicTypeName(String announcePublicTypeName) {
		this.announcePublicTypeName = announcePublicTypeName;
	}

	public Integer getAnnounceType() {
		return announceType;
	}

	public void setAnnounceType(Integer announceType) {
		this.announceType = announceType;
	}

	public String getCreaterHeadUrl() {
		return createrHeadUrl;
	}

	public void setCreaterHeadUrl(String createrHeadUrl) {
		this.createrHeadUrl = createrHeadUrl;
	}

	public String getAnnounceTitle() {
		return announceTitle;
	}

	public void setAnnounceTitle(String announceTitle) {
		this.announceTitle = announceTitle;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	/**
     * 获取主键属性
     *
     * @return id
     */
	public java.lang.String getId() {
		return id;
	}
	
	/**
	 * 设置主键属性
	 *
	 * @param id
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	/**
     * 获取被评论的用户属性
     *
     * @return actorId
     */
	public java.lang.String getActorId() {
		return actorId;
	}
	
	/**
	 * 设置被评论的用户属性
	 *
	 * @param actorId
	 */
	public void setActorId(java.lang.String actorId) {
		this.actorId = actorId;
	}
	
	/**
     * 获取评论类型：0评论发通告的人；1评论接通告的人属性
     *
     * @return type
     */
	public java.lang.Integer getType() {
		return type;
	}
	
	/**
	 * 设置评论类型：0评论发通告的人；1评论接通告的人属性
	 *
	 * @param type
	 */
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	/**
     * 获取评论人属性
     *
     * @return creater
     */
	public java.lang.String getCreater() {
		return creater;
	}
	
	/**
	 * 设置评论人属性
	 *
	 * @param creater
	 */
	public void setCreater(java.lang.String creater) {
		this.creater = creater;
	}
	
	/**
     * 获取评论时间属性
     *
     * @return createTime
     */
	public java.util.Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置评论时间属性
	 *
	 * @param createTime
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	/**
     * 获取通告id属性
     *
     * @return announceId
     */
	public java.lang.String getAnnounceId() {
		return announceId;
	}
	
	/**
	 * 设置通告id属性
	 *
	 * @param announceId
	 */
	public void setAnnounceId(java.lang.String announceId) {
		this.announceId = announceId;
	}
	
	/**
     * 获取评论文字属性
     *
     * @return content
     */
	public java.lang.String getContent() {
		return content;
	}
	
	/**
	 * 设置评论文字属性
	 *
	 * @param content
	 */
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	/**
     * 获取评分属性
     *
     * @return score
     */
	public java.lang.Integer getScore() {
		return score;
	}
	
	/**
	 * 设置评分属性
	 *
	 * @param score
	 */
	public void setScore(java.lang.Integer score) {
		this.score = score;
	}
	
	/**
     * 获取评论状态：0正常；属性
     *
     * @return status
     */
	public java.lang.Integer getStatus() {
		return status;
	}
	
	/**
	 * 设置评论状态：0正常；属性
	 *
	 * @param status
	 */
	public void setStatus(java.lang.Integer status) {
		this.status = status;
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
        sb.append("ActorComment");
        sb.append("{id=").append(id);
        sb.append(", actorId=").append(actorId);
        sb.append(", type=").append(type);
        sb.append(", creater=").append(creater);
        sb.append(", createTime=").append(createTime);
        sb.append(", announceId=").append(announceId);
        sb.append(", content=").append(content);
        sb.append(", score=").append(score);
        sb.append(", status=").append(status);
		sb.append('}');
        return sb.toString();
    }
    
}