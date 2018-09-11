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
import com.yyhz.sc.data.model.ActorRole;
import com.yyhz.sc.data.model.RoleInfo;
import com.yyhz.sc.services.ActorCommentService;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.ActorRoleService;
import com.yyhz.sc.services.RoleInfoService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: ActorInfoController 
* @Description: 控制层 
* @author crazyt 
* @date 2017-03-01 14:48:26 
* @Copyright：
 */
@Controller
public class ActorInfoController extends BaseController {
	
	@Resource
	private ActorInfoService actorInfoService;
	
	@Resource
	private ActorRoleService actorRoleService;
	
	@Resource
	private RoleInfoService roleInfoService;
	
	@Resource 
	private ActorCommentService actorCommentService;

	@RequestMapping(value = "system/actorInfoList")
	public String actorInfoList(HttpServletRequest request,HttpServletResponse response) {
		return "back/actor/actor_info_list";
	}
	
	@RequestMapping(value = "system/actorInfoDetail")
	public String actorInfoDetail(HttpServletRequest request,HttpServletResponse response,String id) {
		request.setAttribute("id", id);	
		return "back/actor/actor_info_detail";
	}
	
	@RequestMapping(value = "system/actorInfoBasic")
	public String actorInfoBasic(HttpServletRequest request,HttpServletResponse response,String id) {
		request.setAttribute("id", id);
		ActorInfo actorInfo = actorInfoService.selectById(id);
		
		float avgScore = actorCommentService.getAvgScore(id);
		if(avgScore == 0.0f){
			actorInfo.setAvgScore(null);
		}else{
			actorInfo.setAvgScore(avgScore);
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("actorId", id);
		List<ActorRole> arList = actorRoleService.selectAll(params);
		if(arList == null || arList.isEmpty()){
			request.setAttribute("actorInfo", actorInfo);
			return "back/actor/actor_info_basic";
		}
		List<String> roleIdList = new ArrayList<String>();
		for(ActorRole actorRole : arList){
			roleIdList.add(actorRole.getRoleId());
		}
		List<RoleInfo> roleList = roleInfoService.selectByIds(roleIdList);
		if(roleList == null || roleList.isEmpty()){
			request.setAttribute("actorInfo", actorInfo);
			return "back/actor/actor_info_basic";
		}
		StringBuffer roleNames = new StringBuffer("");
		for(RoleInfo roleInfo : roleList){
			roleNames.append(roleInfo.getName()).append(",");
		}
		if(!"".equals(roleNames.toString())){
			roleNames.deleteCharAt(roleNames.length()-1);
			actorInfo.setRoleName(roleNames.toString());
		}
		request.setAttribute("actorInfo", actorInfo);
		return "back/actor/actor_info_basic";
	}
	
	@RequestMapping(value = "system/actorInfoPersonal")
	public String actorInfoPersonal(HttpServletRequest request,HttpServletResponse response,String id) {
		request.setAttribute("id", id);
		ActorInfo actorInfo = actorInfoService.selectById(id);
		request.setAttribute("actorInfo", actorInfo);
		return "back/actor/actor_info_personal";
	}
	
	@RequestMapping(value = "system/actorInfoAbout")
	public String actorInfoAbout(HttpServletRequest request,HttpServletResponse response,String id) {
		request.setAttribute("id", id);
		return "back/actor/actor_info_about";
	}

	@RequestMapping(value = "system/actorInfoAjaxPage")
	@ResponseBody
	public PageInfo<ActorInfo> actorInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, ActorInfo info, Integer page,
			Integer rows) {
		PageInfo<ActorInfo> pageInfo = new PageInfo<ActorInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setSort("createTime");
		info.setOrder("desc");
		pageInfo = actorInfoService.selectActorInfoForPage(info, pageInfo,"selectActorInfoForPage",true);
		return pageInfo;
	}
	
	@RequestMapping(value = "system/actorInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> actorInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, ActorInfo info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			result = actorInfoService.insert(info);
			msg = "保存失败！";
		} else {
			result = actorInfoService.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "system/actorInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> actorInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, ActorInfo info) {
		int result = 0;
		try {
			result = actorInfoService.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
	
	@RequestMapping(value = "system/actorInfoAjaxUpdate")
	@ResponseBody
	public Map<String,Object> actorInfoAjaxUpdate(HttpServletRequest request,
			HttpServletResponse response, ActorInfo info) {
		int result = 0;
		try {
			result = actorInfoService.update(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
