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
import com.yyhz.sc.data.model.ShowPraise;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.ShowPraiseService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: ShowPraiseController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-03-27 16:41:22 
* @Copyright：
 */
@Controller
public class ShowPraiseController extends BaseController {
	
	@Resource
	private ShowPraiseService showPraiseService;
	
	@Resource
	private ActorInfoService actorInfoService;

	@RequestMapping(value = "system/showPraiseList")
	public String showPraiseList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/show_praise_list";
	}

	@RequestMapping(value = "system/showPraiseAjaxPage")
	@ResponseBody
	public PageInfo<ShowPraise> showPraiseAjaxPage(HttpServletRequest request,
			HttpServletResponse response, ShowPraise info, Integer page,
			Integer rows) {
		PageInfo<ShowPraise> pageInfo = new PageInfo<ShowPraise>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		showPraiseService.selectAll(info, pageInfo);
		List<ShowPraise> list = pageInfo.getRows();
		if(list == null || list.isEmpty()){
			return pageInfo;
		}
		List<String> actorIdList = new ArrayList<String>();
		for(ShowPraise showPraise : list){
			actorIdList.add(showPraise.getCreater());			
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
		for(ShowPraise showPraise : list){
			showPraise.setActorInfo(actorMap.get(showPraise.getCreater()));
		}
		return pageInfo;
	}

	@RequestMapping(value = "system/showPraiseAjaxAll")
	@ResponseBody
	public List<ShowPraise> showPraiseAjaxAll(HttpServletRequest request,
			HttpServletResponse response, ShowPraise info, Integer page,
			Integer rows) {
		List<ShowPraise> results= showPraiseService.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/showPraiseAjaxSave")
	@ResponseBody
	public Map<String,Object> showPraiseAjaxSave(HttpServletRequest request,
			HttpServletResponse response, ShowPraise info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			result = showPraiseService.insert(info);
			msg = "保存失败！";
		} else {
			result = showPraiseService.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "system/showPraiseAjaxDelete")
	@ResponseBody
	public Map<String,Object> showPraiseAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, ShowPraise info) {
		int result = 0;
		try {
			result = showPraiseService.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
