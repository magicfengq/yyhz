package com.yyhz.sc.controller.back;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.data.model.RoleInfo;
import com.yyhz.sc.services.RoleInfoService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: RoleInfoController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-03-04 17:23:53 
* @Copyright：
 */
@Controller
public class RoleInfoController extends BaseController {
	
	@Resource
	private RoleInfoService roleInfoService;

	@RequestMapping(value = "system/roleInfoList")
	public String roleInfoList(HttpServletRequest request,HttpServletResponse response) {
		return "back/role_info_list";
	}
	
	@RequestMapping(value = "system/roleInfoAjaxPage")
	@ResponseBody
	public PageInfo<RoleInfo> roleInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, RoleInfo info, Integer page,
			Integer rows) {
		PageInfo<RoleInfo> pageInfo = new PageInfo<RoleInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setStatus(0);
		/*if("id".equals(info.getSort()) || StringUtils.isBlank(info.getSort())){
			info.setSort("createTime");
			info.setOrder("asc");
		}*/
		info.setSort("power");
		info.setOrder("desc");
		roleInfoService.selectAll(info, pageInfo);
		return pageInfo;
	}
	
	@RequestMapping(value = "system/roleInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> roleInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, RoleInfo info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			info.setCreateTime(new Date());
			info.setPower(0);
			result = roleInfoService.insert(info);
			msg = "保存失败！";
		} else {
			result = roleInfoService.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}
	
	@RequestMapping(value = "system/roleInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> roleInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, RoleInfo info) {
		int result = 0;
		try {
			info.setStatus(1);
			result = roleInfoService.update(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
