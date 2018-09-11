package com.qcloud.vod;

import java.util.Date;

import com.yyhz.utils.DateUtils;

public class QCloudVodUtils {
	private static final String SECRET_ID = "AKID9CxYxAUTcdVCmABLAoHcmkWt55xdo5Wm";
	private static final String SECRET_KEY = "DF5jGc4Z7Xns21oPo24yjKjwPlwwkMn2";
	private static final int THREAD_NUM = 8;
	
	// 上传视频文件到腾讯视频点播，返回url地址。
	public static VodFileInfo UploadVideo(String strFilePath) {
		
		return UploadVideo(strFilePath, 0);
	}
	
	// 上传视频文件到腾讯视频点播，返回url地址。
	public static VodFileInfo UploadVideo(String strFilePath, int classId) {
		
		String fileName = DateUtils.toString(new Date(), "yyyyMMddHHmmssSSS");
		String fileType = strFilePath.substring(strFilePath.lastIndexOf("."));

		VodCall vodReq = new VodCall();
		vodReq.OpenEcho();
		int ret = vodReq.Init(SECRET_ID , SECRET_KEY, VodCall.USAGE_UPLOAD, THREAD_NUM);
		if(ret < 0) {
			return null;
		}
		
		ret = vodReq.SetFileInfo(strFilePath, fileName, fileType, classId);
		if(ret < 0) {
			return null;
		}
		
		ret = vodReq.Upload();
		if(ret < 0) {
			return null;
		}else {
			return new VodFileInfo(vodReq.m_strFileId, vodReq.m_strUrl);
		}
	}
	
	public static void main(String[] args) {
		QCloudVodUtils.UploadVideo("D:/aaa.MP4");
	}
}
