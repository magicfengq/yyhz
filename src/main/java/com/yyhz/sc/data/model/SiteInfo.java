package com.yyhz.sc.data.model;

import java.io.Serializable;
import com.yyhz.sc.base.entity.BaseEntity;

/**
 * site_info实体表()
 * @author lipeng
 * @date 2017-03-08 21:49:09
 * @project 
 */
 @SuppressWarnings("serial")
public class SiteInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String imgUuid; // 头像
	private java.lang.String creater; // 评论发布人
	private java.util.Date createTime; // 评论时间
	private java.lang.String content; // 评论内容
	private java.lang.String siteId; // 要评论的场地id
	private java.lang.Integer status; // 评论状态：0正常；1已删除；
	private java.lang.String operater; // 操作人；后台执行删除操作的人
	private java.util.Date opt; // 操作时间；后台执行删除的时间
	
	private String createrMobile;
	private String createrName; // 创建者姓名
	
	private SystemPictureInfo systemPictureInfo;
	
	public SystemPictureInfo getSystemPictureInfo() {
		return systemPictureInfo;
	}
	public void setSystemPictureInfo(SystemPictureInfo systemPictureInfo) {
		this.systemPictureInfo = systemPictureInfo;
	}
	
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
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
     * 获取头像属性
     *
     * @return imgUuid
     */
	public java.lang.String getImgUuid() {
		return imgUuid;
	}
	
	/**
	 * 设置头像属性
	 *
	 * @param imgUuid
	 */
	public void setImgUuid(java.lang.String imgUuid) {
		this.imgUuid = imgUuid;
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
     * 获取要评论的场地id属性
     *
     * @return siteId
     */
	public java.lang.String getSiteId() {
		return siteId;
	}
	
	/**
	 * 设置要评论的场地id属性
	 *
	 * @param siteId
	 */
	public void setSiteId(java.lang.String siteId) {
		this.siteId = siteId;
	}
	
	/**
     * 获取评论状态：0正常；1已删除；属性
     *
     * @return status
     */
	public java.lang.Integer getStatus() {
		return status;
	}
	
	/**
	 * 设置评论状态：0正常；1已删除；属性
	 *
	 * @param status
	 */
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	/**
     * 获取操作人；后台执行删除操作的人属性
     *
     * @return operater
     */
	public java.lang.String getOperater() {
		return operater;
	}
	
	/**
	 * 设置操作人；后台执行删除操作的人属性
	 *
	 * @param operater
	 */
	public void setOperater(java.lang.String operater) {
		this.operater = operater;
	}
	
	/**
     * 获取操作时间；后台执行删除的时间属性
     *
     * @return opt
     */
	public java.util.Date getOpt() {
		return opt;
	}
	
	/**
	 * 设置操作时间；后台执行删除的时间属性
	 *
	 * @param opt
	 */
	public void setOpt(java.util.Date opt) {
		this.opt = opt;
	}

	public String getCreaterMobile() {
		return createrMobile;
	}
	
	public void setCreaterMobile(String createrMobile) {
		this.createrMobile = createrMobile;
	}
	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SiteInfo");
        sb.append("{id=").append(id);
        sb.append(", imgUuid=").append(imgUuid);
        sb.append(", creater=").append(creater);
        sb.append(", createTime=").append(createTime);
        sb.append(", content=").append(content);
        sb.append(", siteId=").append(siteId);
        sb.append(", status=").append(status);
        sb.append(", operater=").append(operater);
        sb.append(", opt=").append(opt);
		sb.append('}');
        return sb.toString();
    }
    
}