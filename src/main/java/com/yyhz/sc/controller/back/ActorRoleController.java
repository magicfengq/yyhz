package com.yyhz.sc.controller.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyhz.sc.data.model.ActorRole;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.services.ActorRoleService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: ActorRoleController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-03-04 17:23:53 
* @Copyright：
 */
@Controller
public class ActorRoleController extends BaseController {
	@Resource
	private ActorRoleService service;

	@RequestMapping(value = "/actorRoleList")
	public String actorRoleList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/actor_role_list";
	}

	@RequestMapping(value = "/actorRoleAjaxPage")
	@ResponseBody
	public PageInfo<ActorRole> actorRoleAjaxPage(HttpServletRequest request,
			HttpServletResponse response, ActorRole info, Integer page,
			Integer rows) {
		PageInfo<ActorRole> pageInfo = new PageInfo<ActorRole>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "/actorRoleAjaxAll")
	@ResponseBody
	public List<ActorRole> actorRoleAjaxAll(HttpServletRequest request,
			HttpServletResponse response, ActorRole info, Integer page,
			Integer rows) {
		List<ActorRole> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "/actorRoleAjaxSave")
	@ResponseBody
	public Map<String,Object> actorRoleAjaxSave(HttpServletRequest request,
			HttpServletResponse response, ActorRole info) {
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

	@RequestMapping(value = "/actorRoleAjaxDelete")
	@ResponseBody
	public Map<String,Object> actorRoleAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, ActorRole info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
