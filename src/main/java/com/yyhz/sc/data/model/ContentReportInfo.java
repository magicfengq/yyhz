package com.yyhz.sc.data.model;

import java.io.Serializable;

import com.yyhz.sc.base.entity.BaseEntity;

/**
 * content_report_info实体表()
 * @author fengq
 * @date 2019-01-22 17:11:05
 * @project app首页后台管理
 */
 @SuppressWarnings("serial")
public class ContentReportInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // id
	private java.lang.String type; // 0：卡片 1：通告 2：秀
	private java.lang.String reportId; // id
	private java.lang.String createTime; // createTime
	private java.lang.String status; // 0：未处理 1：同意 2：不同意
	private java.lang.String creater; // creater
	private java.lang.String reason; // 原因
	/**
     * 获取id属性
     *
     * @return id
     */
	public java.lang.String getId() {
		return id;
	}
	
	/**
	 * 设置id属性
	 *
	 * @param id
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}
	/**
     * 获取0：卡片 1：通告 2：秀属性
     *
     * @return type
     */
	public java.lang.String getType() {
		return type;
	}
	
	/**
	 * 设置0：卡片 1：通告 2：秀属性
	 *
	 * @param type
	 */
	public void setType(java.lang.String type) {
		this.type = type;
	}
	/**
     * 获取id属性
     *
     * @return reportId
     */
	public java.lang.String getReportId() {
		return reportId;
	}
	
	/**
	 * 设置id属性
	 *
	 * @param reportId
	 */
	public void setReportId(java.lang.String reportId) {
		this.reportId = reportId;
	}
	/**
     * 获取createTime属性
     *
     * @return createTime
     */
	public java.lang.String getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置createTime属性
	 *
	 * @param createTime
	 */
	public void setCreateTime(java.lang.String createTime) {
		this.createTime = createTime;
	}
	/**
     * 获取0：未处理 1：同意 2：不同意属性
     *
     * @return status
     */
	public java.lang.String getStatus() {
		return status;
	}
	
	/**
	 * 设置0：未处理 1：同意 2：不同意属性
	 *
	 * @param status
	 */
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	/**
     * 获取creater属性
     *
     * @return creater
     */
	public java.lang.String getCreater() {
		return creater;
	}
	
	/**
	 * 设置creater属性
	 *
	 * @param creater
	 */
	public void setCreater(java.lang.String creater) {
		this.creater = creater;
	}
	/**
     * 获取原因属性
     *
     * @return reason
     */
	public java.lang.String getReason() {
		return reason;
	}
	
	/**
	 * 设置原因属性
	 *
	 * @param reason
	 */
	public void setReason(java.lang.String reason) {
		this.reason = reason;
	}
}