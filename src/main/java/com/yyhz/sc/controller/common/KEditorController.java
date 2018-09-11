package com.yyhz.sc.controller.common;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.data.model.FileInfo;
import com.yyhz.utils.FileTool;
import com.yyhz.utils.stream.config.Configurations;

@Controller
public class KEditorController extends BaseController {

	@RequestMapping(value = "keUpload")
	public void keUpload(HttpServletRequest request, HttpServletResponse response, String model) {
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 文件保存目录路径
		String tsPath = getCurrentTimestampStr();
		String savePath = Configurations.getFileRepository() + "/" + model + "/" + tsPath + "/";
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}

		// 文件保存目录URL
		String saveUrl = request.getContextPath() + "/downFileResult.do?urlPath=" + model + "/" + tsPath + "/";
		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,mp4");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

		// 最大文件大小
		// long maxSize = 1000000;

		response.setContentType("text/html; charset=UTF-8");

		if (!ServletFileUpload.isMultipartContent(request)) {
			out.println(getError("请选择文件。"));
			return;
		}
		// 检查目录
		File uploadDir = new File(savePath);

		if (!uploadDir.isDirectory()) {
			out.println(getError("上传目录不存在。"));
			return;
		}
		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			out.println(getError("上传目录没有写权限。"));
			return;
		}

		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {
			out.println(getError("目录名不正确。"));
			return;
		}
		// 创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile imgFile = multipartRequest.getFile("imgFile");
		FileInfo fileInfo = FileTool.saveFile(imgFile, savePath);
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("url", saveUrl + "/" + fileInfo.getRealName());
		//obj.put("url", "downFileResult.do?urlPath=" + videoFileName);
		out.println(JSON.toJSONString(obj));
		
	}

	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toString();
	}

	private static String getCurrentTimestampStr() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}
}
