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
import com.yyhz.sc.data.model.ShowComment;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.ShowCommentService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: ShowCommentController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-03-27 16:41:22 
* @Copyright：
 */
@Controller
public class ShowCommentController extends BaseController {
	
	@Resource
	private ShowCommentService showCommentService;
	
	@Resource
	private ActorInfoService actorInfoService;

	@RequestMapping(value = "system/showCommentList")
	public String showCommentList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/show_comment_list";
	}

	@RequestMapping(value = "system/showCommentAjaxPage")
	@ResponseBody
	public PageInfo<ShowComment> showCommentAjaxPage(HttpServletRequest request,
			HttpServletResponse response, ShowComment info, Integer page,
			Integer rows) {
		PageInfo<ShowComment> pageInfo = new PageInfo<ShowComment>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		showCommentService.selectAll(info, pageInfo);
		List<ShowComment> list = pageInfo.getRows();
		if(list == null || list.isEmpty()){
			return pageInfo;
		}
		List<String> actorIdList = new ArrayList<String>();
		for(ShowComment showComment : list){
			actorIdList.add(showComment.getCreater());			
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
		for(ShowComment showComment : list){
			showComment.setActorInfo(actorMap.get(showComment.getCreater()));
		}
		return pageInfo;
	}

	@RequestMapping(value = "system/showCommentAjaxAll")
	@ResponseBody
	public List<ShowComment> showCommentAjaxAll(HttpServletRequest request,
			HttpServletResponse response, ShowComment info, Integer page,
			Integer rows) {
		List<ShowComment> results= showCommentService.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/showCommentAjaxSave")
	@ResponseBody
	public Map<String,Object> showCommentAjaxSave(HttpServletRequest request,
			HttpServletResponse response, ShowComment info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			result = showCommentService.insert(info);
			msg = "保存失败！";
		} else {
			result = showCommentService.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "system/showCommentAjaxDelete")
	@ResponseBody
	public Map<String,Object> showCommentAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, ShowComment info) {
		int result = 0;
		try {
			result = showCommentService.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
