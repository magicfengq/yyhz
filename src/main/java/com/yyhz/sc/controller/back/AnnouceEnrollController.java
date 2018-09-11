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
import com.yyhz.sc.data.model.AnnouceEnroll;
import com.yyhz.sc.data.model.AnnounceInfo;
import com.yyhz.sc.services.AnnouceEnrollService;
import com.yyhz.sc.services.AnnounceInfoService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: AnnouceEnrollController 
* @Description: 控制层 
* @author crazyt 
* @date 2017-03-13 10:41:58 
* @Copyright：
 */
@Controller
public class AnnouceEnrollController extends BaseController {
	
	@Resource
	private AnnouceEnrollService annouceEnrollService;
	
	@Resource
	private AnnounceInfoService announceInfoService;
	
	@RequestMapping(value = "system/annouceEnrollList")
	public String annouceEnrollList(HttpServletRequest request,HttpServletResponse response) {
		return "back/annouce_enroll_list";
	}

	@RequestMapping(value = "system/annouceEnrollAjaxPage")
	@ResponseBody
	public PageInfo<AnnouceEnroll> annouceEnrollAjaxPage(HttpServletRequest request,
			HttpServletResponse response, AnnouceEnroll info, Integer page,
			Integer rows) {
		PageInfo<AnnouceEnroll> pageInfo = new PageInfo<AnnouceEnroll>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		annouceEnrollService.selectAll(info, pageInfo);
		return pageInfo;
	}
	
	@RequestMapping(value = "system/annouceEnrollAjaxPageForUser")
	@ResponseBody
	public PageInfo<AnnouceEnroll> annouceEnrollAjaxPageForUser(HttpServletRequest request,
			HttpServletResponse response, AnnouceEnroll info, Integer page,
			Integer rows) {
		PageInfo<AnnouceEnroll> pageInfo = new PageInfo<AnnouceEnroll>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setSort("createTime");
		info.setOrder("desc");
		info.setEnrollStatus(0);
		annouceEnrollService.selectAll(info, pageInfo);
		List<AnnouceEnroll> list =  pageInfo.getRows();
		if(list == null || list.isEmpty()){
			return pageInfo;
		}
		List<String> annouceIdList = new ArrayList<String>();
		for(AnnouceEnroll annouceEnroll : list){
			annouceIdList.add(annouceEnroll.getAnnounceId());
		}
		List<AnnounceInfo> annouceList = announceInfoService.selectByIds(annouceIdList);
		if(annouceList == null || annouceList.isEmpty()){ 
			return pageInfo;
		}
		Map<String,AnnounceInfo> annouceMap = new HashMap<String,AnnounceInfo>();
		for(AnnounceInfo announceInfo : annouceList){
			annouceMap.put(announceInfo.getId(), announceInfo);
		}
		for(AnnouceEnroll annouceEnroll : list){
			AnnounceInfo announceInfo = annouceMap.get(annouceEnroll.getAnnounceId());
			annouceEnroll.setTitle(announceInfo.getTitle());
			annouceEnroll.setType(announceInfo.getType());
		}		
		return pageInfo;
	}
	
	@RequestMapping(value = "system/annouceEnrollAjaxSave")
	@ResponseBody
	public Map<String,Object> annouceEnrollAjaxSave(HttpServletRequest request,
			HttpServletResponse response, AnnouceEnroll info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			result = annouceEnrollService.insert(info);
			msg = "保存失败！";
		} else {
			result = annouceEnrollService.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "system/annouceEnrollAjaxDelete")
	@ResponseBody
	public Map<String,Object> annouceEnrollAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, AnnouceEnroll info) {
		int result = 0;
		try {
			result = annouceEnrollService.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
