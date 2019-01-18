package com.yyhz.sc.data.model;

import java.io.Serializable;
import java.util.List;

import com.yyhz.sc.base.entity.BaseEntity;

/**
 * announce_info实体表()
 * @author crazyt
 * @date 2017-03-09 15:55:22
 * @project 
 */
 @SuppressWarnings("serial")
public class AnnounceInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.Integer type; // 1艺人；2租借；3策划/创意；4婚礼/派对
	private java.lang.String publicType; // 通告类型/发布类型
	private java.lang.String title; // 通告标题
	private java.lang.String name; // 艺人类型；需要装备；策划项目；定制内容；
	private java.util.Date showTime; // 活动时间；使用时间；项目时间；定制时间；
	private java.util.Date entranceTime; // 艺人的进场时间
	private java.lang.String city; // 活动城市；使用城市；所在城市
	private java.lang.String address; // 活动详址
	private java.lang.Integer sex; // 性别要求 1男；2女；
	private java.lang.Double price; // 酬金；预算；
	private java.lang.String detail; // 通告详情；装备详单；项目详情；定制要求
	private java.lang.Integer readCount; // 浏览量
	private java.lang.Integer status; // 状态 0正常；1已删除；
	private java.lang.Integer enrollStatus; // 报名状态 0正常；1已关闭；
	private java.lang.String note; // 注意事项
	private java.lang.String creater; // 创建人
	private java.util.Date createTime; // 创建时间
	private java.lang.Integer enrollNum; // 报名人数
	private java.lang.String clothesColor; // 衣服颜色
	private java.lang.String actorId;
	private String showTimeStr; // 活动时间字符串
	private String entranceTimeStr; // 艺人的进场时间字符串
	private String keyword; // 搜索时候使用的关键字
	private Float avgScore; // 创建人的平均分数
	private String headImgUrl; // 创建人头像url
	private String createrName; // 创建人姓名
	private Integer authenticateLevel; // 创建人认证等级
	private String publicTypeNames; // 发布类型名称，多个用英文","分隔
	
	private String enrollActorId; // 报名通告的用户id，用于根据报名者id，查找所有报名的通告
	private Integer enrollActorCheckState; // 报名通告的用户，审核状态，查询我的报名通告需要。

	
	public Integer getEnrollActorCheckState() {
		return enrollActorCheckState;
	}

	public void setEnrollActorCheckState(Integer enrollActorCheckState) {
		this.enrollActorCheckState = enrollActorCheckState;
	}

	public String getEnrollActorId() {
		return enrollActorId;
	}

	public void setEnrollActorId(String enrollActorId) {
		this.enrollActorId = enrollActorId;
	}

	public String getPublicTypeNames() {
		return publicTypeNames;
	}

	public void setPublicTypeNames(String publicTypeNames) {
		this.publicTypeNames = publicTypeNames;
	}

	public Float getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(Float avgScore) {
		this.avgScore = avgScore;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getShowTimeStr() {
		return showTimeStr;
	}

	public void setShowTimeStr(String showTimeStr) {
		this.showTimeStr = showTimeStr;
	}

	public String getEntranceTimeStr() {
		return entranceTimeStr;
	}

	public void setEntranceTimeStr(String entranceTimeStr) {
		this.entranceTimeStr = entranceTimeStr;
	}

	
	private List<AnnouceEnroll> enrollInfos;

	
	public List<AnnouceEnroll> getEnrollInfos() {
		return enrollInfos;
	}

	public void setEnrollInfos(List<AnnouceEnroll> enrollInfos) {
		this.enrollInfos = enrollInfos;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}


	public Integer getAuthenticateLevel() {
		return authenticateLevel;
	}

	public void setAuthenticateLevel(Integer authenticateLevel) {
		this.authenticateLevel = authenticateLevel;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
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
     * 获取通告类型/发布类型属性
     *
     * @return publicType
     */
	public java.lang.String getPublicType() {
		return publicType;
	}
	
	/**
	 * 设置通告类型/发布类型属性
	 *
	 * @param publicType
	 */
	public void setPublicType(java.lang.String publicType) {
		this.publicType = publicType;
	}
	
	/**
     * 获取通告标题属性
     *
     * @return title
     */
	public java.lang.String getTitle() {
		return title;
	}
	
	/**
	 * 设置通告标题属性
	 *
	 * @param title
	 */
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	
	/**
     * 获取艺人类型；需要装备；策划项目；定制内容；属性
     *
     * @return name
     */
	public java.lang.String getName() {
		return name;
	}
	
	/**
	 * 设置艺人类型；需要装备；策划项目；定制内容；属性
	 *
	 * @param name
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	/**
     * 获取活动时间；使用时间；项目时间；定制时间；属性
     *
     * @return showTime
     */
	public java.util.Date getShowTime() {
		return showTime;
	}
	
	/**
	 * 设置活动时间；使用时间；项目时间；定制时间；属性
	 *
	 * @param showTime
	 */
	public void setShowTime(java.util.Date showTime) {
		this.showTime = showTime;
	}
	
	/**
     * 获取艺人的进场时间属性
     *
     * @return entranceTime
     */
	public java.util.Date getEntranceTime() {
		return entranceTime;
	}
	
	/**
	 * 设置艺人的进场时间属性
	 *
	 * @param entranceTime
	 */
	public void setEntranceTime(java.util.Date entranceTime) {
		this.entranceTime = entranceTime;
	}
	
	/**
     * 获取活动城市；使用城市；所在城市属性
     *
     * @return city
     */
	public java.lang.String getCity() {
		return city;
	}
	
	/**
	 * 设置活动城市；使用城市；所在城市属性
	 *
	 * @param city
	 */
	public void setCity(java.lang.String city) {
		this.city = city;
	}
	
	/**
     * 获取活动详址属性
     *
     * @return address
     */
	public java.lang.String getAddress() {
		return address;
	}
	
	/**
	 * 设置活动详址属性
	 *
	 * @param address
	 */
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	
	/**
     * 获取性别要求 1男；2女；属性
     *
     * @return sex
     */
	public java.lang.Integer getSex() {
		return sex;
	}
	
	/**
	 * 设置性别要求 1男；2女；属性
	 *
	 * @param sex
	 */
	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}
	
	/**
     * 获取酬金；预算；属性
     *
     * @return price
     */
	public java.lang.Double getPrice() {
		return price;
	}
	
	/**
	 * 设置酬金；预算；属性
	 *
	 * @param price
	 */
	public void setPrice(java.lang.Double price) {
		this.price = price;
	}
	
	/**
     * 获取通告详情；装备详单；项目详情；定制要求属性
     *
     * @return detail
     */
	public java.lang.String getDetail() {
		return detail;
	}
	
	/**
	 * 设置通告详情；装备详单；项目详情；定制要求属性
	 *
	 * @param detail
	 */
	public void setDetail(java.lang.String detail) {
		this.detail = detail;
	}
	
	/**
     * 获取浏览量属性
     *
     * @return readCount
     */
	public java.lang.Integer getReadCount() {
		return readCount;
	}
	
	/**
	 * 设置浏览量属性
	 *
	 * @param readCount
	 */
	public void setReadCount(java.lang.Integer readCount) {
		this.readCount = readCount;
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
	
	/**
     * 获取报名状态 0正常；1已关闭；属性
     *
     * @return enrollStatus
     */
	public java.lang.Integer getEnrollStatus() {
		return enrollStatus;
	}
	
	/**
	 * 设置报名状态 0正常；1已关闭；属性
	 *
	 * @param enrollStatus
	 */
	public void setEnrollStatus(java.lang.Integer enrollStatus) {
		this.enrollStatus = enrollStatus;
	}
	
	/**
     * 获取创建人属性
     *
     * @return creater
     */
	public java.lang.String getCreater() {
		return creater;
	}
	
	/**
	 * 设置创建人属性
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
     * 获取报名人数属性
     *
     * @return enrollNum
     */
	public java.lang.Integer getEnrollNum() {
		return enrollNum;
	}
	
	/**
	 * 设置报名人数属性
	 *
	 * @param enrollNum
	 */
	public void setEnrollNum(java.lang.Integer enrollNum) {
		this.enrollNum = enrollNum;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("AnnounceInfo");
        sb.append("{id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", publicType=").append(publicType);
        sb.append(", title=").append(title);
        sb.append(", name=").append(name);
        sb.append(", showTime=").append(showTime);
        sb.append(", entranceTime=").append(entranceTime);
        sb.append(", city=").append(city);
        sb.append(", address=").append(address);
        sb.append(", sex=").append(sex);
        sb.append(", price=").append(price);
        sb.append(", detail=").append(detail);
        sb.append(", readCount=").append(readCount);
        sb.append(", status=").append(status);
        sb.append(", enrollStatus=").append(enrollStatus);
        sb.append(", creater=").append(creater);
        sb.append(", createTime=").append(createTime);
        sb.append(", enrollNum=").append(enrollNum);
		sb.append('}');
        return sb.toString();
    }

	public java.lang.String getClothesColor() {
		return clothesColor;
	}

	public void setClothesColor(java.lang.String clothesColor) {
		this.clothesColor = clothesColor;
	}

	public java.lang.String getActorId() {
		return actorId;
	}

	public void setActorId(java.lang.String actorId) {
		this.actorId = actorId;
	}
    
}