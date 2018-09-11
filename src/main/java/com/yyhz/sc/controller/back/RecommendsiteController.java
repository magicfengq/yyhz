package com.yyhz.sc.controller.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyhz.sc.data.model.Recommendsite;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.services.RecommendsiteService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: RecommendsiteController 
* @Description: 控制层 
* @author crazyt 
* @date 2017-03-21 20:39:11 
* @Copyright：
 */
@Controller
public class RecommendsiteController extends BaseController {
	@Resource
	private RecommendsiteService service;

	@RequestMapping(value = "/recommendsiteList")
	public String recommendsiteList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/recommendsite_list";
	}

	@RequestMapping(value = "/recommendsiteAjaxPage")
	@ResponseBody
	public PageInfo<Recommendsite> recommendsiteAjaxPage(HttpServletRequest request,
			HttpServletResponse response, Recommendsite info, Integer page,
			Integer rows) {
		PageInfo<Recommendsite> pageInfo = new PageInfo<Recommendsite>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "/recommendsiteAjaxAll")
	@ResponseBody
	public List<Recommendsite> recommendsiteAjaxAll(HttpServletRequest request,
			HttpServletResponse response, Recommendsite info, Integer page,
			Integer rows) {
		List<Recommendsite> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "/recommendsiteAjaxSave")
	@ResponseBody
	public Map<String,Object> recommendsiteAjaxSave(HttpServletRequest request,
			HttpServletResponse response, Recommendsite info) {
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

	@RequestMapping(value = "/recommendsiteAjaxDelete")
	@ResponseBody
	public Map<String,Object> recommendsiteAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, Recommendsite info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
