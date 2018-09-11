package com.qcloud.vod;

public class VodFileInfo {
	String fileId;
	String fileUrl;
	
	
	public VodFileInfo() {
	}

	public VodFileInfo(String fileId, String fileUrl) {
		super();
		this.fileId = fileId;
		this.fileUrl = fileUrl;
	}
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
}
