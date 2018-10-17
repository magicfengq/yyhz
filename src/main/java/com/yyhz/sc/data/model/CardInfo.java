package com.yyhz.sc.data.model;

import java.io.Serializable;

import com.yyhz.sc.base.entity.BaseEntity;

/**
 * card_info实体表()
 * @author lipeng
 * @date 2017-03-16 23:54:43
 * @project 
 */
 @SuppressWarnings("serial")
public class CardInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 主键
	private java.lang.Integer type; // 1艺人；2租借；3策划/创意；4婚礼/派对
	private java.lang.String cardName; // 艺人名称；租借主题;
	private java.lang.Integer sex; // 性别：1男 2女
	private java.lang.String cardRoleId; // 角色类型id；关联card_role表(没有使用)
	private java.lang.String detailRole; // 具体角色，直接填内容
	private java.lang.String publicType; // 发布类型/通告类型
	private java.util.Date birthDate; // 出生日期
	private java.lang.String city; // 所在城市
	private java.lang.String actCities; // 活动范围；租售范围；(多个地点用','分隔)
	private java.lang.Double price; // 艺人每场费用(单位：元)；租借造价；
	private java.lang.String height; // 身高
	private java.lang.String weight; // 体重
	private java.lang.String shoesSize; // 鞋码
	private java.lang.String size; // 三围；尺寸
	private java.lang.String institution; // 机构
	private java.lang.String details; // 装备说明;内容介绍
	private java.lang.String imgUuid; // 头像
	private java.lang.String mutiImgUuid; // 相册uuid
	private java.lang.String creater; // 创建人id
	private java.util.Date createTime; // 创建时间
	private java.lang.Integer status; // 状态 0正常；1已删除；
	private java.lang.String createrName;//创建人名称
	
	// 新增属性用于返回给前端
	private String createrHeadUrl; // 创建人头像
	private String birthDateStr; // 出生日期字符串
	private String cardRoleName; // 卡片角色名
	private String cardImgUrls;  // 卡片关联图片url，多个url用”,“分隔
	private Float avgScore; // 创建人的平均分数
	private String keyword;  // 检索用关键字
	private Integer authenticateLevel; // 认证级别
	private String publicTypeNames; // 发布类型名称，多个用英文","分隔
	
	private SystemPictureInfo systemPictureInfo;
	
	
	public String getCreaterHeadUrl() {
		return createrHeadUrl;
	}

	public void setCreaterHeadUrl(String createrHeadUrl) {
		this.createrHeadUrl = createrHeadUrl;
	}

	public Integer getAuthenticateLevel() {
		return authenticateLevel;
	}

	public void setAuthenticateLevel(Integer authenticateLevel) {
		this.authenticateLevel = authenticateLevel;
	}

	public Float getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(Float avgScore) {
		this.avgScore = avgScore;
	}

	public String getPublicTypeNames() {
		return publicTypeNames;
	}

	public void setPublicTypeNames(String publicTypeNames) {
		this.publicTypeNames = publicTypeNames;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public java.lang.String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(java.lang.String createrName) {
		this.createrName = createrName;
	}

	public SystemPictureInfo getSystemPictureInfo() {
		return systemPictureInfo;
	}
	
	public void setSystemPictureInfo(SystemPictureInfo systemPictureInfo) {
		this.systemPictureInfo = systemPictureInfo;
	}
	
	public String getCardRoleName() {
		return cardRoleName;
	}

	public void setCardRoleName(String cardRoleName) {
		this.cardRoleName = cardRoleName;
	}

	public String getCardImgUrls() {
		return cardImgUrls;
	}

	public void setCardImgUrls(String cardImgUrls) {
		this.cardImgUrls = cardImgUrls;
	}

	public String getBirthDateStr() {
		return birthDateStr;
	}

	public void setBirthDateStr(String birthDateStr) {
		this.birthDateStr = birthDateStr;
	}

	/**
     * 获取主键属性
     *
     * @return id
     */
	public java.lang.String getId() {
		return id;
	}
	
	/**
	 * 设置主键属性
	 *
	 * @param id
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	/**
     * 获取1艺人；2租借；3策划/创意；4婚礼/派对属性
     *
     * @return type
     */
	public java.lang.Integer getType() {
		return type;
	}
	
	/**
	 * 设置1艺人；2租借；3策划/创意；4婚礼/派对属性
	 *
	 * @param type
	 */
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	/**
     * 获取艺人名称；租借主题;属性
     *
     * @return name
     */
	public java.lang.String getCardName() {
		return cardName;
	}
	
	/**
	 * 设置艺人名称；租借主题;属性
	 *
	 * @param name
	 */
	public void setCardName(java.lang.String cardName) {
		this.cardName = cardName;
	}
	
	/**
     * 获取性别：1男 2女属性
     *
     * @return sex
     */
	public java.lang.Integer getSex() {
		return sex;
	}
	
	/**
	 * 设置性别：1男 2女属性
	 *
	 * @param sex
	 */
	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}
	
	/**
     * 获取角色类型id；关联card_role表(没有使用)属性
     *
     * @return cardRoleId
     */
	public java.lang.String getCardRoleId() {
		return cardRoleId;
	}
	
	/**
	 * 设置角色类型id；关联card_role表(没有使用)属性
	 *
	 * @param cardRoleId
	 */
	public void setCardRoleId(java.lang.String cardRoleId) {
		this.cardRoleId = cardRoleId;
	}
	
	/**
     * 获取具体角色，直接填内容属性
     *
     * @return detailRole
     */
	public java.lang.String getDetailRole() {
		return detailRole;
	}
	
	/**
	 * 设置具体角色，直接填内容属性
	 *
	 * @param detailRole
	 */
	public void setDetailRole(java.lang.String detailRole) {
		this.detailRole = detailRole;
	}
	
	/**
     * 获取发布类型/通告类型属性
     *
     * @return publicType
     */
	public java.lang.String getPublicType() {
		return publicType;
	}
	
	/**
	 * 设置发布类型/通告类型属性
	 *
	 * @param publicType
	 */
	public void setPublicType(java.lang.String publicType) {
		this.publicType = publicType;
	}
	
	/**
     * 获取出生日期属性
     *
     * @return birthDate
     */
	public java.util.Date getBirthDate() {
		return birthDate;
	}
	
	/**
	 * 设置出生日期属性
	 *
	 * @param birthDate
	 */
	public void setBirthDate(java.util.Date birthDate) {
		this.birthDate = birthDate;
	}
	
	/**
     * 获取所在城市属性
     *
     * @return city
     */
	public java.lang.String getCity() {
		return city;
	}
	
	/**
	 * 设置所在城市属性
	 *
	 * @param city
	 */
	public void setCity(java.lang.String city) {
		this.city = city;
	}
	
	/**
     * 获取活动范围；租售范围；(多个地点用','分隔)属性
     *
     * @return actCities
     */
	public java.lang.String getActCities() {
		return actCities;
	}
	
	/**
	 * 设置活动范围；租售范围；(多个地点用','分隔)属性
	 *
	 * @param actCities
	 */
	public void setActCities(java.lang.String actCities) {
		this.actCities = actCities;
	}
	
	/**
     * 获取艺人每场费用(单位：元)；租借造价；属性
     *
     * @return price
     */
	public java.lang.Double getPrice() {
		return price;
	}
	
	/**
	 * 设置艺人每场费用(单位：元)；租借造价；属性
	 *
	 * @param price
	 */
	public void setPrice(java.lang.Double price) {
		this.price = price;
	}
	
	/**
     * 获取身高属性
     *
     * @return height
     */
	public java.lang.String getHeight() {
		return height;
	}
	
	/**
	 * 设置身高属性
	 *
	 * @param height
	 */
	public void setHeight(java.lang.String height) {
		this.height = height;
	}
	
	/**
     * 获取体重属性
     *
     * @return weight
     */
	public java.lang.String getWeight() {
		return weight;
	}
	
	/**
	 * 设置体重属性
	 *
	 * @param weight
	 */
	public void setWeight(java.lang.String weight) {
		this.weight = weight;
	}
	
	/**
     * 获取鞋码属性
     *
     * @return shoesSize
     */
	public java.lang.String getShoesSize() {
		return shoesSize;
	}
	
	/**
	 * 设置鞋码属性
	 *
	 * @param shoesSize
	 */
	public void setShoesSize(java.lang.String shoesSize) {
		this.shoesSize = shoesSize;
	}
	
	/**
     * 获取三围；尺寸属性
     *
     * @return size
     */
	public java.lang.String getSize() {
		return size;
	}
	
	/**
	 * 设置三围；尺寸属性
	 *
	 * @param size
	 */
	public void setSize(java.lang.String size) {
		this.size = size;
	}
	
	/**
     * 获取机构属性
     *
     * @return institution
     */
	public java.lang.String getInstitution() {
		return institution;
	}
	
	/**
	 * 设置机构属性
	 *
	 * @param institution
	 */
	public void setInstitution(java.lang.String institution) {
		this.institution = institution;
	}
	
	/**
     * 获取装备说明;内容介绍属性
     *
     * @return details
     */
	public java.lang.String getDetails() {
		return details;
	}
	
	/**
	 * 设置装备说明;内容介绍属性
	 *
	 * @param details
	 */
	public void setDetails(java.lang.String details) {
		this.details = details;
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
     * 获取相册uuid属性
     *
     * @return mutiImgUuid
     */
	public java.lang.String getMutiImgUuid() {
		return mutiImgUuid;
	}
	
	/**
	 * 设置相册uuid属性
	 *
	 * @param mutiImgUuid
	 */
	public void setMutiImgUuid(java.lang.String mutiImgUuid) {
		this.mutiImgUuid = mutiImgUuid;
	}
	
	/**
     * 获取创建人id属性
     *
     * @return creater
     */
	public java.lang.String getCreater() {
		return creater;
	}
	
	/**
	 * 设置创建人id属性
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
     * 获取状态 0正常；1已删除；属性
     *
     * @return status
     */
	public java.lang.Integer getStatus() {
		return status;
	}
	
	/**
	 * 设置状态 0正常；1已删除；属性
	 *
	 * @param status
	 */
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CardInfo");
        sb.append("{id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", cardName=").append(cardName);
        sb.append(", sex=").append(sex);
        sb.append(", cardRoleId=").append(cardRoleId);
        sb.append(", detailRole=").append(detailRole);
        sb.append(", publicType=").append(publicType);
        sb.append(", birthDate=").append(birthDate);
        sb.append(", city=").append(city);
        sb.append(", actCities=").append(actCities);
        sb.append(", price=").append(price);
        sb.append(", height=").append(height);
        sb.append(", weight=").append(weight);
        sb.append(", shoesSize=").append(shoesSize);
        sb.append(", size=").append(size);
        sb.append(", institution=").append(institution);
        sb.append(", details=").append(details);
        sb.append(", imgUuid=").append(imgUuid);
        sb.append(", mutiImgUuid=").append(mutiImgUuid);
        sb.append(", creater=").append(creater);
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
		sb.append('}');
        return sb.toString();
    }
    
}