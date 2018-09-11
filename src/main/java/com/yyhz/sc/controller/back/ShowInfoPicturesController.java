package com.yyhz.sc.controller.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyhz.sc.data.model.ShowInfoPictures;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.services.ShowInfoPicturesService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: ShowInfoPicturesController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-03-25 12:03:45 
* @Copyright：
 */
@Controller
public class ShowInfoPicturesController extends BaseController {
	@Resource
	private ShowInfoPicturesService service;

	@RequestMapping(value = "/showInfoPicturesList")
	public String showInfoPicturesList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/show_info_pictures_list";
	}

	@RequestMapping(value = "/showInfoPicturesAjaxPage")
	@ResponseBody
	public PageInfo<ShowInfoPictures> showInfoPicturesAjaxPage(HttpServletRequest request,
			HttpServletResponse response, ShowInfoPictures info, Integer page,
			Integer rows) {
		PageInfo<ShowInfoPictures> pageInfo = new PageInfo<ShowInfoPictures>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "/showInfoPicturesAjaxAll")
	@ResponseBody
	public List<ShowInfoPictures> showInfoPicturesAjaxAll(HttpServletRequest request,
			HttpServletResponse response, ShowInfoPictures info, Integer page,
			Integer rows) {
		List<ShowInfoPictures> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "/showInfoPicturesAjaxSave")
	@ResponseBody
	public Map<String,Object> showInfoPicturesAjaxSave(HttpServletRequest request,
			HttpServletResponse response, ShowInfoPictures info) {
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

	@RequestMapping(value = "/showInfoPicturesAjaxDelete")
	@ResponseBody
	public Map<String,Object> showInfoPicturesAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, ShowInfoPictures info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
