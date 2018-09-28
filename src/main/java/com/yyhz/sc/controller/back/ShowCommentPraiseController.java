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
import com.yyhz.sc.data.model.ShowCommentPraise;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.ShowCommentPraiseService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: ShowCommentPraiseController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-03-27 16:41:22 
* @Copyright：
 */
@Controller
public class ShowCommentPraiseController extends BaseController {
	
	@Resource
	private ShowCommentPraiseService showCommentPraiseService;
	
	@Resource
	private ActorInfoService actorInfoService;

	@RequestMapping(value = "system/showCommentPraiseList")
	public String showCommentPraiseList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/show_comment_praise_list";
	}

	@RequestMapping(value = "system/showCommentPraiseAjaxPage")
	@ResponseBody
	public PageInfo<ShowCommentPraise> showCommentPraiseAjaxPage(HttpServletRequest request,
			HttpServletResponse response, ShowCommentPraise info, Integer page,
			Integer rows) {
		PageInfo<ShowCommentPraise> pageInfo = new PageInfo<ShowCommentPraise>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		showCommentPraiseService.selectAll(info, pageInfo);
		List<ShowCommentPraise> list = pageInfo.getRows();
		if(list == null || list.isEmpty()){
			return pageInfo;
		}
		List<String> actorIdList = new ArrayList<String>();
		for(ShowCommentPraise showCommentPraise : list){
			actorIdList.add(showCommentPraise.getCreater());			
		}
		
		//发布账号
		List<ActorInfo> actorList = actorInfoService.selectByIds(actorIdList);
		if(actorList == null || actorList.isEmpty()){
			return  pageInfo;
		}
		Map<String,ActorInfo> actorMap = new HashMap<String,ActorInfo>();
		for(ActorInfo actor : actorList){
			actorMap.put(actor.getId(), actor);
		}
		for(ShowCommentPraise showCommentPraise : list){
			showCommentPraise.setActorInfo(actorMap.get(showCommentPraise.getCreater()));
		}
		return pageInfo;
	}

	@RequestMapping(value = "system/showCommentPraiseAjaxAll")
	@ResponseBody
	public List<ShowCommentPraise> showCommentPraiseAjaxAll(HttpServletRequest request,
			HttpServletResponse response, ShowCommentPraise info, Integer page,
			Integer rows) {
		List<ShowCommentPraise> results= showCommentPraiseService.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/showCommentPraiseAjaxSave")
	@ResponseBody
	public Map<String,Object> showCommentPraiseAjaxSave(HttpServletRequest request,
			HttpServletResponse response, ShowCommentPraise info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			result = showCommentPraiseService.insert(info);
			msg = "保存失败！";
		} else {
			result = showCommentPraiseService.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "system/showCommentPraiseAjaxDelete")
	@ResponseBody
	public Map<String,Object> showCommentPraiseAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, ShowCommentPraise info) {
		int result = 0;
		try {
			result = showCommentPraiseService.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
