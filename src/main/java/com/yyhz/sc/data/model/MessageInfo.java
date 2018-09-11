package com.yyhz.sc.data.model;

import java.io.Serializable;
import java.util.List;

import com.yyhz.sc.base.entity.BaseEntity;

/**
 * message_info实体表()
 * @author lipeng
 * @date 2017-04-10 13:48:12
 * @project 
 */
 @SuppressWarnings("serial")
public class MessageInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.Integer type; // 消息类型：0聊天消息；1秀消息；2通告消息；3系统消息
	private java.lang.String title; // 消息标题
	private java.lang.String creater; // 消息发布人；可能是系统用户，也可能是艺人
	private java.util.Date createTime; // 创建时间
	private java.lang.String content; // 消息内容
	private java.lang.String params; // 参数信息：如秀一秀id；通告id；
	private java.util.Date sendTime; // 
	private java.lang.String status; // 0-已保存(未发送),1-已发送,-1-已删除
	private java.lang.String sendTarget; // 
	
	private List<String> sendTargetEntity;
	private List<String> sendUsers;
	
	
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
     * 获取消息类型：0聊天消息；1秀消息；2通告消息；3系统消息属性
     *
     * @return type
     */
	public java.lang.Integer getType() {
		return type;
	}
	
	/**
	 * 设置消息类型：0聊天消息；1秀消息；2通告消息；3系统消息属性
	 *
	 * @param type
	 */
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	/**
     * 获取消息标题属性
     *
     * @return title
     */
	public java.lang.String getTitle() {
		return title;
	}
	
	/**
	 * 设置消息标题属性
	 *
	 * @param title
	 */
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	
	/**
     * 获取消息发布人；可能是系统用户，也可能是艺人属性
     *
     * @return creater
     */
	public java.lang.String getCreater() {
		return creater;
	}
	
	/**
	 * 设置消息发布人；可能是系统用户，也可能是艺人属性
	 *
	 * @param creater
	 */
	public void setCreater(java.lang.String creater) {
		this.creater = creater;
	}
	
	/**
     * 获取创建时间属性
     *
     * @return createTime
     */
	public java.util.Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置创建时间属性
	 *
	 * @param createTime
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	/**
     * 获取消息内容属性
     *
     * @return content
     */
	public java.lang.String getContent() {
		return content;
	}
	
	/**
	 * 设置消息内容属性
	 *
	 * @param content
	 */
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	/**
     * 获取参数信息：如秀一秀id；通告id；属性
     *
     * @return params
     */
	public java.lang.String getParams() {
		return params;
	}
	
	/**
	 * 设置参数信息：如秀一秀id；通告id；属性
	 *
	 * @param params
	 */
	public void setParams(java.lang.String params) {
		this.params = params;
	}
	
	/**
     * 获取属性
     *
     * @return sendTime
     */
	public java.util.Date getSendTime() {
		return sendTime;
	}
	
	/**
	 * 设置属性
	 *
	 * @param sendTime
	 */
	public void setSendTime(java.util.Date sendTime) {
		this.sendTime = sendTime;
	}
	
	/**
     * 获取0-已保存(未发送),1-已发送,-1-已删除属性
     *
     * @return status
     */
	public java.lang.String getStatus() {
		return status;
	}
	
	/**
	 * 设置0-已保存(未发送),1-已发送,-1-已删除属性
	 *
	 * @param status
	 */
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	
	/**
     * 获取属性
     *
     * @return sendTarget
     */
	public java.lang.String getSendTarget() {
		return sendTarget;
	}
	
	/**
	 * 设置属性
	 *
	 * @param sendTarget
	 */
	public void setSendTarget(java.lang.String sendTarget) {
		this.sendTarget = sendTarget;
	}

	public List<String> getSendTargetEntity() {
		return sendTargetEntity;
	}

	public void setSendTargetEntity(List<String> sendTargetEntity) {
		this.sendTargetEntity = sendTargetEntity;
	}

	public List<String> getSendUsers() {
		return sendUsers;
	}

	public void setSendUsers(List<String> sendUsers) {
		this.sendUsers = sendUsers;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MessageInfo");
        sb.append("{id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", title=").append(title);
        sb.append(", creater=").append(creater);
        sb.append(", createTime=").append(createTime);
        sb.append(", content=").append(content);
        sb.append(", params=").append(params);
        sb.append(", sendTime=").append(sendTime);
        sb.append(", status=").append(status);
        sb.append(", sendTarget=").append(sendTarget);
		sb.append('}');
        return sb.toString();
    }
    
}