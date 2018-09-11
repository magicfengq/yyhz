package com.yyhz.sc.controller.back;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yyhz.easemob.httpclient.apidemo.EasemobMessages;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.data.model.AuthenticateApply;
import com.yyhz.sc.data.model.SystemPictureInfo;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.AuthenticateApplyService;
import com.yyhz.sc.services.SystemPictureInfoService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: AuthenticateApplyController 
* @Description: 控制层 
* @author crazyt 
* @date 2017-03-28 09:19:55 
* @Copyright：
 */
@Controller
public class AuthenticateApplyController extends BaseController {
	
	@Resource
	private AuthenticateApplyService authenticateApplyService;
	
	@Resource
	private ActorInfoService actorInfoService;
	
	@Resource
	private SystemPictureInfoService systemPictureInfoService;

	@RequestMapping(value = "system/authenticateApplyList")
	public String authenticateApplyList(HttpServletRequest request,
			HttpServletResponse response) {
		return "back/authenticate/authenticate_apply_list";
	}
	
	@RequestMapping(value = "system/authenticateInfo")
	public String authenticateInfo(HttpServletRequest request,HttpServletResponse response,String id) {
		AuthenticateApply authenticateApply = authenticateApplyService.selectById(id);
		request.setAttribute("info", authenticateApply);
		return "back/authenticate/authenticate_info";
	}
	
	@RequestMapping(value = "system/credentialsInfo")
	public String credentialsInfo(HttpServletRequest request,HttpServletResponse response,String id) {
		AuthenticateApply authenticateApply = authenticateApplyService.selectById(id);
		Map<String,Map<String,Object>> credentialsMap = new HashMap<String,Map<String,Object>>();
		List<String> imageUuidList = new ArrayList<String>();
		if(StringUtils.isNotBlank(authenticateApply.getPhoto1())){
			Map<String,Object> extraMap = new HashMap<String,Object>();
			extraMap.put("extraText", "身份证正面");
			extraMap.put("orderList", 1);
			credentialsMap.put(authenticateApply.getPhoto1(), extraMap);
			imageUuidList.add(authenticateApply.getPhoto1());
		}
		if(StringUtils.isNotBlank(authenticateApply.getPhoto2())){
			Map<String,Object> extraMap = new HashMap<String,Object>();
			extraMap.put("extraText", "身份证反面");
			extraMap.put("orderList", 2);
			credentialsMap.put(authenticateApply.getPhoto2(), extraMap);
			imageUuidList.add(authenticateApply.getPhoto2());
		}
		if(StringUtils.isNotBlank(authenticateApply.getPhoto3())){
			Map<String,Object> extraMap = new HashMap<String,Object>();
			extraMap.put("extraText", "营业执照");
			extraMap.put("orderList", 3);
			credentialsMap.put(authenticateApply.getPhoto3(), extraMap);
			imageUuidList.add(authenticateApply.getPhoto3());
		}
		if(StringUtils.isNotBlank(authenticateApply.getPhoto4())){
			Map<String,Object> extraMap = new HashMap<String,Object>();
			extraMap.put("extraText", "资格证");
			extraMap.put("orderList", 4);
			credentialsMap.put(authenticateApply.getPhoto4(), extraMap);
			imageUuidList.add(authenticateApply.getPhoto4());
		}
		
		Map<String,SystemPictureInfo> picMap = new HashMap<String,SystemPictureInfo>();
		if(imageUuidList != null && !imageUuidList.isEmpty()){
			List<SystemPictureInfo> picList = systemPictureInfoService.selectByUuids(imageUuidList);
			if(picList == null || picList.isEmpty()){
				return "back/authenticate/credentials_info";
			}
			for(SystemPictureInfo systemPictureInfo : picList){
				picMap.put(systemPictureInfo.getUuid(), systemPictureInfo);
			}
		}
		
		Map<String,Object> resultMap = new TreeMap<String,Object>();
		if(picMap != null && !picMap.isEmpty()){
			for(Entry<String, SystemPictureInfo> entry :  picMap.entrySet()){
				if(!credentialsMap.containsKey(entry.getKey())){
					continue;
				}
				Map<String,Object> extraMap = credentialsMap.get(entry.getKey());
				Map<String,Object> retMap = new HashMap<String,Object>();
				retMap.put("text", extraMap.get("extraText"));
				retMap.put("orderList", extraMap.get("orderList"));
				retMap.put("picInfo", entry.getValue());
				
				resultMap.put(entry.getKey(), retMap);
			}
		}
		
		List<Map.Entry<String,Object>> list = new ArrayList<Map.Entry<String,Object>>(resultMap.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,Object>>() {
			@SuppressWarnings("unchecked")
			@Override
			public int compare(Entry<String, Object> e1, Entry<String, Object> e2) {
				Map<String,Object> m1 = (Map<String,Object>)e1.getValue();
                Map<String,Object> m2 = (Map<String,Object>)e2.getValue();
                Integer ol1 = (Integer)m1.get("orderList");
                Integer ol2 = (Integer)m2.get("orderList");
                return ol1.compareTo(ol2);
			}
        });
        
		request.setAttribute("resultInfo", list);
		request.setAttribute("info", authenticateApply);
		return "back/authenticate/credentials_info";
	}
	
