package com.yyhz.sc.controller.back;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.data.model.ContentReportInfo;
import com.yyhz.sc.services.ContentReportInfoService;
import com.yyhz.utils.UUIDUtil;
/**
 * 
* @ClassName: ContentReportInfoController 
* @Description: app首页后台管理控制层 
* @author fengq 
* @date 2019-01-22 15:46:01 
* @Copyright：app首页后台管理
 */
@Controller
public class ContentReportInfoController extends BaseController {
	@Resource
	private ContentReportInfoService service;

	/**
	 * 列表页面跳转
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/back/contentReportInfoList")
	public String contentReportInfoList(HttpServletRequest request,
			HttpServletResponse response) {
		return "back/contentReport/content_report_info_list";
	}

	/**
	 * 分页
	 * @param request
	 * @param response
	 * @param info
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/back/contentReportInfoAjaxPage")
	@ResponseBody
	public Object contentReportInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, ContentReportInfo info, Integer page,
			Integer rows) {
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return JSONObject.toJSON(pageInfo);
	}

	/**
	 * 查询所有
	 * @param request
	 * @param response
	 * @param info
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/back/contentReportInfoAjaxAll")
	@ResponseBody
	public Object contentReportInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, ContentReportInfo info, Integer page,
			Integer rows) {
		List<ContentReportInfo> results= service.selectAll(info);
		return JSONObject.toJSON(results); 
	}
	
	/**
	 * 保存
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/back/contentReportInfoAjaxSave")
	@ResponseBody
	public Object contentReportInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, ContentReportInfo info) {
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

	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/back/contentReportInfoAjaxDelete")
	@ResponseBody
	public Object contentReportInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, ContentReportInfo info) {
		int result = 0;
		if (info.getId() == null) {
			return getJsonResult(result,"操作成功", "操作失败,ID为空！");
		}
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "操作失败！");
	}
}
