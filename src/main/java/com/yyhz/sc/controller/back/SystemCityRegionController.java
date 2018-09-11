package com.yyhz.sc.controller.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyhz.sc.data.model.SystemCityRegion;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.services.SystemCityRegionService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: SystemCityRegionController 
* @Description: 控制层 
* @author crazyt 
* @date 2017-03-20 08:21:05 
* @Copyright：
 */
@Controller
public class SystemCityRegionController extends BaseController {
	@Resource
	private SystemCityRegionService service;

	@RequestMapping(value = "/systemCityRegionList")
	public String systemCityRegionList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/system_city_region_list";
	}

	@RequestMapping(value = "/systemCityRegionAjaxPage")
	@ResponseBody
	public PageInfo<SystemCityRegion> systemCityRegionAjaxPage(HttpServletRequest request,
			HttpServletResponse response, SystemCityRegion info, Integer page,
			Integer rows) {
		PageInfo<SystemCityRegion> pageInfo = new PageInfo<SystemCityRegion>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "/systemCityRegionAjaxAll")
	@ResponseBody
	public List<SystemCityRegion> systemCityRegionAjaxAll(HttpServletRequest request,
			HttpServletResponse response, SystemCityRegion info, Integer page,
			Integer rows) {
		List<SystemCityRegion> results= service.selectAll(info);
		return results; 
	}
}
