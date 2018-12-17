package com.yyhz.sc.base.controller;

import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.qcloud.vod.QCloudVodUtils;
import com.qcloud.vod.VodFileInfo;
import com.yyhz.constant.SessionConstants;
import com.yyhz.sc.data.model.FileInfo;
import com.yyhz.sc.data.model.SystemPictureInfo;
import com.yyhz.sc.data.model.SystemUserInfo;
import com.yyhz.sc.services.SystemPictureInfoService;
import com.yyhz.utils.DateUtils;
import com.yyhz.utils.FileTool;
import com.yyhz.utils.UUIDUtil;
import com.yyhz.utils.stream.config.Configurations;

@Controller
public class BaseController {

	public final static Integer pageSize = 10;
	
	@Resource
	private SystemPictureInfoService systemPictureInfoService;

	protected Map<String, Object> getJsonResult(Integer result, String msg, String errorMsg) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (result > 0) {
			map.put("success", true);
			map.put("msg", msg);
		} else {
			map.put("success", false);
			map.put("msg", errorMsg);
		}
		return map;
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String handleException(Exception ex, HttpServletRequest request) {
		String errorMessage = StringUtils.EMPTY;
		if (ex instanceof MaxUploadSizeExceededException) {
			errorMessage = "文件应不大于 " + getFileMB(((MaxUploadSizeExceededException) ex).getMaxUploadSize());
		} else {
			ex.printStackTrace();
			errorMessage = "未知错误: " + ex.getMessage();
		}
		return errorMessage;
	}

	private String getFileMB(long byteFile) {
		if (byteFile == 0)
			return "0MB";
		long mb = 1024 * 1024;
		return "" + byteFile / mb + "MB";
	}

