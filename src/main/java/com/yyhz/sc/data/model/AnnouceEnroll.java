package com.yyhz.sc.data.model;

import java.io.Serializable;

import com.yyhz.sc.base.entity.BaseEntity;

/**
 * annouce_enroll实体表()
 * @author crazyt
 * @date 2017-03-13 10:41:58
 * @project 
 */
 @SuppressWarnings("serial")
public class AnnouceEnroll extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String announceId; // 通告id
	private java.lang.String actorId; // 报名艺人
	private java.util.Date createTime; // 报名时间
	private java.util.Date checkTime; // 审核时间
	private java.lang.Integer checkStatus; // 审核状态  0待审核；1通过；2拒绝；审核人是通告的发布人
	private java.lang.Integer enrollStatus; // 报名状态：0已报名；1已取消
	
	private String actorImgUrl; // 报名艺人头像url
	private String actorName; // 报名艺人姓名
	
	private String title;//通告标题
	private Integer type;//通告类型
	
	private String imgUuid; // 报名艺人头像
	private String realName;
	private Integer authenticateLevel; // 报名艺人认证等级
	private Integer avgScore; // 报名艺人分数
	private String announceCreater; // 通告创建者
	
	public String getAnnounceCreater() {
		return announceCreater;
	}

	public void setAnnounceCreater(String announceCreater) {
		this.announceCreater = announceCreater;
	}

	public Integer getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(Integer avgScore) {
		this.avgScore = avgScore;
	}

	public Integer getAuthenticateLevel() {
		return authenticateLevel;
	}

	public void setAuthenticateLevel(Integer authenticateLevel) {
		this.authenticateLevel = authenticateLevel;
	}

	public String getRealName() {
		return realName;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	private SystemPictureInfo systemPictureInfo;
	
	public String getImgUuid() {
		return imgUuid;
	}

	public void setImgUuid(String imgUuid) {
		this.imgUuid = imgUuid;
	}

	public SystemPictureInfo getSystemPictureInfo() {
		return systemPictureInfo;
	}

	public void setSystemPictureInfo(SystemPictureInfo systemPictureInfo) {
		this.systemPictureInfo = systemPictureInfo;
	}

	public String getActorImgUrl() {
		return actorImgUrl;
	}

	public void setActorImgUrl(String actorImgUrl) {
		this.actorImgUrl = actorImgUrl;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
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
     * 获取报名艺人属性
     *
     * @return actorId
     */
	public java.lang.String getActorId() {
		return actorId;
	}
	
	/**
	 * 设置报名艺人属性
	 *
	 * @param actorId
	 */
	public void setActorId(java.lang.String actorId) {
		this.actorId = actorId;
	}
	
	/**
     * 获取报名时间属性
     *
     * @return createTime
     */
	public java.util.Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置报名时间属性
	 *
	 * @param createTime
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	/**
     * 获取审核时间属性
     *
     * @return checkTime
     */
	public java.util.Date getCheckTime() {
		return checkTime;
	}
	
	/**
	 * 设置审核时间属性
	 *
	 * @param checkTime
	 */
	public void setCheckTime(java.util.Date checkTime) {
		this.checkTime = checkTime;
	}
	
	/**
     * 获取审核状态  0待审核；1通过；2拒绝；审核人是通告的发布人属性
     *
     * @return checkStatus
     */
	public java.lang.Integer getCheckStatus() {
		return checkStatus;
	}
	
	/**
	 * 设置审核状态  0待审核；1通过；2拒绝；审核人是通告的发布人属性
	 *
	 * @param checkStatus
	 */
	public void setCheckStatus(java.lang.Integer checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	/**
     * 获取报名状态：0已报名；1已取消属性
     *
     * @return enrollStatus
     */
	public java.lang.Integer getEnrollStatus() {
		return enrollStatus;
	}
	
	/**
	 * 设置报名状态：0已报名；1已取消属性
	 *
	 * @param enrollStatus
	 */
	public void setEnrollStatus(java.lang.Integer enrollStatus) {
		this.enrollStatus = enrollStatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("AnnouceEnroll");
        sb.append("{id=").append(id);
        sb.append(", announceId=").append(announceId);
        sb.append(", actorId=").append(actorId);
        sb.append(", createTime=").append(createTime);
        sb.append(", checkTime=").append(checkTime);
        sb.append(", checkStatus=").append(checkStatus);
        sb.append(", enrollStatus=").append(enrollStatus);
		sb.append('}');
        return sb.toString();
    }
    
}