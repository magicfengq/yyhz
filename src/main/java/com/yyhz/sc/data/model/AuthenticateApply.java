package com.yyhz.sc.data.model;

import java.io.Serializable;
import java.util.List;

import com.yyhz.sc.base.entity.BaseEntity;

/**
 * authenticate_apply实体表()
 * @author crazyt
 * @date 2017-03-28 09:19:55
 * @project 
 */
 @SuppressWarnings("serial")
public class AuthenticateApply extends BaseEntity implements Serializable {
	private java.lang.String id; // 主键
	private java.lang.String actorId; // 申请用户
	private java.lang.Integer userCurrentLevel; // 认证级别 0未认证 1实名认 2资历认证
	private java.util.Date applyTime; // 认证申请时间
	private java.lang.String realName; // 真实姓名
	private java.lang.String mobile; // 手机号
	private java.lang.String idcard; // 身份证号
	private java.lang.String photo1; // 身份证正面
	private java.lang.String photo2; // 身份证反面
	private java.lang.String photo3; // 营业执照
	private java.lang.String photo4; // 资格证
	private java.lang.String checkUser; // 后台审核人
	private java.util.Date checkTime; // 审核时间
	private java.lang.Integer checkStatus; // 审核状态  0待审核；1通过；2拒绝
	
	private ActorInfo actorInfo;
	private List<String> actorIdList;
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
     * 获取申请用户属性
     *
     * @return actorId
     */
	public java.lang.String getActorId() {
		return actorId;
	}
	
	/**
	 * 设置申请用户属性
	 *
	 * @param actorId
	 */
	public void setActorId(java.lang.String actorId) {
		this.actorId = actorId;
	}
	
	/**
     * 获取认证级别 0未认证 1实名认 2资历认证属性
     *
     * @return userCurrentLevel
     */
	public java.lang.Integer getUserCurrentLevel() {
		return userCurrentLevel;
	}
	
	/**
	 * 设置认证级别 0未认证 1实名认 2资历认证属性
	 *
	 * @param userCurrentLevel
	 */
	public void setUserCurrentLevel(java.lang.Integer userCurrentLevel) {
		this.userCurrentLevel = userCurrentLevel;
	}
	
	/**
     * 获取认证申请时间属性
     *
     * @return applyTime
     */
	public java.util.Date getApplyTime() {
		return applyTime;
	}
	
	/**
	 * 设置认证申请时间属性
	 *
	 * @param applyTime
	 */
	public void setApplyTime(java.util.Date applyTime) {
		this.applyTime = applyTime;
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
     * 获取身份证号属性
     *
     * @return idcard
     */
	public java.lang.String getIdcard() {
		return idcard;
	}
	
	/**
	 * 设置身份证号属性
	 *
	 * @param idcard
	 */
	public void setIdcard(java.lang.String idcard) {
		this.idcard = idcard;
	}
	
	/**
     * 获取身份证正面属性
     *
     * @return photo1
     */
	public java.lang.String getPhoto1() {
		return photo1;
	}
	
	/**
	 * 设置身份证正面属性
	 *
	 * @param photo1
	 */
	public void setPhoto1(java.lang.String photo1) {
		this.photo1 = photo1;
	}
	
	/**
     * 获取身份证反面属性
     *
     * @return photo2
     */
	public java.lang.String getPhoto2() {
		return photo2;
	}
	
	/**
	 * 设置身份证反面属性
	 *
	 * @param photo2
	 */
	public void setPhoto2(java.lang.String photo2) {
		this.photo2 = photo2;
	}
	
	/**
     * 获取营业执照属性
     *
     * @return photo3
     */
	public java.lang.String getPhoto3() {
		return photo3;
	}
	
	/**
	 * 设置营业执照属性
	 *
	 * @param photo3
	 */
	public void setPhoto3(java.lang.String photo3) {
		this.photo3 = photo3;
	}
	
	/**
     * 获取资格证属性
     *
     * @return photo4
     */
	public java.lang.String getPhoto4() {
		return photo4;
	}
	
	/**
	 * 设置资格证属性
	 *
	 * @param photo4
	 */
	public void setPhoto4(java.lang.String photo4) {
		this.photo4 = photo4;
	}
	
	/**
     * 获取后台审核人属性
     *
     * @return checkUser
     */
	public java.lang.String getCheckUser() {
		return checkUser;
	}
	
	/**
	 * 设置后台审核人属性
	 *
	 * @param checkUser
	 */
	public void setCheckUser(java.lang.String checkUser) {
		this.checkUser = checkUser;
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
     * 获取审核状态  0待审核；1通过；2拒绝属性
     *
     * @return checkStatus
     */
	public java.lang.Integer getCheckStatus() {
		return checkStatus;
	}
	
	/**
	 * 设置审核状态  0待审核；1通过；2拒绝属性
	 *
	 * @param checkStatus
	 */
	public void setCheckStatus(java.lang.Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public ActorInfo getActorInfo() {
		return actorInfo;
	}

	public void setActorInfo(ActorInfo actorInfo) {
		this.actorInfo = actorInfo;
	}

	public List<String> getActorIdList() {
		return actorIdList;
	}

	public void setActorIdList(List<String> actorIdList) {
		this.actorIdList = actorIdList;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("AuthenticateApply");
        sb.append("{id=").append(id);
        sb.append(", actorId=").append(actorId);
        sb.append(", userCurrentLevel=").append(userCurrentLevel);
        sb.append(", applyTime=").append(applyTime);
        sb.append(", realName=").append(realName);
        sb.append(", mobile=").append(mobile);
        sb.append(", idcard=").append(idcard);
        sb.append(", photo1=").append(photo1);
        sb.append(", photo2=").append(photo2);
        sb.append(", photo3=").append(photo3);
        sb.append(", photo4=").append(photo4);
        sb.append(", checkUser=").append(checkUser);
        sb.append(", checkTime=").append(checkTime);
        sb.append(", checkStatus=").append(checkStatus);
		sb.append('}');
        return sb.toString();
    }
    
}