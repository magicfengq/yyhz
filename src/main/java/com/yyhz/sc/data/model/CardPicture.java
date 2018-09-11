package com.yyhz.sc.data.model;

import java.io.Serializable;
import com.yyhz.sc.base.entity.BaseEntity;

/**
 * card_picture实体表()
 * @author crazyt
 * @date 2017-03-15 15:25:12
 * @project 
 */
 @SuppressWarnings("serial")
public class CardPicture extends BaseEntity implements Serializable {
	private java.lang.String id; // 主键id
	private java.lang.String cardId; // 卡片id
	private java.lang.String imgUuid; // 图片uuid
	
	
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
     * 获取卡片id属性
     *
     * @return cardId
     */
	public java.lang.String getCardId() {
		return cardId;
	}
	
	/**
	 * 设置卡片id属性
	 *
	 * @param cardId
	 */
	public void setCardId(java.lang.String cardId) {
		this.cardId = cardId;
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
        sb.append("CardPicture");
        sb.append("{id=").append(id);
        sb.append(", cardId=").append(cardId);
        sb.append(", imgUuid=").append(imgUuid);
		sb.append('}');
        return sb.toString();
    }
    
}