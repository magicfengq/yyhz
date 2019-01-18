package com.yyhz.sc.data.model;

import java.io.Serializable;
import java.util.List;

import com.yyhz.sc.base.entity.BaseEntity;

/**
 * show_info实体表()
 * @author lipeng
 * @date 2017-03-22 17:00:09
 * @project 
 */
 @SuppressWarnings("serial")
public class ShowInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String content; // 文字内容
	private java.lang.String publicType; // 发布类型 来源于public_type表，type=5的数据；
	private java.lang.String city; // 定位城市
	private java.lang.Integer commentNum; // 评论数
	private java.lang.Integer praiseNum; // 点赞数
	private java.lang.Integer shareNum; // 分享数
	private java.lang.String creater; // 秀一秀发布人
	private java.util.Date createTime; // 发布时间
	private java.lang.String type; // 秀类型：0图片；1视频
	private java.lang.String imgUuid; // 
	private java.lang.String videoUuid; // 
	private java.lang.String videoPreviewUuid;
	private String actorId;
	private String status;
	private String mobile;
	private String userName;
	private String publicTypeName;
	private List<String> actorIdList;
	private SystemPictureInfo systemVideoInfo;
	private SystemPictureInfo systemVideoPreviewInfo;
	
	private String imgUrls; // 图片url，多个用英文','分隔
	private String videoUrl; // 视频url
	private String videoPreviewUrl; // 视频预览图
	
	public String getVideoPreviewUrl() {
		return videoPreviewUrl;
	}

	public void setVideoPreviewUrl(String videoPreviewUrl) {
		this.videoPreviewUrl = videoPreviewUrl;
	}

	public String getImgUrls() {
		return imgUrls;
	}

	public void setImgUrls(String imgUrls) {
		this.imgUrls = imgUrls;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
	
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
     * 获取文字内容属性
     *
     * @return content
     */
	public java.lang.String getContent() {
		return content;
	}
	
	/**
	 * 设置文字内容属性
	 *
	 * @param content
	 */
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	/**
     * 获取发布类型 来源于public_type表，type=5的数据；属性
     *
     * @return publicType
     */
	public java.lang.String getPublicType() {
		return publicType;
	}
	
	/**
	 * 设置发布类型 来源于public_type表，type=5的数据；属性
	 *
	 * @param publicType
	 */
	public void setPublicType(java.lang.String publicType) {
		this.publicType = publicType;
	}
	
	/**
     * 获取定位城市属性
     *
     * @return city
     */
	public java.lang.String getCity() {
		return city;
	}
	
	/**
	 * 设置定位城市属性
	 *
	 * @param city
	 */
	public void setCity(java.lang.String city) {
		this.city = city;
	}
	
	/**
     * 获取评论数属性
     *
     * @return commentNum
     */
	public java.lang.Integer getCommentNum() {
		return commentNum;
	}
	
	/**
	 * 设置评论数属性
	 *
	 * @param commentNum
	 */
	public void setCommentNum(java.lang.Integer commentNum) {
		this.commentNum = commentNum;
	}
	
	/**
     * 获取点赞数属性
     *
     * @return praiseNum
     */
	public java.lang.Integer getPraiseNum() {
		return praiseNum;
	}
	
	/**
	 * 设置点赞数属性
	 *
	 * @param praiseNum
	 */
	public void setPraiseNum(java.lang.Integer praiseNum) {
		this.praiseNum = praiseNum;
	}
	
	/**
     * 获取分享数属性
     *
     * @return shareNum
     */
	public java.lang.Integer getShareNum() {
		return shareNum;
	}
	
	/**
	 * 设置分享数属性
	 *
	 * @param shareNum
	 */
	public void setShareNum(java.lang.Integer shareNum) {
		this.shareNum = shareNum;
	}
	
	/**
     * 获取秀一秀发布人属性
     *
     * @return creater
     */
	public java.lang.String getCreater() {
		return creater;
	}
	
	/**
	 * 设置秀一秀发布人属性
	 *
	 * @param creater
	 */
	public void setCreater(java.lang.String creater) {
		this.creater = creater;
	}
	
	/**
     * 获取发布时间属性
     *
     * @return createTime
     */
	public java.util.Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置发布时间属性
	 *
	 * @param createTime
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
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
     * 获取属性
     *
     * @return videoUuid
     */
	public java.lang.String getVideoUuid() {
		return videoUuid;
	}
	
	/**
	 * 设置属性
	 *
	 * @param videoUuid
	 */
	public void setVideoUuid(java.lang.String videoUuid) {
		this.videoUuid = videoUuid;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPublicTypeName() {
		return publicTypeName;
	}

	public void setPublicTypeName(String publicTypeName) {
		this.publicTypeName = publicTypeName;
	}

	public List<String> getActorIdList() {
		return actorIdList;
	}

	public void setActorIdList(List<String> actorIdList) {
		this.actorIdList = actorIdList;
	}

	public SystemPictureInfo getSystemVideoInfo() {
		return systemVideoInfo;
	}

	public void setSystemVideoInfo(SystemPictureInfo systemVideoInfo) {
		this.systemVideoInfo = systemVideoInfo;
	}

	public java.lang.String getVideoPreviewUuid() {
		return videoPreviewUuid;
	}

	public void setVideoPreviewUuid(java.lang.String videoPreviewUuid) {
		this.videoPreviewUuid = videoPreviewUuid;
	}

	public SystemPictureInfo getSystemVideoPreviewInfo() {
		return systemVideoPreviewInfo;
	}
	
	public void setSystemVideoPreviewInfo(SystemPictureInfo systemVideoPreviewInfo) {
		this.systemVideoPreviewInfo = systemVideoPreviewInfo;
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
        sb.append("ShowInfo");
        sb.append("{id=").append(id);
        sb.append(", content=").append(content);
        sb.append(", publicType=").append(publicType);
        sb.append(", city=").append(city);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", praiseNum=").append(praiseNum);
        sb.append(", shareNum=").append(shareNum);
        sb.append(", creater=").append(creater);
        sb.append(", createTime=").append(createTime);
        sb.append(", type=").append(type);
        sb.append(", imgUuid=").append(imgUuid);
        sb.append(", videoUuid=").append(videoUuid);
		sb.append('}');
        return sb.toString();
    }

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
    
}