	public String encodeParam(String param) {
		try {
			if (StringUtils.isNotBlank(param)) {
				param = URLEncoder.encode(param, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return param;
	}

	public String decodeParam(String param) {
		try {
			if (StringUtils.isNotBlank(param)) {
				param = URLDecoder.decode(param, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return param;
	}

	public void writeJsonObject(HttpServletResponse response, int result, String msg, Object ob) {
		JSONObject jsonobject = new JSONObject();
		jsonobject.put("result", result);
		jsonobject.put("msg", msg);
		if (null != ob) {
			jsonobject.put("data", ob);
		}
		try {
			String json = jsonobject.toString();
			System.out.println(json);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.write(json);// mess为返回到jsp页面的值
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SystemUserInfo getSessionUser(HttpServletRequest request) {
		SystemUserInfo systemUser = null;
		try {
			systemUser = (SystemUserInfo) request.getSession().getAttribute(SessionConstants.SESSION_USER);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return systemUser;
	}

	protected boolean deleteFile(String path) {
		boolean flag = false;
		FileTool.deleteFile(path);
		path = path.substring(0, path.lastIndexOf("/"));
		File dirFile = new File(path);
		// 删除日文件夹
		if (dirFile.listFiles() != null && dirFile.listFiles().length == 0) {
			flag = FileTool.deleteDirectory(path);
		}
		// 删除月文件夹
		if (flag) {
			path = path.substring(0, path.lastIndexOf("/"));
			dirFile = new File(path);
			if (dirFile.listFiles().length == 0) {
				flag = FileTool.deleteDirectory(path);
			}
		}
		// 删除年文件夹
		if (flag) {
			path = path.substring(0, path.lastIndexOf("/"));
			dirFile = new File(path);
			if (dirFile.listFiles().length == 0) {
				flag = FileTool.deleteDirectory(path);
			}
		}
		return flag;
	}
	
	protected boolean deleteFile(SystemPictureInfo pInfo) {
			
		if(pInfo != null && StringUtils.isNotBlank(pInfo.getUuid())){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("uuid", pInfo.getUuid());
			SystemPictureInfo systemPictureInfo = systemPictureInfoService.selectEntity(params);
			if(systemPictureInfo != null){
				String configRepo = Configurations.getFileRepository();
				//删除原图片
				String sourcePath = configRepo + "/" + systemPictureInfo.getUrlPath();
				FileTool.deleteFile(sourcePath);
				
				// 删除数据库记录
				SystemPictureInfo condition = new SystemPictureInfo();
				condition.setId(systemPictureInfo.getId());
				condition.setUuid(systemPictureInfo.getUuid());
				
				systemPictureInfoService.delete(condition);
				
				return true;
			}
		}
		
		return false;
	}

	protected List<SystemPictureInfo> uploadFiles(String modelName, MultipartFile[] imageFiles) {
		List<SystemPictureInfo> pInfos = new ArrayList<SystemPictureInfo>();
		if(imageFiles != null) {
			for(MultipartFile imageFile : imageFiles) {
				SystemPictureInfo pInfo = uploadFile2(modelName, imageFile);
				if(pInfo != null) {
					pInfos.add(pInfo);
				}
			}		
		}
		return pInfos;
	}
	
	protected SystemPictureInfo uploadVideo2QCloud(MultipartFile imageFile) {
		
		String tempPath = Configurations.getFileRepository() + "/temp/";
		FileInfo fileInfo = FileTool.saveFile(imageFile, tempPath);
		String tempFilePath = fileInfo.getRealPath();
		if(tempFilePath == null || "".equals(tempFilePath)) {
			return null;
		}
		
		VodFileInfo vodFileInfo = QCloudVodUtils.UploadVideo(tempFilePath);
		if(vodFileInfo == null) {
			return null;
		}
		
		FileTool.deleteFile(tempFilePath);
		
		SystemPictureInfo systemPicInfo = new SystemPictureInfo();
		systemPicInfo.setId(UUIDUtil.getUUID());
		systemPicInfo.setUuid(UUIDUtil.getUUID());
		systemPicInfo.setUrlPath(vodFileInfo.getFileUrl());
		systemPicInfo.setParams(vodFileInfo.getFileId());
		systemPicInfo.setFsize((int)fileInfo.getFsize());
		systemPicInfo.setCdate(new Date());
		systemPicInfo.setSuffix(fileInfo.getSuffix());
		int count = systemPictureInfoService.insert(systemPicInfo);
		if(count > 0) {
			return systemPicInfo;
		}else {
			return null;
		}

	}
	
	protected SystemPictureInfo uploadImageFile(String modelName, String imgUrl) {
		String configRepo = Configurations.getFileRepository();
		String pathTmp = modelName + "/";
		String path = pathTmp + DateUtils.toString(new Date(), "yyyyMMdd") + "/";
		String realPath = configRepo + "/" + path;
		FileInfo fileInfo = FileTool.saveImageFile(imgUrl, realPath);
		
		if(fileInfo == null) {
			return null;
		}
		
		String urlPath = path + fileInfo.getRealName();
		SystemPictureInfo systemPicInfo = new SystemPictureInfo();
		systemPicInfo.setId(UUIDUtil.getUUID());
		systemPicInfo.setUuid(UUIDUtil.getUUID());
		systemPicInfo.setUrlPath(urlPath);
		systemPicInfo.setFsize((int)fileInfo.getFsize());
		systemPicInfo.setCdate(new Date());
		systemPicInfo.setSuffix(fileInfo.getSuffix());
		int count = systemPictureInfoService.insert(systemPicInfo);
		if(count > 0) {
			return systemPicInfo;
		}else {
			return null;
		}

	}
	
	protected SystemPictureInfo uploadFile2(String modelName, MultipartFile imageFile){
		return uploadFile2(modelName, imageFile, null);
	}

	protected SystemPictureInfo uploadFile2(String modelName, MultipartFile imageFile, SystemPictureInfo pInfo){
		SystemPictureInfo result = null;
		if(pInfo != null && StringUtils.isNotBlank(pInfo.getUuid())){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("uuid", pInfo.getUuid());
			SystemPictureInfo systemPictureInfo = systemPictureInfoService.selectEntity(params);
			if(systemPictureInfo == null){
				//添加图片
				result = uploadAndSave2(modelName,imageFile);
			}else{
				//修改图片
				result = uploadAndUpdate2(modelName,imageFile, systemPictureInfo);
			}
		}else{
			//添加图片
			result = uploadAndSave2(modelName, imageFile);
		}
		
		return result;
	}
	
	private SystemPictureInfo uploadAndSave2(String modelName, MultipartFile multipartFile){
		String configRepo = Configurations.getFileRepository();
		String pathTmp = modelName + "/";
		String path = pathTmp + DateUtils.toString(new Date(), "yyyyMMdd") + "/";
		String realPath = configRepo + "/" + path;
		FileInfo fileInfo = FileTool.saveFile(multipartFile, realPath);
		String urlPath = path + fileInfo.getRealName();
		SystemPictureInfo systemPicInfo = new SystemPictureInfo();
		systemPicInfo.setId(UUIDUtil.getUUID());
		systemPicInfo.setUuid(UUIDUtil.getUUID());
		systemPicInfo.setUrlPath(urlPath);
		systemPicInfo.setFsize((int)fileInfo.getFsize());
		systemPicInfo.setCdate(new Date());
		systemPicInfo.setSuffix(fileInfo.getSuffix());
		systemPicInfo.setName(fileInfo.getFileName());
		int count = systemPictureInfoService.insert(systemPicInfo);
		if(count > 0) {
			return systemPicInfo;
		}else {
			return null;
		}
	}

	private SystemPictureInfo uploadAndUpdate2(String modelName, MultipartFile multipartFile, SystemPictureInfo pInfo){
		String configRepo = Configurations.getFileRepository();
		//删除原图片
		String sourcePath = configRepo + "/" + pInfo.getUrlPath();
		FileTool.deleteFile(sourcePath);
		String pathTmp = modelName + "/";
		String path = pathTmp + DateUtils.toString(new Date(), "yyyyMMdd") + "/";
		String realPath = configRepo + "/" + path;
		FileInfo fileInfo = FileTool.saveFile(multipartFile, realPath);
		String urlPath = path + fileInfo.getRealName();
		SystemPictureInfo systemPicInfo = new SystemPictureInfo();
		systemPicInfo.setId(pInfo.getId());
		systemPicInfo.setUrlPath(urlPath);
		systemPicInfo.setFsize((int)fileInfo.getFsize());
		systemPicInfo.setCdate(new Date());
		systemPicInfo.setSuffix(fileInfo.getSuffix());
		systemPicInfo.setName(fileInfo.getFileName());
		int count = systemPictureInfoService.update(systemPicInfo);
		
		if(count > 0) {
			systemPicInfo.setUuid(pInfo.getUuid());
			return systemPicInfo;
		}else {
			return null;
		}
	}
	
	protected int uploadFile(String modelName,MultipartFile imageFile,Object entity){
		Object getValue = null;
		int result = 0;
		try {
			getValue = getProperty(entity,"imgUuid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(getValue != null && StringUtils.isNotBlank((String)getValue)){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("uuid", getValue);
			SystemPictureInfo systemPictureInfo = systemPictureInfoService.selectEntity(params);
			if(systemPictureInfo == null){
				//添加图片
				result = uplaodAndSave(modelName,imageFile,entity);
			}else{
				//修改图片
				result = uplaodAndUpdate(modelName,imageFile,systemPictureInfo);
			}
		}else{
			//添加图片
			result = uplaodAndSave(modelName,imageFile,entity);
		}
		return result;
	}
	
	private int uplaodAndSave(String modelName,MultipartFile multipartFile,Object entity){
		int result = 0;
		String configRepo = Configurations.getFileRepository();
		String pathTmp = modelName + "/";
		String path = pathTmp + DateUtils.toString(new Date(), "yyyyMMdd") + "/";
		String realPath = configRepo + path;
		FileInfo fileInfo = FileTool.saveFile(multipartFile, realPath);
		String urlPath = path + fileInfo.getRealName();
		SystemPictureInfo systemPicInfo = new SystemPictureInfo();
		systemPicInfo.setId(UUIDUtil.getUUID());
		systemPicInfo.setUuid(UUIDUtil.getUUID());
		systemPicInfo.setUrlPath(urlPath);
		systemPicInfo.setFsize((int)fileInfo.getFsize());
		systemPicInfo.setCdate(new Date());
		systemPicInfo.setSuffix(fileInfo.getSuffix());
		systemPicInfo.setName(fileInfo.getFileName());
		result = systemPictureInfoService.insert(systemPicInfo);
		try {
			setProperty(entity, systemPicInfo.getUuid(), "imgUuid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private int uplaodAndUpdate(String modelName,MultipartFile multipartFile,SystemPictureInfo systemPictureInfo){
		String configRepo = Configurations.getFileRepository();
		//删除原图片
		String sourcePath = configRepo + systemPictureInfo.getUrlPath();
		FileTool.deleteFile(sourcePath);
		String pathTmp = modelName + "/";
		String path = pathTmp + DateUtils.toString(new Date(), "yyyyMMdd") + "/";
		String realPath = configRepo + path;
		FileInfo fileInfo = FileTool.saveFile(multipartFile, realPath);
		String urlPath = path + fileInfo.getRealName();
		SystemPictureInfo systemPicInfo = new SystemPictureInfo();
		systemPicInfo.setId(systemPictureInfo.getId());
		systemPicInfo.setUrlPath(urlPath);
		systemPicInfo.setFsize((int)fileInfo.getFsize());
		systemPicInfo.setCdate(new Date());
		systemPicInfo.setSuffix(fileInfo.getSuffix());
		systemPicInfo.setName(fileInfo.getFileName());
		return systemPictureInfoService.update(systemPicInfo);
	}
	


	
	private String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {  
            return null;
        }  
        int startIndex = 0;  
        if (fieldName.charAt(0) == '_')  
            startIndex = 1;  
        return "set"  
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()  
                + fieldName.substring(startIndex + 1);  
    } 
    
    private String parGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {  
            return null;
        }  
        int startIndex = 0;  
        if (fieldName.charAt(0) == '_')  
            startIndex = 1;  
        return "get"  
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()  
                + fieldName.substring(startIndex + 1);  
    }
    
    private Object getProperty(Object entity,String propName) throws Exception{
    	Field imgField = entity.getClass().getDeclaredField(propName);
		String fieldGetName = parGetName(imgField.getName());
		Method fieldGetMet = entity.getClass().getMethod(fieldGetName);
		return fieldGetMet.invoke(entity);
    }
    
    private void setProperty(Object entity,String value,String propName) throws Exception{
		Field field = entity.getClass().getDeclaredField(propName);        				
		String fieldSetName = parSetName(field.getName());
		Method fieldSetMet = entity.getClass().getMethod(fieldSetName,field.getType());
		fieldSetMet.invoke(entity, value);
    }
    protected SystemPictureInfo uploadBase64Img(String modelName,String base64ImgStr) {
		
    	String configRepo = Configurations.getFileRepository();
		String pathTmp = modelName + "/";
		String path = pathTmp + DateUtils.toString(new Date(), "yyyyMMdd") + "/";
		String realPath = configRepo + "/" + path;
		FileInfo fileInfo = FileTool.saveBase64File(base64ImgStr, realPath);
		String urlPath = path + fileInfo.getRealName();
		SystemPictureInfo systemPicInfo = new SystemPictureInfo();
		systemPicInfo.setId(UUIDUtil.getUUID());
		systemPicInfo.setUuid(UUIDUtil.getUUID());
		systemPicInfo.setUrlPath(urlPath);
		systemPicInfo.setFsize((int)fileInfo.getFsize());
		systemPicInfo.setCdate(new Date());
		systemPicInfo.setSuffix(fileInfo.getSuffix());
		systemPicInfo.setName(fileInfo.getFileName());
		int count = systemPictureInfoService.insert(systemPicInfo);
		if(count > 0) {
			return systemPicInfo;
		}else {
			return null;
		}

	}
}
