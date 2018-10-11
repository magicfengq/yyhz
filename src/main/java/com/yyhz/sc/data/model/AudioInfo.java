package com.yyhz.sc.data.model;

import java.io.Serializable;
import java.util.Date;

import com.yyhz.sc.base.entity.BaseEntity;



/**
 * audio_info实体表()
 * @author fengq
 * @date 2018-09-13 17:10:10
 * @project app首页后台管理
 */
 @SuppressWarnings("serial")
public class AudioInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // id
	private java.lang.String uuid; // uuid
	private java.lang.String audioName; // name
	private java.lang.Integer status; // name
	private java.lang.String downloadUrl;
	private Date createTime; // 创建时间
	private java.lang.String singerName;
	private java.lang.String duration;
	
	private java.lang.String soneName; // name
	private java.lang.String filePath;
	
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
     * 获取uuid属性
     *
     * @return uuid
     */
	public java.lang.String getUuid() {
		return uuid;
	}
	
	/**
	 * 设置uuid属性
	 *
	 * @param uuid
	 */
	public void setUuid(java.lang.String uuid) {
		this.uuid = uuid;
	}
	
	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getAudioName() {
		return audioName;
	}

	public void setAudioName(java.lang.String audioName) {
		this.audioName = audioName;
	}

	public java.lang.String getDuration() {
		return duration;
	}

	public void setDuration(java.lang.String duration) {
		this.duration = duration;
	}

	public java.lang.String getSingername() {
		return singerName;
	}

	public void setSingername(java.lang.String singerName) {
		this.singerName = singerName;
	}

	public java.lang.String getSoneName() {
		return soneName;
	}

	public void setSoneName(java.lang.String soneName) {
		this.soneName = soneName;
	}

	public java.lang.String getFilePath() {
		return filePath;
	}

	public void setFilePath(java.lang.String filePath) {
		this.filePath = filePath;
	}
}