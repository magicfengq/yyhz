package com.yyhz.sc.controller.back;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.data.model.SiteInfo;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.SiteInfoService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: SiteInfoController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-03-08 21:49:09 
* @Copyright：
 */
@Controller
public class SiteInfoController extends BaseController {
	
	@Resource
	private SiteInfoService service;
	
	@Resource
	private ActorInfoService actorInfoService;

	@RequestMapping(value = "back/siteInfo")
	public String siteInfo(HttpServletRequest request,
			HttpServletResponse response) {
		return "back/site_info";
	}

	@RequestMapping(value = "back/siteInfoAjaxPage")
	@ResponseBody
	public PageInfo<SiteInfo> siteInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, SiteInfo info, Integer page,
			Integer rows) {
		PageInfo<SiteInfo> pageInfo = new PageInfo<SiteInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		List<SiteInfo> siteInfoList = pageInfo.getRows();
		if(siteInfoList == null || siteInfoList.isEmpty()){
			return pageInfo;
		}
		List<String> actorIdList = new ArrayList<String>();
		for(SiteInfo siteInfo : siteInfoList){
			String actorId = siteInfo.getCreater();
			actorIdList.add(actorId);
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("actorIdList", actorIdList);
		List<ActorInfo> actorInfoList = actorInfoService.selectByIds(actorIdList);
		if(actorInfoList == null || actorInfoList.isEmpty()){
			return pageInfo;
		}
		Map<String,ActorInfo> actorInfoMap = new HashMap<String,ActorInfo>();
		for(ActorInfo actorInfo : actorInfoList){
			actorInfoMap.put(actorInfo.getId(), actorInfo);
		}
		for(SiteInfo siteInfo : siteInfoList){
			ActorInfo actorInfo = actorInfoMap.get(siteInfo.getCreater());
			siteInfo.setCreaterMobile(actorInfo.getMobile());
			siteInfo.setCreaterName(actorInfo.getName());
		}
		return pageInfo;
	}

	@RequestMapping(value = "/siteInfoAjaxAll")
	@ResponseBody
	public List<SiteInfo> siteInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, SiteInfo info, Integer page,
			Integer rows) {
		List<SiteInfo> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "/siteInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> siteInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, SiteInfo info) {
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

	@RequestMapping(value = "back/siteInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> siteInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, SiteInfo info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