	@RequestMapping(value = "system/authenticateApplyDetail")
	public String authenticateApplyDetail(HttpServletRequest request,HttpServletResponse response,String id) {
		AuthenticateApply authenticateApply = authenticateApplyService.selectById(id);
		request.setAttribute("info", authenticateApply);
		return "back/authenticate/authenticate_apply_detail";
	}

	@RequestMapping(value = "system/authenticateApplyAjaxPage")
	@ResponseBody
	public PageInfo<AuthenticateApply> authenticateApplyAjaxPage(HttpServletRequest request,
			HttpServletResponse response, AuthenticateApply info, String account,String roleName,Integer page,
			Integer rows) {
		PageInfo<AuthenticateApply> pageInfo = new PageInfo<AuthenticateApply>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		authenticateApplyService.selectAll(info, pageInfo);
		List<AuthenticateApply> list = pageInfo.getRows();
		if(list == null || list.isEmpty()){
			return pageInfo;
		}
		List<String> actorIdList = new ArrayList<String>();
		for(AuthenticateApply authenticateApply : list){
			actorIdList.add(authenticateApply.getActorId());			
		}
		//发布账号
		PageInfo<ActorInfo> actorPageInfo = new PageInfo<ActorInfo>();
		actorPageInfo.setPage(1);
		actorPageInfo.setPageSize(1000);
		ActorInfo actorInfo = new ActorInfo();
		actorInfo.setActorIdList(actorIdList);
		actorInfo.setRoleName(roleName);
		actorInfo.setMobile(account);
		actorPageInfo = actorInfoService.selectActorInfoForPage(actorInfo, actorPageInfo,"selectActorInfoForPage",true);
		List<ActorInfo> actorList = actorPageInfo.getRows();
		if(actorList == null || actorList.isEmpty()){
			return  pageInfo;
		}
		
		Map<String,ActorInfo> actorMap = new HashMap<String,ActorInfo>();
		List<String> filterActorIdList = new ArrayList<String>();
		for(ActorInfo actor : actorList){
			filterActorIdList.add(actor.getId());
			actorMap.put(actor.getId(), actor);
		}
		
		info.setActorIdList(filterActorIdList);
		authenticateApplyService.selectAll(info, pageInfo);
		list = pageInfo.getRows();
		
		for(AuthenticateApply authenticateApply : list){
			authenticateApply.setActorInfo(actorMap.get(authenticateApply.getActorId()));
		}
		return pageInfo;
	}

