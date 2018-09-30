package com.yyhz.sc.controller.back;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yyhz.easemob.httpclient.apidemo.EasemobMessages;
import com.yyhz.rong.models.response.ResponseResult;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.data.model.MessageInfo;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.MessageInfoService;
import com.yyhz.utils.RongCloudMethodUtil;
import com.yyhz.utils.UUIDUtil;

import net.sf.json.JSONObject;

/**
 * 
* @ClassName: MessageInfoController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-04-10 10:55:54 
* @Copyright：
 */
@Controller
public class MessageInfoController extends BaseController {
	
	@Resource
	private MessageInfoService messageInfoService;
	
	@Resource
	private ActorInfoService actorInfoService;

	@RequestMapping(value = "system/messageInfoList")
	public String messageInfoList(HttpServletRequest request,HttpServletResponse response) {
		return "back/message_info_list";
	}

	@RequestMapping(value = "system/messageInfoAjaxPage")
	@ResponseBody
	public PageInfo<MessageInfo> messageInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, MessageInfo info, Integer page,
			Integer rows) {
		PageInfo<MessageInfo> pageInfo = new PageInfo<MessageInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		messageInfoService.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "system/messageInfoAjaxAll")
	@ResponseBody
	public List<MessageInfo> messageInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, MessageInfo info, Integer page,
			Integer rows) {
		List<MessageInfo> results= messageInfoService.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/messageInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> messageInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, MessageInfo info,String send) {
		int result = 0;
		String msg = "";
		List<ActorInfo> actorInfoList = null;
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			info.setCreater(getSessionUser(request).getUserId());
			info.setCreateTime(new Date());
			info.setType(3);
			info.setStatus("0");
			Map<String,Object> targetMap = new HashMap<String,Object>();
			targetMap.put("level", info.getSendTargetEntity());
			if(info.getSendUsers() != null && !info.getSendUsers().isEmpty()){
				actorInfoList = actorInfoService.selectByIds(info.getSendUsers());
				List<Map<String,String>> sendUserList = new ArrayList<Map<String,String>>();
				if(actorInfoList != null && !actorInfoList.isEmpty()){
					for(ActorInfo actorInfo : actorInfoList){
						Map<String,String> actorMap = new HashMap<String,String>();
						actorMap.put("id", actorInfo.getId());
						actorMap.put("name", actorInfo.getName());
						sendUserList.add(actorMap);
					}
				}
				targetMap.put("sendUsers", sendUserList);
			}
						
			String sendTarget = JSON.toJSONString(targetMap);
			info.setSendTarget(sendTarget);
			result = messageInfoService.insert(info);
			msg = "保存失败！";
		} else {
			Map<String,Object> targetMap = new HashMap<String,Object>();
			targetMap.put("level", info.getSendTargetEntity());
			if(info.getSendUsers() != null && !info.getSendUsers().isEmpty()){
				actorInfoList = actorInfoService.selectByIds(info.getSendUsers());
				List<Map<String,String>> sendUserList = new ArrayList<Map<String,String>>();
				if(actorInfoList != null && !actorInfoList.isEmpty()){
					for(ActorInfo actorInfo : actorInfoList){
						Map<String,String> actorMap = new HashMap<String,String>();
						actorMap.put("id", actorInfo.getId());
						actorMap.put("name", actorInfo.getName());
						sendUserList.add(actorMap);
					}
				}
				targetMap.put("sendUsers", sendUserList);
			}	
			String sendTarget = JSON.toJSONString(targetMap);
			info.setSendTarget(sendTarget);
			result = messageInfoService.update(info);
			msg = "修改失败！";
		}
		
		if(result > 0 && "1".equals(send)){
			List<String> targetEntity = info.getSendTargetEntity();
			List<ActorInfo> actorlist = new ArrayList<ActorInfo>();
			if(targetEntity.contains("-1")){
				//所有用户
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("status", 0);		
				actorlist = actorInfoService.selectAll(params);
			}else{
				//普通用户
				if(targetEntity.contains("0")){
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("status", 0);
					params.put("level", 0);
					actorlist = actorInfoService.selectAll(params);
				}
				
				//实名用户
				if(targetEntity.contains("1")){
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("status", 0);
					params.put("level", 1);
					List<ActorInfo> list = actorInfoService.selectAll(params);
					actorlist.addAll(list);
				}
				
				//高级用户
				if(targetEntity.contains("2")){
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("status", 0);
					params.put("level", 2);
					List<ActorInfo> list = actorInfoService.selectAll(params);
					actorlist.addAll(list);
				}
				
				//具体用户
				if(targetEntity.contains("3")){
					if(actorInfoList != null){
						actorlist.removeAll(actorInfoList);
						actorInfoList.addAll(actorlist);
						actorlist = actorInfoList;
					}
				}
			}
			
			//发送消息
			JsonNodeFactory factory = new JsonNodeFactory(false);
			String from = "admin";
	        String targetTypeus = "users";
	        ObjectNode ext = factory.objectNode();
	        ArrayNode targetusers = factory.arrayNode();
	        for(ActorInfo actorInfo : actorlist){
	        	 targetusers.add(actorInfo.getId());
	        }
	        ObjectNode txtmsg = factory.objectNode();
	        txtmsg.put("msg", info.getContent());
	        txtmsg.put("type","txt");
	        //ObjectNode node = EasemobMessages.sendMessages(targetTypeus, targetusers, txtmsg, from, ext);
	        //JsonNode jsonNode = node.get("statusCode");
	        //if(jsonNode.asInt() == 200){
	        //	info.setStatus("1");
	        //	info.setSendTime(new Date());
	        //	messageInfoService.update(info);
	        //}
	        String[] targetIds = new String[actorlist.size()];
	        int count=0;
	        for(ActorInfo actorInfo : actorlist){
	        	targetIds[count] = actorInfo.getId();
	        	count++;
	        }
	        JSONObject json = JSONObject.fromObject(ext);
	        ResponseResult privateResult = RongCloudMethodUtil.privateMessage("admin", info.getContent(), targetIds, json.toString());
	        if(privateResult.code == 200){
	        	info.setStatus("1");
	        	info.setSendTime(new Date());
	        	messageInfoService.update(info);
	        }
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "system/messageInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> messageInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, MessageInfo info) {
		int result = 0;
		try {
			info.setStatus("-1");
			result = messageInfoService.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
