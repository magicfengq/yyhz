package com.yyhz.sc.controller.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyhz.sc.data.model.CardPicture;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.services.CardPictureService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: CardPictureController 
* @Description: 控制层 
* @author crazyt 
* @date 2017-03-15 15:25:12 
* @Copyright：
 */
@Controller
public class CardPictureController extends BaseController {
	
	@Resource
	private CardPictureService cardPictureService;

	@RequestMapping(value = "system/cardPictureList")
	public String cardPictureList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/card_picture_list";
	}

	@RequestMapping(value = "system/cardPictureAjaxPage")
	@ResponseBody
	public PageInfo<CardPicture> cardPictureAjaxPage(HttpServletRequest request,
			HttpServletResponse response, CardPicture info, Integer page,
			Integer rows) {
		PageInfo<CardPicture> pageInfo = new PageInfo<CardPicture>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		cardPictureService.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "system/cardPictureAjaxAll")
	@ResponseBody
	public List<CardPicture> cardPictureAjaxAll(HttpServletRequest request,
			HttpServletResponse response, CardPicture info) {
		List<CardPicture> results= cardPictureService.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/cardPictureAjaxSave")
	@ResponseBody
	public Map<String,Object> cardPictureAjaxSave(HttpServletRequest request,
			HttpServletResponse response, CardPicture info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			result = cardPictureService.insert(info);
			msg = "保存失败！";
		} else {
			result = cardPictureService.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "system/cardPictureAjaxDelete")
	@ResponseBody
	public Map<String,Object> cardPictureAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, CardPicture info) {
		int result = 0;
		try {
			result = cardPictureService.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
