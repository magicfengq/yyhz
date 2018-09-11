package com.yyhz.sc.data.model;

import java.io.Serializable;
import com.yyhz.sc.base.entity.BaseEntity;

/**
 * advert实体表()
 * @author lipeng
 * @date 2017-02-25 13:03:33
 * @project 
 */
 @SuppressWarnings("serial")
public class Advert extends BaseEntity implements Serializable {
	private java.lang.String id; // 主键id
	private java.lang.Integer position; // 轮播图位置；此字段为扩展字段
	private java.lang.String title; // 标题
	private java.lang.Integer type; // 类型 0广告；1个人秀
	private java.lang.String imgUuid; // 
	private java.lang.String picurl; // 轮播图图片地址
	private java.lang.String params; // 广告类型存富文本；个人秀存TA的id
	private java.lang.Integer readNum; // 浏览数
	private java.lang.String creater; // 创建人
	private java.util.Date createTime; // 创建时间
	private java.lang.Integer status; // 状态：0正常；1删除 2禁用
	private java.lang.String operater; // 修改人
	private java.util.Date operateTime; // 修改时间
	private java.lang.Integer orderNum; // 排序号，查询时越小越靠前
	private java.util.Date orderTime; // 排序时间；越新越靠前
	
	private String userId;
	private String userName;
	
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
     * 获取轮播图位置；此字段为扩展字段属性
     *
     * @return position
     */
	public java.lang.Integer getPosition() {
		return position;
	}
	
	/**
	 * 设置轮播图位置；此字段为扩展字段属性
	 *
	 * @param position
	 */
	public void setPosition(java.lang.Integer position) {
		this.position = position;
	}
	
	/**
     * 获取标题属性
     *
     * @return title
     */
	public java.lang.String getTitle() {
		return title;
	}
	
	/**
	 * 设置标题属性
	 *
	 * @param title
	 */
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	
	/**
     * 获取类型 0广告；1个人秀属性
     *
     * @return type
     */
	public java.lang.Integer getType() {
		return type;
	}
	
	/**
	 * 设置类型 0广告；1个人秀属性
	 *
	 * @param type
	 */
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	/**
     * 获取属性
     *
     * @return imgUuid
     */
	public java.lang.String getImgUuid() {
		return imgUuid;
	}
	
	/**
	 * 设置属性
	 *
	 * @param imgUuid
	 */
	public void setImgUuid(java.lang.String imgUuid) {
		this.imgUuid = imgUuid;
	}
	
	/**
     * 获取轮播图图片地址属性
     *
     * @return picurl
     */
	public java.lang.String getPicurl() {
		return picurl;
	}
	
	/**
	 * 设置轮播图图片地址属性
	 *
	 * @param picurl
	 */
	public void setPicurl(java.lang.String picurl) {
		this.picurl = picurl;
	}
	
	/**
     * 获取广告类型存富文本；个人秀存TA的id属性
     *
     * @return params
     */
	public java.lang.String getParams() {
		return params;
	}
	
	/**
	 * 设置广告类型存富文本；个人秀存TA的id属性
	 *
	 * @param params
	 */
	public void setParams(java.lang.String params) {
		this.params = params;
	}
	
	/**
     * 获取浏览数属性
     *
     * @return readNum
     */
	public java.lang.Integer getReadNum() {
		return readNum;
	}
	
	/**
	 * 设置浏览数属性
	 *
	 * @param readNum
	 */
	public void setReadNum(java.lang.Integer readNum) {
		this.readNum = readNum;
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
     * 获取状态：0正常；1删除 2禁用属性
     *
     * @return status
     */
	public java.lang.Integer getStatus() {
		return status;
	}
	
	/**
	 * 设置状态：0正常；1删除 2禁用属性
	 *
	 * @param status
	 */
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	/**
     * 获取修改人属性
     *
     * @return operater
     */
	public java.lang.String getOperater() {
		return operater;
	}
	
	/**
	 * 设置修改人属性
	 *
	 * @param operater
	 */
	public void setOperater(java.lang.String operater) {
		this.operater = operater;
	}
	
	/**
     * 获取修改时间属性
     *
     * @return operateTime
     */
	public java.util.Date getOperateTime() {
		return operateTime;
	}
	
	/**
	 * 设置修改时间属性
	 *
	 * @param operateTime
	 */
	public void setOperateTime(java.util.Date operateTime) {
		this.operateTime = operateTime;
	}
	
	/**
     * 获取排序号，查询时越小越靠前属性
     *
     * @return orderNum
     */
	public java.lang.Integer getOrderNum() {
		return orderNum;
	}
	
	/**
	 * 设置排序号，查询时越小越靠前属性
	 *
	 * @param orderNum
	 */
	public void setOrderNum(java.lang.Integer orderNum) {
		this.orderNum = orderNum;
	}
	
	/**
     * 获取排序时间；越新越靠前属性
     *
     * @return orderTime
     */
	public java.util.Date getOrderTime() {
		return orderTime;
	}
	
	/**
	 * 设置排序时间；越新越靠前属性
	 *
	 * @param orderTime
	 */
	public void setOrderTime(java.util.Date orderTime) {
		this.orderTime = orderTime;
	}

	public SystemPictureInfo getSystemPictureInfo() {
		return systemPictureInfo;
	}

	public void setSystemPictureInfo(SystemPictureInfo systemPictureInfo) {
		this.systemPictureInfo = systemPictureInfo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Advert");
        sb.append("{id=").append(id);
        sb.append(", position=").append(position);
        sb.append(", title=").append(title);
        sb.append(", type=").append(type);
        sb.append(", imgUuid=").append(imgUuid);
        sb.append(", picurl=").append(picurl);
        sb.append(", params=").append(params);
        sb.append(", readNum=").append(readNum);
        sb.append(", creater=").append(creater);
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append(", operater=").append(operater);
        sb.append(", operateTime=").append(operateTime);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", orderTime=").append(orderTime);
		sb.append('}');
        return sb.toString();
    }
    
}