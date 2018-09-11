package com.yyhz.sc.controller.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yyhz.constant.AppRetCode;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.data.model.SystemPictureInfo;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.utils.stream.config.Configurations;

@Controller
public class AppAccountInfoController extends BaseController{
	
	@Resource
	private ActorInfoService actorInfoService;
	
	@RequestMapping(value = "api/uploadUserHeader")
	public void uploadUserHeader(HttpServletRequest request,HttpServletResponse response,String id, @RequestParam MultipartFile imageFile){
		if(StringUtils.isBlank(id)){
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数id为空", null);
			return;
		}
		if(imageFile == null){
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "上传文件为空", null);
			return;
		}
		
		ActorInfo actorInfo = actorInfoService.selectById(id);
		if(actorInfo == null){
			this.writeJsonObject(response, AppRetCode.ACCOUNT_NOT_EXIST, AppRetCode.ACCOUNT_NOT_EXIST_TEXT, null);
			return;
		}
		
		SystemPictureInfo pInfo = this.uploadFile2("head", imageFile, null);
		if(pInfo != null) {
			Map<String, Object> result = new HashMap<String, Object>();

			actorInfo.setImgUuid(pInfo.getUuid());
			
			int ret = actorInfoService.update(actorInfo);

			if(ret > 0){
				result.put("uuid", pInfo.getUuid());
				result.put("url", Configurations.buildDownloadUrl(pInfo.getUrlPath()));
				this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, result);
			}else{
				this.writeJsonObject(response, AppRetCode.ERROR, "上传文件失败", result);
			}
		} else {
			this.writeJsonObject(response, AppRetCode.ERROR, "上传文件失败", null);
		}
	}
	
	@RequestMapping(value = "api/uploadFile")
	public void uploadFile(HttpServletRequest request, HttpServletResponse response, SystemPictureInfo req, @RequestParam MultipartFile imageFile) {
		if(imageFile == null){
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "上传文件为空", null);
			return;
		}

		Map<String, Object> result = new HashMap<String, Object>();
		
		SystemPictureInfo pInfo = this.uploadFile2("yyhz", imageFile, req);
		if(pInfo != null) {
			result.put("uuid", pInfo.getUuid());
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, result);
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "上传文件失败", result);
		}
	}
	
	@RequestMapping(value = "api/uploadFiles")
	public void uploadFiles(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) MultipartFile[] imageFiles) {
		if(imageFiles == null){
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "上传文件为空", null);
			return;
		}

		List<String> urls = new ArrayList<String>();
		List<SystemPictureInfo> pInfos = this.uploadFiles("yyhz", imageFiles);
		for(SystemPictureInfo pInfo : pInfos) {
			urls.add(pInfo.getUrlPath());
		}
		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, urls);
	}
	
	@RequestMapping(value = "api/deleteFile")
	public void deleteFile(HttpServletRequest request, HttpServletResponse response, SystemPictureInfo req) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(req != null && StringUtils.isNoneBlank(req.getUuid())) {
			this.deleteFile(req);
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, result);
		}else {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数uuid错误！", result);
		}	
	}
}
