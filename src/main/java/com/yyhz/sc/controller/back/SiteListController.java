package com.yyhz.sc.controller.back;

import java.util.List;
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
import com.yyhz.sc.data.model.SiteList;
import com.yyhz.sc.services.SiteListService;
import com.yyhz.utils.stream.util.StreamVO;

/**
 * 
* @ClassName: SiteListController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-03-08 21:49:09 
* @Copyright：
 */
@Controller
public class SiteListController extends BaseController {
	
	@Resource
	private SiteListService service;

	@RequestMapping(value = "back/siteList")
	public String siteList(HttpServletRequest request,
			HttpServletResponse response) {
		return "back/site/site_list";
	}
	
	@RequestMapping(value = "back/siteRefereeList")
	public String siteRefereeList(HttpServletRequest request,
			HttpServletResponse response) {
		return "back/site/site_referee_list";
	}
	
	@RequestMapping(value = "back/siteRefereeDetail")
	public String siteRefereeDetail(HttpServletRequest request,
			HttpServletResponse response,String id) {
		
		SiteList info = service.selectById(id);
		
		request.setAttribute("info", info);
		return "back/site/site_referee_detail";
	}
	

	@RequestMapping(value = "back/siteListAjaxPage")
	@ResponseBody
	public PageInfo<SiteList> siteListAjaxPage(HttpServletRequest request,
			HttpServletResponse response, SiteList info, Integer page,
			Integer rows) {
		PageInfo<SiteList> pageInfo = new PageInfo<SiteList>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "back/siteListAjaxAll")
	@ResponseBody
	public List<SiteList> siteListAjaxAll(HttpServletRequest request,
			HttpServletResponse response, SiteList info, Integer page,
			Integer rows) {
		List<SiteList> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "back/siteListAjaxSave")
	@ResponseBody
	public Map<String,Object> siteListAjaxSave(HttpServletRequest request,
			HttpServletResponse response, SiteList info,StreamVO streamVO,String operType) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			result = service.insertWithImage(info,streamVO);
			msg = "保存失败！";
		} else {
			//根据opertyp判断是否需要上传
			if(StringUtils.isBlank(operType)){
				result = service.update(info);
			}else{
				result = service.updateWithImage(info,streamVO);
			}
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "back/siteListAjaxDelete")
	@ResponseBody
	public Map<String,Object> siteListAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, SiteList info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
