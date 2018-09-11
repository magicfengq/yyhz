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
import com.yyhz.sc.data.model.ActorComment;
import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.services.ActorCommentService;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: ActorCommentController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-03-19 15:32:34
* @Copyright：
 */
@Controller
public class ActorCommentController extends BaseController {
	
	@Resource
	private ActorCommentService actorCommentService;
	
	@Resource
	private ActorInfoService actorInfoService;

	@RequestMapping(value = "system/actorCommentList")
	public String actorCommentList(HttpServletRequest request,
			HttpServletResponse response) {
		return "back/actor_comment_list";
	}

	@RequestMapping(value = "system/actorCommentAjaxPage")
	@ResponseBody
	public PageInfo<ActorComment> actorCommentAjaxPage(HttpServletRequest request,
			HttpServletResponse response, ActorComment info, Integer page,
			Integer rows) {
		PageInfo<ActorComment> pageInfo = new PageInfo<ActorComment>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setStatus(0);
		actorCommentService.selectAll(info, pageInfo);
		return pageInfo;
	}
	
	@RequestMapping(value = "system/actorCommentAjaxPageForUser")
	@ResponseBody
	public PageInfo<ActorComment> actorCommentAjaxPageForUser(HttpServletRequest request,
			HttpServletResponse response, ActorComment info, Integer page,
			Integer rows) {
		PageInfo<ActorComment> pageInfo = new PageInfo<ActorComment>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setStatus(0);
		info.setSort("createTime");
		info.setOrder("desc");
		actorCommentService.selectAll(info, pageInfo);
		List<ActorComment> list = pageInfo.getRows();
		if(list == null || list.isEmpty()){
			return pageInfo;
		}
		List<String> actorIdList = new ArrayList<String>();
		for(ActorComment comment : list){
			actorIdList.add(comment.getActorId());
		}
		List<ActorInfo> actorList = actorInfoService.selectByIds(actorIdList);
		if(actorList == null || actorList.isEmpty()){
			return pageInfo;
		}
		Map<String,ActorInfo> actorMap = new HashMap<String,ActorInfo>();		
		for(ActorInfo actorInfo : actorList){
			actorMap.put(actorInfo.getId(), actorInfo);
		}
		for(ActorComment comment : list){
			comment.setActorInfo(actorMap.get(comment.getActorId()));
		}			
		return pageInfo;
	}

	@RequestMapping(value = "system/actorCommentAjaxAll")
	@ResponseBody
	public List<ActorComment> actorCommentAjaxAll(HttpServletRequest request,
			HttpServletResponse response, ActorComment info, Integer page,
			Integer rows) {
		List<ActorComment> results= actorCommentService.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/actorCommentAjaxSave")
	@ResponseBody
	public Map<String,Object> actorCommentAjaxSave(HttpServletRequest request,
			HttpServletResponse response, ActorComment info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			result = actorCommentService.insert(info);
			msg = "保存失败！";
		} else {
			result = actorCommentService.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "system/actorCommentAjaxDelete")
	@ResponseBody
	public Map<String,Object> actorCommentAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, ActorComment info) {
		int result = 0;
		try {
			result = actorCommentService.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
