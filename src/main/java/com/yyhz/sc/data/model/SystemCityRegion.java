package com.yyhz.sc.data.model;

import java.io.Serializable;
import com.yyhz.sc.base.entity.BaseEntity;

/**
 * system_city_region实体表()
 * @author crazyt
 * @date 2017-03-20 08:21:05
 * @project 
 */
 @SuppressWarnings("serial")
public class SystemCityRegion extends BaseEntity implements Serializable {
	private java.lang.String code; // 行政区划代码
	private java.lang.String nameCn; // 行政区划名称
	private java.lang.String parentCode; // 上级行政区划
	private java.lang.String nameEn; // 城市拼音
	private java.lang.String level; // 行政区划等级
	
	private String keyword;
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
     * 获取行政区划代码属性
     *
     * @return code
     */
	public java.lang.String getCode() {
		return code;
	}
	
	/**
	 * 设置行政区划代码属性
	 *
	 * @param code
	 */
	public void setCode(java.lang.String code) {
		this.code = code;
	}
	
	/**
     * 获取行政区划名称属性
     *
     * @return nameCn
     */
	public java.lang.String getNameCn() {
		return nameCn;
	}
	
	/**
	 * 设置行政区划名称属性
	 *
	 * @param nameCn
	 */
	public void setNameCn(java.lang.String nameCn) {
		this.nameCn = nameCn;
	}
	
	/**
     * 获取上级行政区划属性
     *
     * @return parentCode
     */
	public java.lang.String getParentCode() {
		return parentCode;
	}
	
	/**
	 * 设置上级行政区划属性
	 *
	 * @param parentCode
	 */
	public void setParentCode(java.lang.String parentCode) {
		this.parentCode = parentCode;
	}
	
	/**
     * 获取城市拼音属性
     *
     * @return nameEn
     */
	public java.lang.String getNameEn() {
		return nameEn;
	}
	
	/**
	 * 设置城市拼音属性
	 *
	 * @param nameEn
	 */
	public void setNameEn(java.lang.String nameEn) {
		this.nameEn = nameEn;
	}
	
	/**
     * 获取行政区划等级属性
     *
     * @return level
     */
	public java.lang.String getLevel() {
		return level;
	}
	
	/**
	 * 设置行政区划等级属性
	 *
	 * @param level
	 */
	public void setLevel(java.lang.String level) {
		this.level = level;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SystemCityRegion");
        sb.append("{code=").append(code);
        sb.append(", nameCn=").append(nameCn);
        sb.append(", parentCode=").append(parentCode);
        sb.append(", nameEn=").append(nameEn);
        sb.append(", level=").append(level);
		sb.append('}');
        return sb.toString();
    }
    
}