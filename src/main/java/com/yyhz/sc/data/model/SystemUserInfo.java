package com.yyhz.sc.data.model;

import java.io.Serializable;

import com.yyhz.sc.base.entity.BaseEntity;

/**
 * system_user_info实体表()
 * @author lipeng
 * @date 2017-02-25 11:05:57
 * @project 
 */
 @SuppressWarnings("serial")
public class SystemUserInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String userId; // 登陆账号
	private java.lang.String userName; // 用户名称
	private java.lang.String userPwd; // 用户密码
	private java.lang.String roleId; // 用户角色
	private java.lang.String mobile; // 手机号码
	private java.lang.String email; // 邮箱
	private java.lang.String address; // 地址
	private java.lang.Integer status; // 状态1启用0禁用
	private java.lang.Integer isDelete; // 是否删除0否1是
	private java.lang.String description; // 备注
	
	// 角色对象
    private SystemRoleInfo systemRole = new SystemRoleInfo();
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
     * 获取登陆账号属性
     *
     * @return userId
     */
	public java.lang.String getUserId() {
		return userId;
	}
	
	/**
	 * 设置登陆账号属性
	 *
	 * @param userId
	 */
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	/**
     * 获取用户名称属性
     *
     * @return userName
     */
	public java.lang.String getUserName() {
		return userName;
	}
	
	/**
	 * 设置用户名称属性
	 *
	 * @param userName
	 */
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}
	
	/**
     * 获取用户密码属性
     *
     * @return userPwd
     */
	public java.lang.String getUserPwd() {
		return userPwd;
	}
	
	/**
	 * 设置用户密码属性
	 *
	 * @param userPwd
	 */
	public void setUserPwd(java.lang.String userPwd) {
		this.userPwd = userPwd;
	}
	
	/**
     * 获取用户角色属性
     *
     * @return roleId
     */
	public java.lang.String getRoleId() {
		return roleId;
	}
	
	/**
	 * 设置用户角色属性
	 *
	 * @param roleId
	 */
	public void setRoleId(java.lang.String roleId) {
		this.roleId = roleId;
	}
	
	/**
     * 获取手机号码属性
     *
     * @return mobile
     */
	public java.lang.String getMobile() {
		return mobile;
	}
	
	/**
	 * 设置手机号码属性
	 *
	 * @param mobile
	 */
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	
	/**
     * 获取邮箱属性
     *
     * @return email
     */
	public java.lang.String getEmail() {
		return email;
	}
	
	/**
	 * 设置邮箱属性
	 *
	 * @param email
	 */
	public void setEmail(java.lang.String email) {
		this.email = email;
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
     * 获取状态1启用0禁用属性
     *
     * @return status
     */
	public java.lang.Integer getStatus() {
		return status;
	}
	
	/**
	 * 设置状态1启用0禁用属性
	 *
	 * @param status
	 */
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	/**
     * 获取是否删除0否1是属性
     *
     * @return isDelete
     */
	public java.lang.Integer getIsDelete() {
		return isDelete;
	}
	
	/**
	 * 设置是否删除0否1是属性
	 *
	 * @param isDelete
	 */
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	/**
     * 获取备注属性
     *
     * @return description
     */
	public java.lang.String getDescription() {
		return description;
	}
	
	/**
	 * 设置备注属性
	 *
	 * @param description
	 */
	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public SystemRoleInfo getSystemRole() {
		return systemRole;
	}

	public void setSystemRole(SystemRoleInfo systemRole) {
		this.systemRole = systemRole;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SystemUserInfo");
        sb.append("{id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", userPwd=").append(userPwd);
        sb.append(", roleId=").append(roleId);
        sb.append(", mobile=").append(mobile);
        sb.append(", email=").append(email);
        sb.append(", address=").append(address);
        sb.append(", status=").append(status);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", description=").append(description);
		sb.append('}');
        return sb.toString();
    }
    
}