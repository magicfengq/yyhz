package com.yyhz.sc.data.model;

import java.io.Serializable;
import com.yyhz.sc.base.entity.BaseEntity;

/**
 * public_type实体表()
 * @author crazyt
 * @date 2017-03-12 16:08:27
 * @project 
 */
 @SuppressWarnings("serial")
public class PublicType extends BaseEntity implements Serializable {
	private java.lang.String id; // 主键
	private java.lang.Integer type; // 角色类型 1艺人；2租借；3策划/创意；4婚礼/派对
	private java.lang.String name; // 类型名称
	private java.lang.Integer status; // 0正常；1已删除
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
     * 获取角色类型 1艺人；2租借；3策划/创意；4婚礼/派对属性
     *
     * @return type
     */
	public java.lang.Integer getType() {
		return type;
	}
	
	/**
	 * 设置角色类型 1艺人；2租借；3策划/创意；4婚礼/派对属性
	 *
	 * @param type
	 */
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	/**
     * 获取类型名称属性
     *
     * @return name
     */
	public java.lang.String getName() {
		return name;
	}
	
	/**
	 * 设置类型名称属性
	 *
	 * @param name
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	/**
     * 获取0正常；1已删除属性
     *
     * @return status
     */
	public java.lang.Integer getStatus() {
		return status;
	}
	
	/**
	 * 设置0正常；1已删除属性
	 *
	 * @param status
	 */
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("PublicType");
        sb.append("{id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", name=").append(name);
        sb.append(", status=").append(status);
		sb.append('}');
        return sb.toString();
    }
    
}