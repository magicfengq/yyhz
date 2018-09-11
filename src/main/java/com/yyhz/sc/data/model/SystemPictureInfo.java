package com.yyhz.sc.data.model;

import java.io.Serializable;
import com.yyhz.sc.base.entity.BaseEntity;

/**
 * system_picture_info实体表()
 * @author lipeng
 * @date 2017-02-25 13:17:36
 * @project 
 */
 @SuppressWarnings("serial")
public class SystemPictureInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String uuid; // uuid关联
	private java.lang.String urlPath; // 图片路径
	private java.lang.Integer fwidth; // 文件宽
	private java.lang.Integer fheight; // 文件高
	private java.lang.Integer fsize; // 文件大小
	private java.lang.String ftype; // 文件类型
	private java.lang.String suffix; // 文件后缀
	private java.lang.String name; // 文件名称
	private java.util.Date cdate; // 创建日期
	private java.lang.String params; // 参数
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
     * 获取uuid关联属性
     *
     * @return uuid
     */
	public java.lang.String getUuid() {
		return uuid;
	}
	
	/**
	 * 设置uuid关联属性
	 *
	 * @param uuid
	 */
	public void setUuid(java.lang.String uuid) {
		this.uuid = uuid;
	}
	
	/**
     * 获取图片路径属性
     *
     * @return urlPath
     */
	public java.lang.String getUrlPath() {
		return urlPath;
	}
	
	/**
	 * 设置图片路径属性
	 *
	 * @param urlPath
	 */
	public void setUrlPath(java.lang.String urlPath) {
		this.urlPath = urlPath;
	}
	
	/**
     * 获取文件宽属性
     *
     * @return fwidth
     */
	public java.lang.Integer getFwidth() {
		return fwidth;
	}
	
	/**
	 * 设置文件宽属性
	 *
	 * @param fwidth
	 */
	public void setFwidth(java.lang.Integer fwidth) {
		this.fwidth = fwidth;
	}
	
	/**
     * 获取文件高属性
     *
     * @return fheight
     */
	public java.lang.Integer getFheight() {
		return fheight;
	}
	
	/**
	 * 设置文件高属性
	 *
	 * @param fheight
	 */
	public void setFheight(java.lang.Integer fheight) {
		this.fheight = fheight;
	}
	
	/**
     * 获取文件大小属性
     *
     * @return fsize
     */
	public java.lang.Integer getFsize() {
		return fsize;
	}
	
	/**
	 * 设置文件大小属性
	 *
	 * @param fsize
	 */
	public void setFsize(java.lang.Integer fsize) {
		this.fsize = fsize;
	}
	
	/**
     * 获取文件类型属性
     *
     * @return ftype
     */
	public java.lang.String getFtype() {
		return ftype;
	}
	
	/**
	 * 设置文件类型属性
	 *
	 * @param ftype
	 */
	public void setFtype(java.lang.String ftype) {
		this.ftype = ftype;
	}
	
	/**
     * 获取文件后缀属性
     *
     * @return suffix
     */
	public java.lang.String getSuffix() {
		return suffix;
	}
	
	/**
	 * 设置文件后缀属性
	 *
	 * @param suffix
	 */
	public void setSuffix(java.lang.String suffix) {
		this.suffix = suffix;
	}
	
	/**
     * 获取文件名称属性
     *
     * @return name
     */
	public java.lang.String getName() {
		return name;
	}
	
	/**
	 * 设置文件名称属性
	 *
	 * @param name
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	/**
     * 获取创建日期属性
     *
     * @return cdate
     */
	public java.util.Date getCdate() {
		return cdate;
	}
	
	/**
	 * 设置创建日期属性
	 *
	 * @param cdate
	 */
	public void setCdate(java.util.Date cdate) {
		this.cdate = cdate;
	}
	
	/**
     * 获取参数属性
     *
     * @return params
     */
	public java.lang.String getParams() {
		return params;
	}
	
	/**
	 * 设置参数属性
	 *
	 * @param params
	 */
	public void setParams(java.lang.String params) {
		this.params = params;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SystemPictureInfo");
        sb.append("{id=").append(id);
        sb.append(", uuid=").append(uuid);
        sb.append(", urlPath=").append(urlPath);
        sb.append(", fwidth=").append(fwidth);
        sb.append(", fheight=").append(fheight);
        sb.append(", fsize=").append(fsize);
        sb.append(", ftype=").append(ftype);
        sb.append(", suffix=").append(suffix);
        sb.append(", name=").append(name);
        sb.append(", cdate=").append(cdate);
        sb.append(", params=").append(params);
		sb.append('}');
        return sb.toString();
    }
    
}