package com.yyhz.sc.data.model;

import java.io.Serializable;
import com.yyhz.sc.base.entity.BaseEntity;

/**
 * card_announce_public_type实体表()
 * @author crazyt
 * @date 2017-03-12 19:45:32
 * @project 
 */
 @SuppressWarnings("serial")
public class CardAnnouncePublicType extends BaseEntity implements Serializable {
	private java.lang.String id; // 主键id
	private java.lang.String cardAnnounceId; // 卡片或通告id
	private java.lang.String publicTypeId; // 发布类型id
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
     * 获取卡片或通告id属性
     *
     * @return cardAnnounceId
     */
	public java.lang.String getCardAnnounceId() {
		return cardAnnounceId;
	}
	
	/**
	 * 设置卡片或通告id属性
	 *
	 * @param cardAnnounceId
	 */
	public void setCardAnnounceId(java.lang.String cardAnnounceId) {
		this.cardAnnounceId = cardAnnounceId;
	}
	
	/**
     * 获取发布类型id属性
     *
     * @return publicTypeId
     */
	public java.lang.String getPublicTypeId() {
		return publicTypeId;
	}
	
	/**
	 * 设置发布类型id属性
	 *
	 * @param publicTypeId
	 */
	public void setPublicTypeId(java.lang.String publicTypeId) {
		this.publicTypeId = publicTypeId;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CardAnnouncePublicType");
        sb.append("{id=").append(id);
        sb.append(", cardAnnounceId=").append(cardAnnounceId);
        sb.append(", publicTypeId=").append(publicTypeId);
		sb.append('}');
        return sb.toString();
    }
    
}