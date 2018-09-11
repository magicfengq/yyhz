package com.yyhz.sc.controller.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyhz.sc.data.model.SystemRoleAuthority;
import com.yyhz.sc.base.page.PageInfo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.services.SystemRoleAuthorityService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: SystemRoleAuthorityController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-02-25 11:05:57 
* @Copyright：
 */
@Controller
public class SystemRoleAuthorityController extends BaseController {
	
	@Resource
	private SystemRoleAuthorityService service;

	@RequestMapping(value = "/system/systemRoleAuthorityList")
	public String systemRoleAuthorityList(HttpServletRequest request, HttpServletResponse response) {
		return "system/system_role_authority_list";
	}

	@RequestMapping(value = "/system/systemRoleAuthorityAjaxPage")
	@ResponseBody
	public JSONObject systemRoleAuthorityAjaxPage(HttpServletRequest request, HttpServletResponse response,
			SystemRoleAuthority info, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return (JSONObject) JSONObject.toJSON(pageInfo);
	}

	@RequestMapping(value = "/system/systemRoleAuthorityAjaxAll")
	@ResponseBody
	public JSONArray systemRoleAuthorityAjaxAll(HttpServletRequest request, HttpServletResponse response,
			SystemRoleAuthority info, Integer page, Integer rows) {
		List<SystemRoleAuthority> results = service.selectAll(info);
		return (JSONArray) JSONArray.toJSON(results);
	}

	@RequestMapping(value = "/system/systemRoleAuthorityAjaxSave")
	@ResponseBody
	public Object systemRoleAuthorityAjaxSave(HttpServletRequest request, HttpServletResponse response,
			SystemRoleAuthority info) {
		int result = 0;
		String msg = "授权失败！";
		try {
			result = service.saveAuthority(info);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return getJsonResult(result, "操作成功", msg);
	}

	@RequestMapping(value = "/system/systemRoleAuthorityAjaxDelete")
	@ResponseBody
	public Object systemRoleAuthorityAjaxDelete(HttpServletRequest request, HttpServletResponse response,
			SystemRoleAuthority info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result, "操作成功", "删除失败！");
	}
}
