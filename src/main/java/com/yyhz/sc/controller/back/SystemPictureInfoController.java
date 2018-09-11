package com.yyhz.sc.controller.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyhz.sc.data.model.SystemPictureInfo;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.services.SystemPictureInfoService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: SystemPictureInfoController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-02-25 13:17:36 
* @Copyright：
 */
@Controller
public class SystemPictureInfoController extends BaseController {
	@Resource
	private SystemPictureInfoService service;

	@RequestMapping(value = "/systemPictureInfoList")
	public String systemPictureInfoList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/system_picture_info_list";
	}

	@RequestMapping(value = "/systemPictureInfoAjaxPage")
	@ResponseBody
	public PageInfo<SystemPictureInfo> systemPictureInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, SystemPictureInfo info, Integer page,
			Integer rows) {
		PageInfo<SystemPictureInfo> pageInfo = new PageInfo<SystemPictureInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "/systemPictureInfoAjaxAll")
	@ResponseBody
	public List<SystemPictureInfo> systemPictureInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, SystemPictureInfo info, Integer page,
			Integer rows) {
		List<SystemPictureInfo> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "/systemPictureInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> systemPictureInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, SystemPictureInfo info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			result = service.insert(info);
			msg = "保存失败！";
		} else {
			result = service.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "/systemPictureInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> systemPictureInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, SystemPictureInfo info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
