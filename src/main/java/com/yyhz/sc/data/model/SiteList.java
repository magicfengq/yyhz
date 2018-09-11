package com.yyhz.sc.data.model;

import java.io.Serializable;
import java.util.List;

import com.yyhz.sc.base.entity.BaseEntity;

/**
 * site_list实体表()
 * @author lipeng
 * @date 2017-03-08 21:49:09
 * @project 
 */
 @SuppressWarnings("serial")
public class SiteList extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String siteName; // 场地名称
	private java.lang.String phone; // 电话
	private java.lang.String passageway; // 通道
	private java.lang.String alert; // 提醒
	private java.lang.String address; // 地址
	private java.lang.String latitude; // 纬度
	private java.lang.String longitude; // 经度
	private java.util.Date createTime; // 
	private java.lang.String imgUuid; // 图片uuid
	private java.lang.String textArea; // 富文本
	
	private java.lang.String mutiImgUuid;//相册
	private java.lang.String refereeUuid;//推荐人id
	private java.lang.String refereeName;//推荐人名称
	private java.lang.String refereePhone;//推荐人联系方式
	private java.lang.String type;//类型 0后台创建1客户端创建
	private java.lang.String publicType; // 场地类型id
	private java.lang.String city; // 城市
	
	
	private String publicTypeName; // 场地类型名称
	private String keyword; // 检索用关键字
	private String siteImgUrls;// 场地图片，多个用英文","
	private String logoUrl; // 场地logo
	

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getSiteImgUrls() {
		return siteImgUrls;
	}

	public void setSiteImgUrls(String siteImgUrls) {
		this.siteImgUrls = siteImgUrls;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getPublicTypeName() {
		return publicTypeName;
	}

	public void setPublicTypeName(String publicTypeName) {
		this.publicTypeName = publicTypeName;
	}

	public java.lang.String getPublicType() {
		return publicType;
	}

	public void setPublicType(java.lang.String publicType) {
		this.publicType = publicType;
	}

	public java.lang.String getCity() {
		return city;
	}

	public void setCity(java.lang.String city) {
		this.city = city;
	}

	private SystemPictureInfo systemPictureInfo;
	private List<SystemPictureInfo> systemPictureInfoList;
	
	public java.lang.String getRefereePhone() {
		return refereePhone;
	}

	public void setRefereePhone(java.lang.String refereePhone) {
		this.refereePhone = refereePhone;
	}

	public java.lang.String getMutiImgUuid() {
		return mutiImgUuid;
	}

	public void setMutiImgUuid(java.lang.String mutiImgUuid) {
		this.mutiImgUuid = mutiImgUuid;
	}

	public java.lang.String getRefereeUuid() {
		return refereeUuid;
	}

	public void setRefereeUuid(java.lang.String refereeUuid) {
		this.refereeUuid = refereeUuid;
	}

	public java.lang.String getRefereeName() {
		return refereeName;
	}

	public void setRefereeName(java.lang.String refereeName) {
		this.refereeName = refereeName;
	}

	public List<SystemPictureInfo> getSystemPictureInfoList() {
		return systemPictureInfoList;
	}

	public void setSystemPictureInfoList(List<SystemPictureInfo> systemPictureInfoList) {
		this.systemPictureInfoList = systemPictureInfoList;
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
     * 获取场地名称属性
     *
     * @return name
     */
	public java.lang.String getSiteName() {
		return siteName;
	}
	
	/**
	 * 设置场地名称属性
	 *
	 * @param name
	 */
	public void setSiteName(java.lang.String siteName) {
		this.siteName =siteName;
	}
	
	/**
     * 获取电话属性
     *
     * @return phone
     */
	public java.lang.String getPhone() {
		return phone;
	}
	
	/**
	 * 设置电话属性
	 *
	 * @param phone
	 */
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}
	
	/**
     * 获取通道属性
     *
     * @return passageway
     */
	public java.lang.String getPassageway() {
		return passageway;
	}
	
	/**
	 * 设置通道属性
	 *
	 * @param passageway
	 */
	public void setPassageway(java.lang.String passageway) {
		this.passageway = passageway;
	}
	
	/**
     * 获取提醒属性
     *
     * @return alert
     */
	public java.lang.String getAlert() {
		return alert;
	}
	
	/**
	 * 设置提醒属性
	 *
	 * @param alert
	 */
	public void setAlert(java.lang.String alert) {
		this.alert = alert;
	}
	
	/**
     * 获取地址属性
     *
     * @return address
     */
	public java.lang.String getAddress() {
		return address;
	}
	
	/**
	 * 设置地址属性
	 *
	 * @param address
	 */
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	
	/**
     * 获取纬度属性
     *
     * @return latitude
     */
	public java.lang.String getLatitude() {
		return latitude;
	}
	
	/**
	 * 设置纬度属性
	 *
	 * @param latitude
	 */
	public void setLatitude(java.lang.String latitude) {
		this.latitude = latitude;
	}
	
	/**
     * 获取经度属性
     *
     * @return longitude
     */
	public java.lang.String getLongitude() {
		return longitude;
	}
	
	/**
	 * 设置经度属性
	 *
	 * @param longitude
	 */
	public void setLongitude(java.lang.String longitude) {
		this.longitude = longitude;
	}
	
	/**
     * 获取属性
     *
     * @return createTime
     */
	public java.util.Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置属性
	 *
	 * @param createTime
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
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
	
	/**
     * 获取富文本属性
     *
     * @return textArea
     */
	public java.lang.String getTextArea() {
		return textArea;
	}
	
	/**
	 * 设置富文本属性
	 *
	 * @param textArea
	 */
	public void setTextArea(java.lang.String textArea) {
		this.textArea = textArea;
	}
	

	public SystemPictureInfo getSystemPictureInfo() {
		return systemPictureInfo;
	}

	public void setSystemPictureInfo(SystemPictureInfo systemPictureInfo) {
		this.systemPictureInfo = systemPictureInfo;
	}
	

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SiteList");
        sb.append("{id=").append(id);
        sb.append(", siteName=").append(siteName);
        sb.append(", phone=").append(phone);
        sb.append(", passageway=").append(passageway);
        sb.append(", alert=").append(alert);
        sb.append(", address=").append(address);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", createTime=").append(createTime);
        sb.append(", imgUuid=").append(imgUuid);
        sb.append(", textArea=").append(textArea);
		sb.append('}');
        return sb.toString();
    }
    
}