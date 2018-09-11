package com.yyhz.sc.data.model;

import java.io.Serializable;
import com.yyhz.sc.base.entity.BaseEntity;

/**
 * recommendsite实体表()
 * @author crazyt
 * @date 2017-03-21 20:39:11
 * @project 
 */
 @SuppressWarnings("serial")
public class Recommendsite extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String sitename; // 场地名称
	private java.lang.String siteaddress; // 场地地址
	private java.lang.String enterclose; // 通道详情
	private java.lang.String sitetelephone; // 场地电话
	private java.lang.String recomname; // 推荐人姓名
	private java.lang.String recomphone; // 推荐人联系方式
	private java.lang.String otherexplain; // 其他说明
	private java.util.Date createdate; // 创建日期
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
     * 获取场地名称属性
     *
     * @return sitename
     */
	public java.lang.String getSitename() {
		return sitename;
	}
	
	/**
	 * 设置场地名称属性
	 *
	 * @param sitename
	 */
	public void setSitename(java.lang.String sitename) {
		this.sitename = sitename;
	}
	
	/**
     * 获取场地地址属性
     *
     * @return siteaddress
     */
	public java.lang.String getSiteaddress() {
		return siteaddress;
	}
	
	/**
	 * 设置场地地址属性
	 *
	 * @param siteaddress
	 */
	public void setSiteaddress(java.lang.String siteaddress) {
		this.siteaddress = siteaddress;
	}
	
	/**
     * 获取通道详情属性
     *
     * @return enterclose
     */
	public java.lang.String getEnterclose() {
		return enterclose;
	}
	
	/**
	 * 设置通道详情属性
	 *
	 * @param enterclose
	 */
	public void setEnterclose(java.lang.String enterclose) {
		this.enterclose = enterclose;
	}
	
	/**
     * 获取场地电话属性
     *
     * @return sitetelephone
     */
	public java.lang.String getSitetelephone() {
		return sitetelephone;
	}
	
	/**
	 * 设置场地电话属性
	 *
	 * @param sitetelephone
	 */
	public void setSitetelephone(java.lang.String sitetelephone) {
		this.sitetelephone = sitetelephone;
	}
	
	/**
     * 获取推荐人姓名属性
     *
     * @return recomname
     */
	public java.lang.String getRecomname() {
		return recomname;
	}
	
	/**
	 * 设置推荐人姓名属性
	 *
	 * @param recomname
	 */
	public void setRecomname(java.lang.String recomname) {
		this.recomname = recomname;
	}
	
	/**
     * 获取推荐人联系方式属性
     *
     * @return recomphone
     */
	public java.lang.String getRecomphone() {
		return recomphone;
	}
	
	/**
	 * 设置推荐人联系方式属性
	 *
	 * @param recomphone
	 */
	public void setRecomphone(java.lang.String recomphone) {
		this.recomphone = recomphone;
	}
	
	/**
     * 获取其他说明属性
     *
     * @return otherexplain
     */
	public java.lang.String getOtherexplain() {
		return otherexplain;
	}
	
	/**
	 * 设置其他说明属性
	 *
	 * @param otherexplain
	 */
	public void setOtherexplain(java.lang.String otherexplain) {
		this.otherexplain = otherexplain;
	}
	
	/**
     * 获取创建日期属性
     *
     * @return createdate
     */
	public java.util.Date getCreatedate() {
		return createdate;
	}
	
	/**
	 * 设置创建日期属性
	 *
	 * @param createdate
	 */
	public void setCreatedate(java.util.Date createdate) {
		this.createdate = createdate;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Recommendsite");
        sb.append("{id=").append(id);
        sb.append(", sitename=").append(sitename);
        sb.append(", siteaddress=").append(siteaddress);
        sb.append(", enterclose=").append(enterclose);
        sb.append(", sitetelephone=").append(sitetelephone);
        sb.append(", recomname=").append(recomname);
        sb.append(", recomphone=").append(recomphone);
        sb.append(", otherexplain=").append(otherexplain);
        sb.append(", createdate=").append(createdate);
		sb.append('}');
        return sb.toString();
    }
    
}