package com.yyhz.sc.data.model;

import java.io.Serializable;
import java.util.List;

import com.yyhz.sc.base.entity.BaseEntity;

/**
 * show_info_pictures实体表()
 * @author lipeng
 * @date 2017-03-25 12:03:45
 * @project 
 */
 @SuppressWarnings("serial")
public class ShowInfoPictures extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String showId; // 
	private java.lang.String imgUuid; // 
	
	private List<String> showIdList;
	private List<SystemPictureInfo> picList;
	private SystemPictureInfo systemPictureInfo;
	
	
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
     * 获取属性
     *
     * @return showId
     */
	public java.lang.String getShowId() {
		return showId;
	}
	
	/**
	 * 设置属性
	 *
	 * @param showId
	 */
	public void setShowId(java.lang.String showId) {
		this.showId = showId;
	}
	
	/**
     * 获取属性
     *
     * @return imgUuid
     */
	public java.lang.String getImgUuid() {
		return imgUuid;
	}
	
	/**
	 * 设置属性
	 *
	 * @param imgUuid
	 */
	public void setImgUuid(java.lang.String imgUuid) {
		this.imgUuid = imgUuid;
	}

	public List<String> getShowIdList() {
		return showIdList;
	}

	public void setShowIdList(List<String> showIdList) {
		this.showIdList = showIdList;
	}

	public List<SystemPictureInfo> getPicList() {
		return picList;
	}

	public void setPicList(List<SystemPictureInfo> picList) {
		this.picList = picList;
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
        sb.append("ShowInfoPictures");
        sb.append("{id=").append(id);
        sb.append(", showId=").append(showId);
        sb.append(", imgUuid=").append(imgUuid);
		sb.append('}');
        return sb.toString();
    }
    
}