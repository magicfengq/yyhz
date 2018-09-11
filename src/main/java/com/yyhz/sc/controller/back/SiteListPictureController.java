package com.yyhz.sc.controller.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyhz.sc.data.model.SiteListPicture;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.services.SiteListPictureService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: SiteListPictureController 
* @Description: 控制层 
* @author crazyt 
* @date 2017-03-22 23:06:03 
* @Copyright：
 */
@Controller
public class SiteListPictureController extends BaseController {
	
	@Resource
	private SiteListPictureService listPictureService;

	@RequestMapping(value = "system/siteListPictureList")
	public String siteListPictureList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/site_list_picture_list";
	}

	@RequestMapping(value = "system/siteListPictureAjaxPage")
	@ResponseBody
	public PageInfo<SiteListPicture> siteListPictureAjaxPage(HttpServletRequest request,
			HttpServletResponse response, SiteListPicture info, Integer page,
			Integer rows) {
		PageInfo<SiteListPicture> pageInfo = new PageInfo<SiteListPicture>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		listPictureService.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "system/siteListPictureAjaxAll")
	@ResponseBody
	public List<SiteListPicture> siteListPictureAjaxAll(HttpServletRequest request,
			HttpServletResponse response, SiteListPicture info, Integer page,
			Integer rows) {
		List<SiteListPicture> results = listPictureService.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/siteListPictureAjaxSave")
	@ResponseBody
	public Map<String,Object> siteListPictureAjaxSave(HttpServletRequest request,
			HttpServletResponse response, SiteListPicture info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			result = listPictureService.insert(info);
			msg = "保存失败！";
		} else {
			result = listPictureService.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "system/siteListPictureAjaxDelete")
	@ResponseBody
	public Map<String,Object> siteListPictureAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, SiteListPicture info) {
		int result = 0;
		try {
			result = listPictureService.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
