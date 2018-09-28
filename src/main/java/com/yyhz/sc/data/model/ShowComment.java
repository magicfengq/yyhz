package com.yyhz.sc.data.model;

import java.io.Serializable;
import java.util.List;

import com.yyhz.sc.base.entity.BaseEntity;

/**
 * show_comment实体表()
 * @author lipeng
 * @date 2017-03-27 16:41:22
 * @project 
 */
 @SuppressWarnings("serial")
public class ShowComment extends BaseEntity implements Serializable {
	private java.lang.String id; // 秀一秀的id
	private java.lang.String showId; // 秀一秀id
	private java.lang.String creater; // 评论发布人
	private java.util.Date createTime; // 评论时间
	private java.lang.String toUserId; // 被评论的用户id；仅用于回复，普通回复此字段为null
	private java.lang.String content; // 评论内容
	private java.lang.Integer status; // 状态 0正常；1已删除；注意删除的时候要更新show_info表的评论数-1
	private java.lang.String operater; // 操作人；用户后台删除评论
	private java.lang.Integer praiseNum;//点赞数
	
	private List<String> showIdList;
	private Long commentNum;
	private ActorInfo actorInfo;
	
	/**
     * 获取秀一秀的id属性
     *
     * @return id
     */
	public java.lang.String getId() {
		return id;
	}
	
	/**
	 * 设置秀一秀的id属性
	 *
	 * @param id
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	/**
     * 获取秀一秀id属性
     *
     * @return showId
     */
	public java.lang.String getShowId() {
		return showId;
	}
	
	/**
	 * 设置秀一秀id属性
	 *
	 * @param showId
	 */
	public void setShowId(java.lang.String showId) {
		this.showId = showId;
	}
	
	/**
     * 获取评论发布人属性
     *
     * @return creater
     */
	public java.lang.String getCreater() {
		return creater;
	}
	
	/**
	 * 设置评论发布人属性
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
     * 获取被评论的用户id；仅用于回复，普通回复此字段为null属性
     *
     * @return toUserId
     */
	public java.lang.String getToUserId() {
		return toUserId;
	}
	
	/**
	 * 设置被评论的用户id；仅用于回复，普通回复此字段为null属性
	 *
	 * @param toUserId
	 */
	public void setToUserId(java.lang.String toUserId) {
		this.toUserId = toUserId;
	}
	
	/**
     * 获取评论内容属性
     *
     * @return content
     */
	public java.lang.String getContent() {
		return content;
	}
	
	/**
	 * 设置评论内容属性
	 *
	 * @param content
	 */
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	/**
     * 获取状态 0正常；1已删除；注意删除的时候要更新show_info表的评论数-1属性
     *
     * @return status
     */
	public java.lang.Integer getStatus() {
		return status;
	}
	
	/**
	 * 设置状态 0正常；1已删除；注意删除的时候要更新show_info表的评论数-1属性
	 *
	 * @param status
	 */
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	/**
     * 获取操作人；用户后台删除评论属性
     *
     * @return operater
     */
	public java.lang.String getOperater() {
		return operater;
	}
	
	/**
	 * 设置操作人；用户后台删除评论属性
	 *
	 * @param operater
	 */
	public void setOperater(java.lang.String operater) {
		this.operater = operater;
	}
	
	public List<String> getShowIdList() {
		return showIdList;
	}

	public void setShowIdList(List<String> showIdList) {
		this.showIdList = showIdList;
	}

	public Long getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Long commentNum) {
		this.commentNum = commentNum;
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
        sb.append("ShowComment");
        sb.append("{id=").append(id);
        sb.append(", showId=").append(showId);
        sb.append(", creater=").append(creater);
        sb.append(", createTime=").append(createTime);
        sb.append(", toUserId=").append(toUserId);
        sb.append(", content=").append(content);
        sb.append(", status=").append(status);
        sb.append(", operater=").append(operater);
		sb.append('}');
        return sb.toString();
    }

	public java.lang.Integer getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(java.lang.Integer praiseNum) {
		this.praiseNum = praiseNum;
	}
    
}