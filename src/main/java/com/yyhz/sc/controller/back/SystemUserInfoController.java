package com.yyhz.sc.controller.back;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.data.model.SystemUserInfo;
import com.yyhz.sc.services.SystemUserInfoService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: SystemUserInfoController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-02-25 11:05:57 
* @Copyright：
 */
@Controller
public class SystemUserInfoController extends BaseController {
	
	@Resource
	private SystemUserInfoService service;

	@RequestMapping(value = "/system/systemUserList")
	public String systemUserList(HttpServletRequest request, HttpServletResponse response) {
		return "system/system_user_list";
	}

	@RequestMapping(value = "/system/systemUserAjaxAll")
	@ResponseBody
	public Object systemUserAjaxAll(HttpServletRequest request, HttpServletResponse response, SystemUserInfo info,
			Integer page, Integer rows) {
		PageInfo<SystemUserInfo> pageInfo = new PageInfo<SystemUserInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return JSONObject.toJSON(pageInfo);
	}

	@RequestMapping(value = "/system/systemUserAjaxSave")
	@ResponseBody
	public Object systemUserAjaxSave(HttpServletRequest request, HttpServletResponse response, SystemUserInfo info) {
		int result = 0;
		String msg = "";
		// if (info.getUserPwd() != null && !info.getUserPwd().equals("")) {
		// info.setUserPwd(CipherUtil.generatePassword(info.getUserPwd()));
		// }

		if (info.getId() == null || info.getId().equals("")) {
			SystemUserInfo con = new SystemUserInfo();
			con.setUserId(info.getUserId());
			int count = service.selectCount(con);
			if (count > 0) {
				msg = "账号重复！";
			} else {
				info.setId(UUIDUtil.getUUID());
				result = service.insert(info);
				msg = "保存失败！";
			}

		} else {
			if (info.getStatus() == 1) {
				// info.setIsDelete(0);
			}
			result = service.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功", msg);
	}

	@RequestMapping(value = "/system/systemUserAjaxDelete")
	@ResponseBody
	public Object systemUserAjaxDelete(HttpServletRequest request, HttpServletResponse response, SystemUserInfo info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getJsonResult(result, "操作成功", "删除失败！");
	}

	@RequestMapping(value = "/system/systemUserAjaxUpdate")
	@ResponseBody
	public Object systemUserAjaxUpdate(HttpServletRequest request, HttpServletResponse response, SystemUserInfo info) {
		int result = 0;
		try {
			if (info.getStatus() == 1) {
				// info.setIsDelete(0);
			}
			result = service.update(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result, "操作成功", "操作失败！");
	}
}
