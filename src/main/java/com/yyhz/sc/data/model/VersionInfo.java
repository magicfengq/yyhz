package com.yyhz.sc.data.model;

import java.io.Serializable;
import com.yyhz.sc.base.entity.BaseEntity;

/**
 * version_info实体表()
 * @author lipeng
 * @date 2017-04-14 12:59:52
 * @project 
 */
 @SuppressWarnings("serial")
public class VersionInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String versionCode; // 版本号
	private java.lang.String description; // 描述
	private java.util.Date createTime; // 创建时间
	private java.lang.String type; // 类型(1-android,2-ios)
	private java.lang.String downloadUrl; // 下载地址
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
     * 获取版本号属性
     *
     * @return versionCode
     */
	public java.lang.String getVersionCode() {
		return versionCode;
	}
	
	/**
	 * 设置版本号属性
	 *
	 * @param versionCode
	 */
	public void setVersionCode(java.lang.String versionCode) {
		this.versionCode = versionCode;
	}
	
	/**
     * 获取描述属性
     *
     * @return description
     */
	public java.lang.String getDescription() {
		return description;
	}
	
	/**
	 * 设置描述属性
	 *
	 * @param description
	 */
	public void setDescription(java.lang.String description) {
		this.description = description;
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
     * 获取类型(1-android,2-ios)属性
     *
     * @return type
     */
	public java.lang.String getType() {
		return type;
	}
	
	/**
	 * 设置类型(1-android,2-ios)属性
	 *
	 * @param type
	 */
	public void setType(java.lang.String type) {
		this.type = type;
	}
	
	/**
     * 获取下载地址属性
     *
     * @return downloadUrl
     */
	public java.lang.String getDownloadUrl() {
		return downloadUrl;
	}
	
	/**
	 * 设置下载地址属性
	 *
	 * @param downloadUrl
	 */
	public void setDownloadUrl(java.lang.String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("VersionInfo");
        sb.append("{id=").append(id);
        sb.append(", versionCode=").append(versionCode);
        sb.append(", description=").append(description);
        sb.append(", createTime=").append(createTime);
        sb.append(", type=").append(type);
        sb.append(", downloadUrl=").append(downloadUrl);
		sb.append('}');
        return sb.toString();
    }
    
}