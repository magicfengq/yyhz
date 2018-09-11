package com.yyhz.sc.data.model;

import java.io.Serializable;
import com.yyhz.sc.base.entity.BaseEntity;

/**
 * site_list_picture实体表()
 * @author crazyt
 * @date 2017-03-22 23:06:03
 * @project 
 */
 @SuppressWarnings("serial")
public class SiteListPicture extends BaseEntity implements Serializable {
	private java.lang.String id; // 主键id
	private java.lang.String siteId; // 场地id
	private java.lang.String imgUuid; // 图片uuid
	
	private String imgUrl;
	private SystemPictureInfo systemPictureInfo;
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	/**
     * 获取主键id属性
     *
     * @return id
     */
	public java.lang.String getId() {
		return id;
	}
	
	/**
	 * 设置主键id属性
	 *
	 * @param id
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	/**
     * 获取场地id属性
     *
     * @return siteId
     */
	public java.lang.String getSiteId() {
		return siteId;
	}
	
	/**
	 * 设置场地id属性
	 *
	 * @param siteId
	 */
	public void setSiteId(java.lang.String siteId) {
		this.siteId = siteId;
	}
	
	/**
     * 获取图片uuid属性
     *
     * @return imgUuid
     */
	public java.lang.String getImgUuid() {
		return imgUuid;
	}
	
	/**
	 * 设置图片uuid属性
	 *
	 * @param imgUuid
	 */
	public void setImgUuid(java.lang.String imgUuid) {
		this.imgUuid = imgUuid;
	}
	
	public SystemPictureInfo getSystemPictureInfo() {
		return systemPictureInfo;
	}

	public void setSystemPictureInfo(SystemPictureInfo systemPictureInfo) {
		this.systemPictureInfo = systemPictureInfo;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SiteListPicture");
        sb.append("{id=").append(id);
        sb.append(", siteId=").append(siteId);
        sb.append(", imgUuid=").append(imgUuid);
		sb.append('}');
        return sb.toString();
    }
    
}