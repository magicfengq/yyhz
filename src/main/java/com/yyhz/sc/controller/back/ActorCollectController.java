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
import com.yyhz.sc.data.model.ActorCollect;
import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.services.ActorCollectService;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: ActorCollectController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-03-18 15:19:54 
* @Copyright：
 */
@Controller
public class ActorCollectController extends BaseController {
	
	@Resource
	private ActorCollectService actorCollectService;
	
	@Resource
	private ActorInfoService actorInfoService;

	@RequestMapping(value = "system/actorCollectList")
	public String actorCollectList(HttpServletRequest request,
			HttpServletResponse response) {
		return "back/actor/actor_collect_list";
	}
	
	@RequestMapping(value = "system/actorCollectAjaxPage")
	@ResponseBody
	public PageInfo<ActorCollect> actorCollectAjaxPage(HttpServletRequest request,
			HttpServletResponse response, ActorCollect info, Integer page,
			Integer rows) {
		PageInfo<ActorCollect> pageInfo = new PageInfo<ActorCollect>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		actorCollectService.selectAll(info, pageInfo);
		List<ActorCollect> list = pageInfo.getRows();
		if(list == null || list.isEmpty()){
			return pageInfo;
		}
		List<String> actorIdList = new ArrayList<String>();
		for(ActorCollect actorCollect : list){
			actorIdList.add(actorCollect.getActorId());
		}
		List<ActorInfo> actorInfoList = actorInfoService.selectByIds(actorIdList);
		Map<String,ActorInfo> actorMap = new HashMap<String,ActorInfo>();
		for(ActorInfo actorInfo : actorInfoList){
			actorMap.put(actorInfo.getId(), actorInfo);
		}
		for(ActorCollect actorCollect : list){
			actorCollect.setActorInfo(actorMap.get(actorCollect.getActorId()));
		}
		return pageInfo;
	}

	@RequestMapping(value = "system/actorCollectAjaxAll")
	@ResponseBody
	public List<ActorCollect> actorCollectAjaxAll(HttpServletRequest request,
			HttpServletResponse response, ActorCollect info, Integer page,
			Integer rows) {
		List<ActorCollect> results= actorCollectService.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/actorCollectAjaxSave")
	@ResponseBody
	public Map<String,Object> actorCollectAjaxSave(HttpServletRequest request,
			HttpServletResponse response, ActorCollect info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			result = actorCollectService.insert(info);
			msg = "保存失败！";
		} else {
			result = actorCollectService.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "system/actorCollectAjaxDelete")
	@ResponseBody
	public Map<String,Object> actorCollectAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, ActorCollect info) {
		int result = 0;
		try {
			result = actorCollectService.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
