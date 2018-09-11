package com.yyhz.sc.data.model;

import java.io.Serializable;
import com.yyhz.sc.base.entity.BaseEntity;

/**
 * feedback实体表()
 * @author crazyt
 * @date 2017-03-27 16:30:37
 * @project 
 */
 @SuppressWarnings("serial")
public class Feedback extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String title;//标题
	private java.lang.String type; // 类型(0.意见 1.投诉)
	private java.lang.String content; // 
	private java.lang.String userId; // 用户id
	private java.util.Date createDate; // 创建日期
	private java.lang.String username;//账号
	
	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getUsername() {
		return username;
	}

	public void setUsername(java.lang.String username) {
		this.username = username;
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
     * 获取类型(0.意见 1.投诉)属性
     *
     * @return type
     */
	public java.lang.String getType() {
		return type;
	}
	
	/**
	 * 设置类型(0.意见 1.投诉)属性
	 *
	 * @param type
	 */
	public void setType(java.lang.String type) {
		this.type = type;
	}
	
	/**
     * 获取属性
     *
     * @return content
     */
	public java.lang.String getContent() {
		return content;
	}
	
	/**
	 * 设置属性
	 *
	 * @param content
	 */
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	/**
     * 获取用户id属性
     *
     * @return userId
     */
	public java.lang.String getUserId() {
		return userId;
	}
	
	/**
	 * 设置用户id属性
	 *
	 * @param userId
	 */
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	/**
     * 获取创建日期属性
     *
     * @return createDate
     */
	public java.util.Date getCreateDate() {
		return createDate;
	}
	
	/**
	 * 设置创建日期属性
	 *
	 * @param createDate
	 */
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Feedback");
        sb.append("{id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", content=").append(content);
        sb.append(", userId=").append(userId);
        sb.append(", createDate=").append(createDate);
		sb.append('}');
        return sb.toString();
    }
    
}