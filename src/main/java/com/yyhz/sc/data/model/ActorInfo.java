package com.yyhz.sc.data.model;

import java.io.Serializable;
import java.util.List;

import com.yyhz.sc.base.entity.BaseEntity;

/**
 * actor_info实体表()
 * @author crazyt
 * @date 2017-03-01 14:48:26
 * @project 
 */
 @SuppressWarnings("serial")
public class ActorInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 主键id；环信id
	private java.lang.String name; // 艺名；公司简称
	private java.lang.String realName; // 真实姓名
	private java.lang.String mobile; // 手机号
	private java.lang.String password; // 密码
	private java.lang.String mkey; // 第三方登录key值
	private java.lang.String imgUuid; // 头像
	private java.lang.String sex; // 性别 1男；2女
	private java.util.Date birthDay; // 生日
	private java.lang.String city; // 所在地
	private java.lang.String actCities; // 活动范围(最多5个)
	private java.lang.String height; // 身高
	private java.lang.String weight; // 体重
	private java.lang.String shoesSize; // 鞋码
	private java.lang.String size; // 三围
	private java.lang.String easemobStatusCode; // 环信注册返回结果
	private java.util.Date lastLoginTime; // 最后一次登录时间
	private java.util.Date createTime; // 创建时间
	private java.lang.Integer status; // 状态 0正常；1已删除
	private java.lang.Integer pushStatus; // 推送状态  0正常；1已关闭
	private java.lang.String logintoken; // 登录token；登录唯一标识
	private java.lang.Integer registerType; // 注册类型  0普通注册；1QQ；2微信；3微博
	private java.lang.Integer authenticateLevel; // 认证级别：0未认证；1实名认证；2资历认证
	private java.lang.Integer level; // 用户级别：0普通用户；实名认证；2资历认证
	private java.lang.String idcard; // 身份证
	private java.lang.Integer totalScore; // 评论总评分  
	private java.lang.Integer commentNum; // 评论人数
	private java.lang.Double lon; // 当前经度
	private java.lang.Double lat; // 当前纬度
	private java.lang.String introduction; // 自我介绍
	
	private SystemPictureInfo systemPictureInfo;
	private String roleName;
	private String headImgUrl; // 头像url
	
	private String birthDayStr;// 生日字符串表示，用于输入
	private Float avgScore; // 平均分

	private List<RoleInfo> roleInfos; // 角色信息列表
	private List<String> actorIdList;
	private String authenticateLevelName;

	private String areaCode;
	public java.lang.String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(java.lang.String introduction) {
		this.introduction = introduction;
	}

	public String getBirthDayStr() {
		return birthDayStr;
	}

	public void setBirthDayStr(String birthDayStr) {
		this.birthDayStr = birthDayStr;
	}

	public List<RoleInfo> getRoleInfos() {
		return roleInfos;
	}

	public void setRoleInfos(List<RoleInfo> roleInfos) {
		this.roleInfos = roleInfos;
	}

	
	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	/**
     * 获取主键id；环信id属性
     *
     * @return id
     */
	public java.lang.String getId() {
		return id;
	}
	
	/**
	 * 设置主键id；环信id属性
	 *
	 * @param id
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	/**
     * 获取艺名；公司简称属性
     *
     * @return name
     */
	public java.lang.String getName() {
		return name;
	}
	
	/**
	 * 设置艺名；公司简称属性
	 *
	 * @param name
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	/**
     * 获取真实姓名属性
     *
     * @return realName
     */
	public java.lang.String getRealName() {
		return realName;
	}
	
	/**
	 * 设置真实姓名属性
	 *
	 * @param realName
	 */
	public void setRealName(java.lang.String realName) {
		this.realName = realName;
	}
	
	/**
     * 获取手机号属性
     *
     * @return mobile
     */
	public java.lang.String getMobile() {
		return mobile;
	}
	
	/**
	 * 设置手机号属性
	 *
	 * @param mobile
	 */
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	
	/**
     * 获取密码属性
     *
     * @return password
     */
	public java.lang.String getPassword() {
		return password;
	}
	
	/**
	 * 设置密码属性
	 *
	 * @param password
	 */
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	
	/**
     * 获取第三方登录key值属性
     *
     * @return mkey
     */
	public java.lang.String getMkey() {
		return mkey;
	}
	
	/**
	 * 设置第三方登录key值属性
	 *
	 * @param mkey
	 */
	public void setMkey(java.lang.String mkey) {
		this.mkey = mkey;
	}
	
	public java.lang.String getImgUuid() {
		return imgUuid;
	}

	public void setImgUuid(java.lang.String imgUuid) {
		this.imgUuid = imgUuid;
	}

	/**
     * 获取性别 1男；2女属性
     *
     * @return sex
     */
	public java.lang.String getSex() {
		return sex;
	}
	
	/**
	 * 设置性别 1男；2女属性
	 *
	 * @param sex
	 */
	public void setSex(java.lang.String sex) {
		this.sex = sex;
	}
	
	/**
     * 获取生日属性
     *
     * @return birthDay
     */
	public java.util.Date getBirthDay() {
		return birthDay;
	}
	
	/**
	 * 设置生日属性
	 *
	 * @param birthDay
	 */
	public void setBirthDay(java.util.Date birthDay) {
		this.birthDay = birthDay;
	}
	
	/**
     * 获取所在地属性
     *
     * @return city
     */
	public java.lang.String getCity() {
		return city;
	}
	
	/**
	 * 设置所在地属性
	 *
	 * @param city
	 */
	public void setCity(java.lang.String city) {
		this.city = city;
	}
	
	/**
     * 获取活动范围(最多5个)属性
     *
     * @return actCities
     */
	public java.lang.String getActCities() {
		return actCities;
	}
	
	/**
	 * 设置活动范围(最多5个)属性
	 *
	 * @param actCities
	 */
	public void setActCities(java.lang.String actCities) {
		this.actCities = actCities;
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
     * 获取三围属性
     *
     * @return size
     */
	public java.lang.String getSize() {
		return size;
	}
	
	/**
	 * 设置三围属性
	 *
	 * @param size
	 */
	public void setSize(java.lang.String size) {
		this.size = size;
	}
	
	/**
     * 获取环信注册返回结果属性
     *
     * @return easemobStatusCode
     */
	public java.lang.String getEasemobStatusCode() {
		return easemobStatusCode;
	}
	
	/**
	 * 设置环信注册返回结果属性
	 *
	 * @param easemobStatusCode
	 */
	public void setEasemobStatusCode(java.lang.String easemobStatusCode) {
		this.easemobStatusCode = easemobStatusCode;
	}
	
	/**
     * 获取最后一次登录时间属性
     *
     * @return lastLoginTime
     */
	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}
	
	/**
	 * 设置最后一次登录时间属性
	 *
	 * @param lastLoginTime
	 */
	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
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
     * 获取状态 0正常；1已删除属性
     *
     * @return status
     */
	public java.lang.Integer getStatus() {
		return status;
	}
	
	/**
	 * 设置状态 0正常；1已删除属性
	 *
	 * @param status
	 */
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	/**
     * 获取推送状态  0正常；1已关闭属性
     *
     * @return pushStatus
     */
	public java.lang.Integer getPushStatus() {
		return pushStatus;
	}
	
	/**
	 * 设置推送状态  0正常；1已关闭属性
	 *
	 * @param pushStatus
	 */
	public void setPushStatus(java.lang.Integer pushStatus) {
		this.pushStatus = pushStatus;
	}
	
	/**
     * 获取登录token；登录唯一标识属性
     *
     * @return logintoken
     */
	public java.lang.String getLogintoken() {
		return logintoken;
	}
	
	/**
	 * 设置登录token；登录唯一标识属性
	 *
	 * @param logintoken
	 */
	public void setLogintoken(java.lang.String logintoken) {
		this.logintoken = logintoken;
	}
	
	/**
     * 获取注册类型  0普通注册；1QQ；2微信；3微博属性
     *
     * @return registerType
     */
	public java.lang.Integer getRegisterType() {
		return registerType;
	}
	
	/**
	 * 设置注册类型  0普通注册；1QQ；2微信；3微博属性
	 *
	 * @param registerType
	 */
	public void setRegisterType(java.lang.Integer registerType) {
		this.registerType = registerType;
	}
	
	/**
     * 获取认证级别：0未认证；1实名认证；2资历认证属性
     *
     * @return authenticateLevel
     */
	public java.lang.Integer getAuthenticateLevel() {
		return authenticateLevel;
	}
	
	/**
	 * 设置认证级别：0未认证；1实名认证；2资历认证属性
	 *
	 * @param authenticateLevel
	 */
	public void setAuthenticateLevel(java.lang.Integer authenticateLevel) {
		this.authenticateLevel = authenticateLevel;
	}
	
	/**
     * 获取用户级别：0普通用户；实名认证；2资历认证属性
     *
     * @return level
     */
	public java.lang.Integer getLevel() {
		return level;
	}
	
	/**
	 * 设置用户级别：0普通用户；实名认证；2资历认证属性
	 *
	 * @param level
	 */
	public void setLevel(java.lang.Integer level) {
		this.level = level;
	}
	
	/**
     * 获取身份证属性
     *
     * @return idcard
     */
	public java.lang.String getIdcard() {
		return idcard;
	}
	
	/**
	 * 设置身份证属性
	 *
	 * @param idcard
	 */
	public void setIdcard(java.lang.String idcard) {
		this.idcard = idcard;
	}
	
	/**
     * 获取评论总评分  属性
     *
     * @return totalScore
     */
	public java.lang.Integer getTotalScore() {
		return totalScore;
	}
	
	/**
	 * 设置评论总评分  属性
	 *
	 * @param totalScore
	 */
	public void setTotalScore(java.lang.Integer totalScore) {
		this.totalScore = totalScore;
	}
	
	/**
     * 获取评论人数属性
     *
     * @return commentNum
     */
	public java.lang.Integer getCommentNum() {
		return commentNum;
	}
	
	/**
	 * 设置评论人数属性
	 *
	 * @param commentNum
	 */
	public void setCommentNum(java.lang.Integer commentNum) {
		this.commentNum = commentNum;
	}
	
	/**
     * 获取当前经度属性
     *
     * @return lon
     */
	public java.lang.Double getLon() {
		return lon;
	}
	
	/**
	 * 设置当前经度属性
	 *
	 * @param lon
	 */
	public void setLon(java.lang.Double lon) {
		this.lon = lon;
	}
	
	/**
     * 获取当前纬度属性
     *
     * @return lat
     */
	public java.lang.Double getLat() {
		return lat;
	}
	
	/**
	 * 设置当前纬度属性
	 *
	 * @param lat
	 */
	public void setLat(java.lang.Double lat) {
		this.lat = lat;
	}

	public SystemPictureInfo getSystemPictureInfo() {
		return systemPictureInfo;
	}

	public void setSystemPictureInfo(SystemPictureInfo systemPictureInfo) {
		this.systemPictureInfo = systemPictureInfo;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<String> getActorIdList() {
		return actorIdList;
	}

	public void setActorIdList(List<String> actorIdList) {
		this.actorIdList = actorIdList;
	}

	public String getAuthenticateLevelName() {
		return authenticateLevelName;
	}

	public void setAuthenticateLevelName(String authenticateLevelName) {
		this.authenticateLevelName = authenticateLevelName;
	}
	
	public Float getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(Float avgScore) {
		this.avgScore = avgScore;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ActorInfo");
        sb.append("{id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", realName=").append(realName);
        sb.append(", mobile=").append(mobile);
        sb.append(", password=").append(password);
        sb.append(", mkey=").append(mkey);
        sb.append(", imgUuid=").append(imgUuid);
        sb.append(", sex=").append(sex);
        sb.append(", birthDay=").append(birthDay);
        sb.append(", city=").append(city);
        sb.append(", actCities=").append(actCities);
        sb.append(", height=").append(height);
        sb.append(", weight=").append(weight);
        sb.append(", shoesSize=").append(shoesSize);
        sb.append(", size=").append(size);
        sb.append(", easemobStatusCode=").append(easemobStatusCode);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append(", pushStatus=").append(pushStatus);
        sb.append(", logintoken=").append(logintoken);
        sb.append(", registerType=").append(registerType);
        sb.append(", authenticateLevel=").append(authenticateLevel);
        sb.append(", level=").append(level);
        sb.append(", idcard=").append(idcard);
        sb.append(", totalScore=").append(totalScore);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", lon=").append(lon);
        sb.append(", lat=").append(lat);
		sb.append('}');
        return sb.toString();
    }

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
    
}