	@RequestMapping(value = "system/authenticateApplyAjaxAll")
	@ResponseBody
	public List<AuthenticateApply> authenticateApplyAjaxAll(HttpServletRequest request,
			HttpServletResponse response, AuthenticateApply info, Integer page,
			Integer rows) {
		List<AuthenticateApply> results= authenticateApplyService.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/authenticateAuditPass")
	@ResponseBody
	public Map<String,Object> authenticateAuditPass(HttpServletRequest request,
			HttpServletResponse response, AuthenticateApply info) {
		
		AuthenticateApply authenticateApply = authenticateApplyService.selectById(info.getId());		
		authenticateApply.setCheckUser(getSessionUser(request).getId());
		authenticateApply.setCheckTime(new Date());
		authenticateApply.setCheckStatus(1);
		authenticateApply.setUserCurrentLevel(info.getUserCurrentLevel());
		int result = authenticateApplyService.update(authenticateApply);
		
		//修改用户信息
		String actorId = info.getActorId();
		ActorInfo actorInfo = new ActorInfo();
		actorInfo.setId(actorId);
		actorInfo.setAuthenticateLevel(info.getUserCurrentLevel());
		actorInfo.setLevel(info.getUserCurrentLevel());
		actorInfo.setRealName(authenticateApply.getRealName());
		actorInfo.setIdcard(authenticateApply.getIdcard());		
		result = actorInfoService.update(actorInfo);
		
		//给客户端发送消息
		if(result > 0){
			JsonNodeFactory factory = new JsonNodeFactory(false);
			String from = "admin";
	        String targetTypeus = "users";
	        ObjectNode ext = factory.objectNode();
	        ArrayNode targetusers = factory.arrayNode();
	        targetusers.add(actorId);
	        ObjectNode txtmsg = factory.objectNode();
	        txtmsg.put("msg", "您的认证申请已经审核通过,请进入个人中心查看。");
	        txtmsg.put("type","txt");
	        EasemobMessages.sendMessages(targetTypeus, targetusers, txtmsg, from, ext);
		}
		
		return getJsonResult(result, "操作成功","操作失败");
	}
	
	@RequestMapping(value = "system/authenticateAuditRefuse")
	@ResponseBody
	public Map<String,Object> authenticateAuditRefuse(HttpServletRequest request,
			HttpServletResponse response, String id,String actorId,String refuseContent) {
		
		AuthenticateApply authenticateApply = authenticateApplyService.selectById(id);		
		authenticateApply.setCheckUser(getSessionUser(request).getId());
		authenticateApply.setCheckTime(new Date());
		authenticateApply.setCheckStatus(2);
		authenticateApply.setUserCurrentLevel(0);
		int result = authenticateApplyService.update(authenticateApply);
		
		//修改用户信息
		ActorInfo actorInfo = new ActorInfo();
		actorInfo.setId(actorId);
		actorInfo.setAuthenticateLevel(0);
		actorInfo.setLevel(0);	
		result = actorInfoService.update(actorInfo);
		
		//给客户端发送消息
		if(result > 0){
			JsonNodeFactory factory = new JsonNodeFactory(false);
			String from = "admin";
	        String targetTypeus = "users";
	        ObjectNode ext = factory.objectNode();
	        ArrayNode targetusers = factory.arrayNode();
	        targetusers.add(actorId);
	        ObjectNode txtmsg = factory.objectNode();
	        txtmsg.put("msg", "您的认证申请已被拒绝,拒绝原因:" + refuseContent + "。您可以重新上传认证资料再次进行申请,给您带来的不便深表歉意。");
	        txtmsg.put("type","txt");
	        EasemobMessages.sendMessages(targetTypeus, targetusers, txtmsg, from, ext);
		}
		
		return getJsonResult(result, "操作成功","操作失败");
	}
	
	@RequestMapping(value = "system/authenticateApplyAjaxSave")
	@ResponseBody
	public Map<String,Object> authenticateApplyAjaxSave(HttpServletRequest request,
			HttpServletResponse response, AuthenticateApply info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			result = authenticateApplyService.insert(info);
			msg = "保存失败！";
		} else {
			result = authenticateApplyService.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}
	
	@RequestMapping(value = "system/authenticateApplyAjaxDelete")
	@ResponseBody
	public Map<String,Object> authenticateApplyAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, AuthenticateApply info) {
		int result = 0;
		try {
			result = authenticateApplyService.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
