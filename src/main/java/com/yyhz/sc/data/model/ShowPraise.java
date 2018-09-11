package com.yyhz.sc.data.model;

import java.io.Serializable;
import java.util.List;

import com.yyhz.sc.base.entity.BaseEntity;

/**
 * show_praise实体表()
 * @author lipeng
 * @date 2017-03-27 16:41:22
 * @project 
 */
 @SuppressWarnings("serial")
public class ShowPraise extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String showId; // 秀一秀id
	private java.lang.String creater; // 点赞人
	private java.util.Date createTime; // 点赞时间
	
	private List<String> showIdList;
	private Long praiseNum;
	private ActorInfo actorInfo;
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
     * 获取点赞人属性
     *
     * @return creater
     */
	public java.lang.String getCreater() {
		return creater;
	}
	
	/**
	 * 设置点赞人属性
	 *
	 * @param creater
	 */
	public void setCreater(java.lang.String creater) {
		this.creater = creater;
	}
	
	/**
     * 获取点赞时间属性
     *
     * @return createTime
     */
	public java.util.Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置点赞时间属性
	 *
	 * @param createTime
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	public Long getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(Long praiseNum) {
		this.praiseNum = praiseNum;
	}

	public List<String> getShowIdList() {
		return showIdList;
	}

	public void setShowIdList(List<String> showIdList) {
		this.showIdList = showIdList;
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
        sb.append("ShowPraise");
        sb.append("{id=").append(id);
        sb.append(", showId=").append(showId);
        sb.append(", creater=").append(creater);
        sb.append(", createTime=").append(createTime);
		sb.append('}');
        return sb.toString();
    }
    
}