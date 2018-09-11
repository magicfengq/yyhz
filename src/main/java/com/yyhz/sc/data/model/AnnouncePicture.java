package com.yyhz.sc.data.model;

import java.io.Serializable;
import com.yyhz.sc.base.entity.BaseEntity;

/**
 * announce_picture实体表()
 * @author crazyt
 * @date 2017-03-14 14:23:58
 * @project 
 */
 @SuppressWarnings("serial")
public class AnnouncePicture extends BaseEntity implements Serializable {


	private java.lang.String id; // 主键id
	private java.lang.String announceId; // 通告id
	private java.lang.String imgUuid; // 图片uuid
	
	private String imgUrl;            // 图片url
	
	private SystemPictureInfo systemPictureInfo;
	
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
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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
        sb.append("AnnouncePicture");
        sb.append("{id=").append(id);
        sb.append(", announceId=").append(announceId);
        sb.append(", imgUuid=").append(imgUuid);
		sb.append('}');
        return sb.toString();
    }
    
}