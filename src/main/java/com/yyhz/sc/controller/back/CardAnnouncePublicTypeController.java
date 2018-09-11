package com.yyhz.sc.controller.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyhz.sc.data.model.CardAnnouncePublicType;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.services.CardAnnouncePublicTypeService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: CardAnnouncePublicTypeController 
* @Description: 控制层 
* @author crazyt 
* @date 2017-03-12 19:45:32 
* @Copyright：
 */
@Controller
public class CardAnnouncePublicTypeController extends BaseController {
	@Resource
	private CardAnnouncePublicTypeService service;

	@RequestMapping(value = "/cardAnnouncePublicTypeList")
	public String cardAnnouncePublicTypeList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/card_announce_public_type_list";
	}

	@RequestMapping(value = "/cardAnnouncePublicTypeAjaxPage")
	@ResponseBody
	public PageInfo<CardAnnouncePublicType> cardAnnouncePublicTypeAjaxPage(HttpServletRequest request,
			HttpServletResponse response, CardAnnouncePublicType info, Integer page,
			Integer rows) {
		PageInfo<CardAnnouncePublicType> pageInfo = new PageInfo<CardAnnouncePublicType>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "/cardAnnouncePublicTypeAjaxAll")
	@ResponseBody
	public List<CardAnnouncePublicType> cardAnnouncePublicTypeAjaxAll(HttpServletRequest request,
			HttpServletResponse response, CardAnnouncePublicType info, Integer page,
			Integer rows) {
		List<CardAnnouncePublicType> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "/cardAnnouncePublicTypeAjaxSave")
	@ResponseBody
	public Map<String,Object> cardAnnouncePublicTypeAjaxSave(HttpServletRequest request,
			HttpServletResponse response, CardAnnouncePublicType info) {
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

	@RequestMapping(value = "/cardAnnouncePublicTypeAjaxDelete")
	@ResponseBody
	public Map<String,Object> cardAnnouncePublicTypeAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, CardAnnouncePublicType info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
