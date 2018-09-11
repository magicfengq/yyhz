package com.yyhz.sc.controller.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyhz.sc.data.model.AnnouncePicture;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.services.AnnouncePictureService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: AnnouncePictureController 
* @Description: 控制层 
* @author crazyt 
* @date 2017-03-15 09:18:34 
* @Copyright：
 */
@Controller
public class AnnouncePictureController extends BaseController {
	
	@Resource
	private AnnouncePictureService announcePictureService;

	@RequestMapping(value = "system/announcePictureList")
	public String announcePictureList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/announce_picture_list";
	}

	@RequestMapping(value = "system/announcePictureAjaxPage")
	@ResponseBody
	public PageInfo<AnnouncePicture> announcePictureAjaxPage(HttpServletRequest request,
			HttpServletResponse response, AnnouncePicture info, Integer page,
			Integer rows) {
		PageInfo<AnnouncePicture> pageInfo = new PageInfo<AnnouncePicture>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		announcePictureService.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "system/announcePictureAjaxAll")
	@ResponseBody
	public List<AnnouncePicture> announcePictureAjaxAll(HttpServletRequest request,
			HttpServletResponse response, AnnouncePicture info, Integer page,
			Integer rows) {
		List<AnnouncePicture> results= announcePictureService.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/announcePictureAjaxSave")
	@ResponseBody
	public Map<String,Object> announcePictureAjaxSave(HttpServletRequest request,
			HttpServletResponse response, AnnouncePicture info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			result = announcePictureService.insert(info);
			msg = "保存失败！";
		} else {
			result = announcePictureService.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "system/announcePictureAjaxDelete")
	@ResponseBody
	public Map<String,Object> announcePictureAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, AnnouncePicture info) {
		int result = 0;
		try {
			result = announcePictureService.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
