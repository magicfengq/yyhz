package com.yyhz.sc.data.model;

import java.io.Serializable;
import com.yyhz.sc.base.entity.BaseEntity;

/**
 * service_info实体表()
 * @author lipeng
 * @date 2017-04-07 15:19:07
 * @project 
 */
 @SuppressWarnings("serial")
public class ServiceInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String type; // 类型:0关于我们,1注册协议,2使用帮组,3服务条款
	private java.lang.String context; // 
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
     * 获取类型:0关于我们,1注册协议,2使用帮组,3服务条款属性
     *
     * @return type
     */
	public java.lang.String getType() {
		return type;
	}
	
	/**
	 * 设置类型:0关于我们,1注册协议,2使用帮组,3服务条款属性
	 *
	 * @param type
	 */
	public void setType(java.lang.String type) {
		this.type = type;
	}
	
	/**
     * 获取属性
     *
     * @return context
     */
	public java.lang.String getContext() {
		return context;
	}
	
	/**
	 * 设置属性
	 *
	 * @param context
	 */
	public void setContext(java.lang.String context) {
		this.context = context;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ServiceInfo");
        sb.append("{id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", context=").append(context);
		sb.append('}');
        return sb.toString();
    }
    
}