package com.yyhz.sc.controller.back;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.data.model.PublicType;
import com.yyhz.sc.data.model.ShowInfo;
import com.yyhz.sc.data.model.ShowInfoPictures;
import com.yyhz.sc.services.ActorCommentService;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.PublicTypeService;
import com.yyhz.sc.services.ShowInfoPicturesService;
import com.yyhz.sc.services.ShowInfoService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: ShowInfoController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-03-22 17:00:09
* @Copyright：
 */
@Controller
public class ShowInfoController extends BaseController {
	
	@Resource
	private ShowInfoService showInfoService;
	
	@Resource
	private ActorInfoService actorInfoService;
	
	@Resource
	private PublicTypeService publicTypeService;
	
	@Resource
	private ShowInfoPicturesService showInfoPicturesService;
	
	@Resource
	private ActorCommentService actorCommentService;

	@RequestMapping(value = "system/showInfoList")
	public String showInfoList(HttpServletRequest request,
			HttpServletResponse response) {
		return "back/show/show_info_list";
	}

	@RequestMapping(value = "system/showInfoAjaxPage")
	@ResponseBody
	public PageInfo<ShowInfo> showInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, ShowInfo info, Integer page,
			Integer rows) {
		
		Map<String,Object> userParams = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(info.getMobile())){
			userParams.put("mobile", info.getMobile());
		}
		if(StringUtils.isNotBlank(info.getUserName())){
			userParams.put("name", info.getUserName());
		}
		List<String> userIdList = new ArrayList<String>();
		if(!userParams.isEmpty()){
			//查询用户信息
			List<ActorInfo> actorList = actorInfoService.selectAll(userParams,"selectActorInfoForPage");
			if(actorList != null && !actorList.isEmpty()){
				for(ActorInfo actorInfo : actorList){
					userIdList.add(actorInfo.getId());
				}
			}
		}
		
		PageInfo<ShowInfo> pageInfo = new PageInfo<ShowInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setStatus("0");
		info.setSort("createTime");
		info.setOrder("desc");
		if(!userIdList.isEmpty()){
			info.setActorIdList(userIdList);
		}
		showInfoService.selectAll(info, pageInfo);
		List<ShowInfo> list = pageInfo.getRows();
		if(list == null || list.isEmpty()){
			return pageInfo;
		}
		List<String> actorIdList = new ArrayList<String>();
		List<String> publicTypeIdList = new ArrayList<String>();
		for(ShowInfo showInfo : list){
			actorIdList.add(showInfo.getCreater());
			if(StringUtils.isNotBlank(showInfo.getPublicType())){
				publicTypeIdList.add(showInfo.getPublicType());
			}			
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
		for(ShowInfo showInfo : list){
			ActorInfo userInfo = actorMap.get(showInfo.getCreater());
			if(userInfo != null){
				showInfo.setMobile(userInfo.getMobile());
				showInfo.setUserName(userInfo.getName());
			}
			
		}
		
		//发布类型
		if(publicTypeIdList == null || publicTypeIdList.isEmpty()){
			return pageInfo;
		}
		List<PublicType> publicTypeList = publicTypeService.selectByIds(publicTypeIdList);
		if(publicTypeList == null || publicTypeList.isEmpty()){
			return pageInfo;
		}
		Map<String,PublicType> publicTypeMap = new HashMap<String,PublicType>();
		for(PublicType piublicType : publicTypeList){
			publicTypeMap.put(piublicType.getId(), piublicType);
		}
		for(ShowInfo showInfo : list){
			PublicType publicType = publicTypeMap.get(showInfo.getPublicType());
			if(publicType != null){
				showInfo.setPublicTypeName(publicType.getName());
			}
			
		}
		return pageInfo;
	}
	
	@RequestMapping(value = "system/showInfoDetail")
	public String showInfoDetail(HttpServletRequest request,HttpServletResponse response,String id) {
		request.setAttribute("id", id);
		ShowInfo showInfo = showInfoService.selectById(id);
		request.setAttribute("showInfo", showInfo);
		if("0".equals(showInfo.getType())){
			//图片
			ShowInfoPictures pictures = new ShowInfoPictures();
			pictures.setShowId(id);
			List<ShowInfoPictures> picList = showInfoPicturesService.selectAll(pictures);
			request.setAttribute("picList", picList);
		}
		return "back/show/show_info_detail";
	}
	
	@RequestMapping(value = "system/showInfoBasic")
	public String showInfoBasic(HttpServletRequest request,HttpServletResponse response,String id) {
		request.setAttribute("id", id);
		//详情
		ShowInfo showInfo = showInfoService.selectById(id);
		String publicTypeId = showInfo.getPublicType();
		if(StringUtils.isNotBlank(publicTypeId)){
			PublicType publicType = publicTypeService.selectById(publicTypeId);
			if(publicType != null){
				showInfo.setPublicTypeName(publicType.getName());
			}
		}
		request.setAttribute("showInfo", showInfo);
		
		//用户信息
		String actorId = showInfo.getCreater();
		ActorInfo actorInfo = actorInfoService.selectById(actorId);
		float avgScore = actorCommentService.getAvgScore(actorId);
		if(avgScore == 0.0f){
			actorInfo.setAvgScore(null);
		}else{
			actorInfo.setAvgScore(avgScore);
		}
		request.setAttribute("actorInfo", actorInfo);
		Integer authenticateLevel = actorInfo.getAuthenticateLevel();
		if(authenticateLevel == 0){
			actorInfo.setAuthenticateLevelName("未认证");
		}else if(authenticateLevel == 1){
			actorInfo.setAuthenticateLevelName("实名认证");
		}else if(authenticateLevel == 2){
			actorInfo.setAuthenticateLevelName("资历认证");
		}else{
			actorInfo.setAuthenticateLevelName("");
		}
		return "back/show/show_info_basic";
	}
	
	@RequestMapping(value = "system/showInfoContent")
	public String showInfoContent(HttpServletRequest request,HttpServletResponse response,String id) {
		request.setAttribute("id", id);
		//详情
		ShowInfo showInfo = showInfoService.selectById(id);
		request.setAttribute("showInfo", showInfo);
		return "back/show/show_info_content";
	}
	
	@RequestMapping(value = "system/showInfoAbout")
	public String showInfoAbout(HttpServletRequest request,HttpServletResponse response,String id) {
		request.setAttribute("id", id);
		return "back/show/show_info_about";
	}
	
	@RequestMapping(value = "system/showInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> showInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, ShowInfo info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			result = showInfoService.insert(info);
			msg = "保存失败！";
		} else {
			result = showInfoService.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}
	
	@RequestMapping(value = "system/showInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> showInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, ShowInfo info) {
		int result = 0;
		try {
			info.setStatus("1");
			result = showInfoService.update(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
