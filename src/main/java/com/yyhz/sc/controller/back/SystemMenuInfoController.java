package com.yyhz.sc.controller.back;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yyhz.constant.SessionConstants;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.data.model.SystemMenuInfo;
import com.yyhz.sc.data.model.SystemUserInfo;
import com.yyhz.sc.services.SystemMenuInfoService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: SystemMenuInfoController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-02-25 11:05:57 
* @Copyright：
 */
@Controller
public class SystemMenuInfoController extends BaseController {
	
	@Resource
	private SystemMenuInfoService systemMenuInfoService;

	@RequestMapping(value = "/system/systemMenuList")
	public String systemMenuList(HttpServletRequest request, HttpServletResponse response) {
		return "system/system_menu_list";
	}

	@RequestMapping(value = "/system/systemMenuAjaxAll")
	@ResponseBody
	public JSONArray systemMenuAjaxAll(HttpServletRequest request, HttpServletResponse response) {
		List<SystemMenuInfo> menus = systemMenuInfoService.selectAllByPid("0");
		System.out.println(JSONArray.toJSON(menus).toString());
		return (JSONArray) JSONArray.toJSON(menus);
	}

	@RequestMapping(value = "/system/systemMenuAjaxAllByRole")
	@ResponseBody
	public JSONArray systemMenuAjaxAllByRole(HttpServletRequest request, HttpServletResponse response,
			SystemMenuInfo info) {
		List<SystemMenuInfo> menus = systemMenuInfoService.selectAllByRole(info);
		System.out.println(JSONArray.toJSON(menus).toString());
		return (JSONArray) JSONArray.toJSON(menus);
	}

	@RequestMapping(value = "/system/systemMenuDrag")
	@ResponseBody
	public String systemMenuDrag(HttpServletRequest request, HttpServletResponse response, String id, String pid,
			String moveType) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String json = "";
		int result = 0;
		try {
			result = systemMenuInfoService.systemMenuDrag(id, pid, moveType);
		} catch (Exception e) {
			// TODO: handle exception
		}
		resultMap.put("result", result);
		json = JSONObject.toJSON(resultMap).toString();
		return json;
	}

	@RequestMapping(value = "/system/systemMenuAjaxSave")
	@ResponseBody
	public JSONObject systemMenuAjaxSave(HttpServletRequest request, HttpServletResponse response, SystemMenuInfo menu) {
		try {

			if (menu.getId() != null && !menu.getId().equals("")) {
				systemMenuInfoService.update(menu);
			} else {
				menu.setId(UUIDUtil.getUUID());
				if (menu.getPid() == null || menu.getPid().equals("")) {
					menu.setPid("0");
				}
				int max = systemMenuInfoService.selectMaxOrderListByPid(menu);
				menu.setOrderList(max + 1);
				systemMenuInfoService.insert(menu);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return (JSONObject) JSONObject.toJSON(menu);
	}

	@RequestMapping(value = "/system/systemMenuAjaxDelete")
	@ResponseBody
	public String systemMenuAjaxDelete(HttpServletRequest request, HttpServletResponse response, SystemMenuInfo menu) {
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int result = 0;
		try {
			result = systemMenuInfoService.delete(menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			resultMap.put("result", true);

		} else {
			resultMap.put("result", false);
		}
		json = JSONObject.toJSON(menu).toString();
		return json;
	}

	@RequestMapping(value = "/system/initSystemMenuTree")
	@ResponseBody
	public JSONArray initSystemMenuTree(HttpServletRequest request, HttpServletResponse response) {

		SystemUserInfo userInfo = (SystemUserInfo) request.getSession().getAttribute(SessionConstants.SESSION_USER);

		if (null == userInfo) {
			return null;
		}
		List<SystemMenuInfo> menus = null;
		SystemMenuInfo menu = new SystemMenuInfo();
		menu.setRoleId(userInfo.getRoleId());
		if (menu.getRoleId().equals("root")) {
			menus = systemMenuInfoService.selectAllByPid("0");
		} else {
			menus = systemMenuInfoService.selectAllByRoleLogin(menu);
		}
		request.getSession(true).setAttribute(SessionConstants.SESSION_PERMISSACT, menus);
		return (JSONArray) JSONArray.toJSON(menus);
	}

}
