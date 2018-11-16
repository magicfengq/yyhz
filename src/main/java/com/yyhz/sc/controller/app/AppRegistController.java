package com.yyhz.sc.controller.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yyhz.constant.AppRetCode;
import com.yyhz.rong.models.Result;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.data.model.ActorRole;
import com.yyhz.sc.data.model.SystemPictureInfo;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.ActorRoleService;
import com.yyhz.sc.services.SystemPictureInfoService;
import com.yyhz.utils.DateUtils;
import com.yyhz.utils.EasemobUtil;
import com.yyhz.utils.RongCloudMethodUtil;
import com.yyhz.utils.UUIDUtil;
import com.yyhz.utils.stream.config.Configurations;

@Controller
@RequestMapping(value = "api")
public class AppRegistController extends BaseController{

	private final Logger logger = LoggerFactory.getLogger(AppRegistController.class);
	
	@Resource
	private ActorInfoService actorInfoService;
	@Resource
	private SystemPictureInfoService systemPictureInfoService;
	@Resource
	private ActorRoleService actorRoleService;

	
	/**
	 * 
	 * @Title: login
	 * @Description: 用户登陆接口
	 * @param mobile 手机号码
	 * @param passWord 用户密码
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "login")
	public void login(HttpServletRequest request,HttpServletResponse response, ActorInfo reqObj) 
	{

		int result = -1;
		String msg = "";
		Map<String, Object> retInfo = null;
		try {
			if (StringUtils.isEmpty(reqObj.getMobile())) {
				writeJsonObject(response, AppRetCode.MOBILE_IS_NULL, AppRetCode.MOBILE_IS_NULL_TEXT, null);
				return;
			}
			if (StringUtils.isEmpty(reqObj.getPassword())) {
				writeJsonObject(response, AppRetCode.PASSOWRD_IS_NULL, AppRetCode.PASSOWRD_IS_NULL_TEXT, null);
				return;
			}
			ActorInfo info = new ActorInfo();
			info.setMobile(reqObj.getMobile());
			ActorInfo member = actorInfoService.selectEntity(info);
			if (member == null) {
				result = AppRetCode.ACCOUNT_EXIST;
				msg = AppRetCode.ACCOUNT_NOT_EXIST_TEXT;
			} else {
				if (reqObj.getPassword().equals(member.getPassword())) {
					if (member.getStatus() == 1) {
						result = AppRetCode.ACCOUNT_DELETED;
						msg = AppRetCode.ACCOUNT_DELETED_TEXT;
					} else {
						result = AppRetCode.NORMAL;
						msg = AppRetCode.NORMAL_TEXT;
						ActorInfo condition = new ActorInfo();
						condition.setId(member.getId());
						condition.setLastLoginTime(new Date());
						actorInfoService.update(condition);
						
						retInfo = getMyInfo(member.getId());
						
						/*
						 * 注册环信
						 */
						/*if (member != null
								&& (member.getEasemobStatusCode() == null || !member
										.getEasemobStatusCode().equals("200"))) {
							/*ActorInfo umember = new ActorInfo();
							umember.setId(member.getId());
							ObjectNode objectNode = EasemobUtil.register(
									member.getId(), "123456");
							
							if(objectNode != null) {
								if (objectNode.get("statusCode").toString()
										.equals("400")
										&& objectNode
												.get("error")
												.asText()
												.equals("duplicate_unique_property_exists")) {
									umember.setEasemobStatusCode("200");
								} else {
									umember.setEasemobStatusCode(objectNode.get(
											"statusCode").toString());
								}
							}
							actorInfoService.update(umember);
						}*/
						//RongCloudMethodUtil.updateUserInfo(member.getId(), reqObj.getMobile(), member.getHeadImgUrl());
						
					}
				} else {
					msg = AppRetCode.PASSOWRD_ERROR_TEXT;
					result = AppRetCode.PASSOWRD_ERROR;
				}

			}
			writeJsonObject(response, result, msg, retInfo);
		} catch (Exception e) {
			e.printStackTrace();
			writeJsonObject(response, AppRetCode.SERVER_EXCEPTION, AppRetCode.SERVER_EXCEPTION_TEXT, null);
		}
	}

	/**
	 * 
	 * @Title: register
	 * @Description: 用户注册接口
	 * @param mobile 手机号码
	 * @param passWord 用户密码
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "register")
	public void register(HttpServletRequest request, HttpServletResponse response, ActorInfo reqObj) {
			
		int result = -1;
		String msg = "";
		try {
			if (StringUtils.isEmpty(reqObj.getMobile())) {
				writeJsonObject(response, AppRetCode.MOBILE_IS_NULL, AppRetCode.MOBILE_IS_NULL_TEXT, null);
				return;
			}
			if (StringUtils.isEmpty(reqObj.getPassword())) {
				writeJsonObject(response, AppRetCode.PASSOWRD_IS_NULL, AppRetCode.PASSOWRD_IS_NULL_TEXT, null);
				return;
			}

			// 判断账号是否已经存在
			ActorInfo condition = new ActorInfo();
			condition.setMobile(reqObj.getMobile());
			ActorInfo member = actorInfoService.selectEntity(condition);
			if (member == null) {
				String memberId = UUIDUtil.getUUID();
				condition.setId(memberId);
				condition.setPassword(reqObj.getPassword());
				condition.setCreateTime(new Date());
				condition.setMobile(reqObj.getMobile());
				condition.setAreaCode(reqObj.getAreaCode());
				condition.setStatus(0); // 有效状态
				condition.setRegisterType(0); // 普通注册
				condition.setAuthenticateLevel(0); // 级别
				result = actorInfoService.insert(condition);
				
				if(result > 0)
				{
					/*
					 * 环信注册
					 */
					/*ObjectNode objectNode = EasemobUtil.register(memberId, "123456");
					String nodeResult = objectNode.get("statusCode").toString();
					ActorInfo umember = new ActorInfo();
					umember.setId(memberId);
					umember.setEasemobStatusCode(nodeResult);
					actorInfoService.update(umember);*/
					//融云注册
					String token = RongCloudMethodUtil.AddUserInfo(memberId, reqObj.getMobile(), "");
					ActorInfo umember = new ActorInfo();
					umember.setId(memberId);
					umember.setEasemobStatusCode(token);
					actorInfoService.update(umember);
					
					result = AppRetCode.NORMAL;
					msg = AppRetCode.NORMAL_TEXT;
				}else
				{
					result = AppRetCode.CREATE_ACCOUNT_FAILED;
					msg = AppRetCode.CREATE_ACCOUNT_FAILED_TEXT;
				}
				
			} else {
				result = AppRetCode.ACCOUNT_EXIST;
				msg = AppRetCode.ACCOUNT_EXIST_TEXT;
			}
			writeJsonObject(response, result, msg, null);
		} catch (Exception e) {
			e.printStackTrace();
			writeJsonObject(response, AppRetCode.SERVER_EXCEPTION, AppRetCode.SERVER_EXCEPTION_TEXT, null);
		}
	}
	
	/**
	 * 
	 * @Title: changePassword
	 * @Description: 修改密码
	 * @param mobile 手机号码
	 * @param passWord 用户密码
	 * @param passwordSure 确认密码
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "changePassword")
	public void changePassword(HttpServletRequest request, HttpServletResponse response, 
								ActorInfo reqObj, String passwordSure) 
	{
		int result = -1;
		String msg = "";
		try {
			if (StringUtils.isEmpty(reqObj.getMobile())) {
				writeJsonObject(response, AppRetCode.MOBILE_IS_NULL, AppRetCode.MOBILE_IS_NULL_TEXT, null);
				return;
			}
			if (StringUtils.isEmpty(reqObj.getPassword())) {
				writeJsonObject(response, AppRetCode.PASSOWRD_IS_NULL, AppRetCode.PASSOWRD_IS_NULL_TEXT, null);
				return;
			}
			if(!StringUtils.equals(reqObj.getPassword(), passwordSure))
			{
				writeJsonObject(response, AppRetCode.PASSWORD_IS_INCONSISTENT, AppRetCode.PASSWORD_IS_INCONSISTENT_TEXT, null);
				return;				
			}

			// 判断账号是否已经存在
			ActorInfo condition = new ActorInfo();
			condition.setMobile(reqObj.getMobile());
			ActorInfo member = actorInfoService.selectEntity(condition);
			if (member == null) {
				msg = AppRetCode.ACCOUNT_NOT_EXIST_TEXT;
				result = AppRetCode.ACCOUNT_NOT_EXIST;
			} else {
				condition.setId(member.getId());
				condition.setPassword(reqObj.getPassword());
				result = actorInfoService.update(condition);
				if (result > 0) {
					result = AppRetCode.NORMAL;
					msg = AppRetCode.NORMAL_TEXT;
				} else {
					result = AppRetCode.RESET_PASSWORD_FAILED;
					msg = AppRetCode.RESET_PASSWORD_FAILED_TEXT;
				}
			}
			writeJsonObject(response, result, msg, null);
		} catch (Exception e) {
			e.printStackTrace();
			writeJsonObject(response, AppRetCode.SERVER_EXCEPTION, AppRetCode.SERVER_EXCEPTION_TEXT, null);
		}
	}
	
	/**
	 * 
	 * @Title: otherLogin
	 * @Description: 修改密码
	 * @param mobile 手机号码
	 * @param passWord 用户密码
	 * @param passwordSure 确认密码
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "other/login")
	public void otherLogin(HttpServletRequest request, HttpServletResponse response, ActorInfo reqObj) {
		int result = -1;
		String msg = "";
		SystemPictureInfo pInfo = null;
		Map<String, Object> retInfo = null;
		try {
			if (StringUtils.isEmpty(reqObj.getMkey())) {
				writeJsonObject(response, AppRetCode.OTHER_LOGIN_KEY_IS_NULL, AppRetCode.OTHER_LOGIN_KEY_IS_NULL_TEXT, null);
				return;
			}
			if (reqObj.getRegisterType() == null) {
				writeJsonObject(response, AppRetCode.REGIST_TYPE_IS_NULL, AppRetCode.REGIST_TYPE_IS_NULL_TEXT, null);
				return;
			}
			
			ActorInfo condition = new ActorInfo();
			condition.setMkey(reqObj.getMkey());
			ActorInfo member = actorInfoService.selectEntity(condition);
			if (member == null) {
				
				member = new ActorInfo();
				member.setId(UUIDUtil.getUUID());
				member.setCreateTime(new Date());
				member.setMkey(reqObj.getMkey());
				member.setStatus(0); // 有效状态
				member.setRegisterType(reqObj.getRegisterType());
				member.setAuthenticateLevel(0); // 级别
				member.setName(reqObj.getName());
				
				// 第一次登陆同步头像
				if(StringUtils.isNotBlank(reqObj.getHeadImgUrl())) {
					pInfo = this.uploadImageFile("head", reqObj.getHeadImgUrl());
				}

				if(pInfo != null) {
					member.setImgUuid(pInfo.getUuid());
				}

				int ret = actorInfoService.insert(member);
				
				if(ret > 0)
				{
					retInfo = getMyInfo(member.getId());
					
					result = AppRetCode.NORMAL;
					msg = AppRetCode.NORMAL_TEXT;
				}else
				{
					result = AppRetCode.CREATE_ACCOUNT_FAILED;
					msg = AppRetCode.CREATE_ACCOUNT_FAILED_TEXT;
				}
			}
			
			if (member.getStatus() == 1) {
				result = AppRetCode.ACCOUNT_DELETED;
				msg = AppRetCode.ACCOUNT_DELETED_TEXT;
			}else {
				result = AppRetCode.NORMAL;
				msg = AppRetCode.NORMAL_TEXT;
				
				condition = new ActorInfo();
				condition.setId(member.getId());
				condition.setLastLoginTime(new Date());
				condition.setName(member.getName());
				if(pInfo != null) {
					condition.setImgUuid(pInfo.getUuid());
				}

				actorInfoService.update(condition);

				retInfo = getMyInfo(member.getId());

				/*
				 * 注册环信
				 */
				if (member != null
						&& (member.getEasemobStatusCode() == null || !member
								.getEasemobStatusCode().equals("200"))) {
					ActorInfo umember = new ActorInfo();
					umember.setId(member.getId());

					ObjectNode objectNode = EasemobUtil.register(
							member.getId(), "123456");
					if(objectNode != null) {
						if (objectNode.get("statusCode").toString()
								.equals("400")
								&& objectNode
										.get("error")
										.asText()
										.equals("duplicate_unique_property_exists")) {
							umember.setEasemobStatusCode("200");
						} else {
							umember.setEasemobStatusCode(objectNode.get(
									"statusCode").toString());
						}
					}
					actorInfoService.update(umember);
				}

			}
			writeJsonObject(response, result, msg, retInfo);
		} catch (Exception e) {
			e.printStackTrace();
			writeJsonObject(response, AppRetCode.SERVER_EXCEPTION, AppRetCode.SERVER_EXCEPTION_TEXT, null);
		}
	}

	private Map<String, Object> getMyInfo(String id) {
		
		if(StringUtils.isBlank(id)) {
			return buildEmptyMyInfo();
		}
		
		Map<String, Object> ret = new HashMap<String, Object>();
		
		// 检索用户信息
		ActorInfo actorInfo = actorInfoService.selectById(id);
		if(actorInfo == null || actorInfo.getStatus() == 1) { // 状态 0正常；1已删除
			return buildEmptyMyInfo();
		}
		
		ret.put("id",StringUtils.trimToEmpty(actorInfo.getId()));
		ret.put("name",StringUtils.trimToEmpty(actorInfo.getName()));
		ret.put("mobile",StringUtils.trimToEmpty(actorInfo.getMobile()));
		ret.put("realName",StringUtils.trimToEmpty(actorInfo.getRealName()));
		ret.put("sex",actorInfo.getSex() == null ? 0 : actorInfo.getSex());
		ret.put("birthDay", StringUtils.trimToEmpty(DateUtils.getDateFormat(actorInfo.getBirthDay())));
		ret.put("city",StringUtils.trimToEmpty(actorInfo.getCity()));
		ret.put("actCities",StringUtils.trimToEmpty(actorInfo.getActCities()));
		ret.put("height",StringUtils.trimToEmpty(actorInfo.getHeight()));
		ret.put("weight",StringUtils.trimToEmpty(actorInfo.getWeight()));
		ret.put("shoesSize",StringUtils.trimToEmpty(actorInfo.getShoesSize()));
		ret.put("size",StringUtils.trimToEmpty(actorInfo.getSize()));
		ret.put("authenticateLevel",actorInfo.getAuthenticateLevel());
		//ret.put("level", actorInfo.getLevel() == null ? 0 : actorInfo.getLevel());
		if(actorInfo.getHeadImgUrl() != null) {
			ret.put("headImgUrl",Configurations.buildDownloadUrl(actorInfo.getHeadImgUrl()));			
		}else {
			ret.put("headImgUrl","");						
		}
		ret.put("pushStatus",actorInfo.getPushStatus() == null ? 0 : actorInfo.getPushStatus());
		ret.put("easemobStatusCode",actorInfo.getEasemobStatusCode());
	
		// 检索角色信息
		ActorRole condition = new ActorRole();
		condition.setActorId(actorInfo.getId());
		
		List<String> roles = new ArrayList<String>();
		List<ActorRole> roleList = actorRoleService.selectAll(condition, "selectAllWithName");
		for(ActorRole role : roleList) {
			roles.add(role.getRoleName());
		}
		
		ret.put("roleInfos", roles);
		
		return ret;
	}
	
	private Map<String, Object> buildEmptyMyInfo() {
		Map<String, Object> ret = new HashMap<String, Object>();
				
		ret.put("id","");
		ret.put("name","");
		ret.put("realName","");
		ret.put("sex","");
		ret.put("birthDay", "");
		ret.put("city","");
		ret.put("actCities","");
		ret.put("height","");
		ret.put("weight","");
		ret.put("shoesSize","");
		ret.put("size","");
		ret.put("authenticateLevel","");
		ret.put("level","");
		ret.put("headImgUrl","");						
		ret.put("pushStatus","");
		List<String> roles = new ArrayList<String>();
		ret.put("roleInfos", roles);
		
		return ret;
	}
	/**
	 * 
	 * @Title: faceLogin
	 * @Description: 刷脸注册接口
	 * @param idCard 身份证号
	 * @param realName 姓名
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "faceLogin")
	public void faceLogin(HttpServletRequest request, HttpServletResponse response, ActorInfo reqObj) {
			
		int result = -1;
		String msg = "";
		Map<String, Object> retInfo = null;
		try {
			if (StringUtils.isEmpty(reqObj.getIdcard())) {
				writeJsonObject(response, 300, "身份证号为空", null);
				return;
			}
			if (StringUtils.isEmpty(reqObj.getRealName())) {
				writeJsonObject(response, 300, "姓名为空", null);
				return;
			}

			// 判断账号是否已经存在
			ActorInfo condition = new ActorInfo();
			condition.setIdcard(reqObj.getIdcard());
			ActorInfo member = actorInfoService.selectEntity(condition);
			if (member == null) {
				String memberId = UUIDUtil.getUUID();
				condition.setId(memberId);
				//condition.setPassword(reqObj.getPassword());
				condition.setCreateTime(new Date());
				//condition.setMobile(reqObj.getMobile());
				condition.setIdcard(reqObj.getIdcard());
				condition.setRealName(reqObj.getRealName());
				condition.setAreaCode(reqObj.getAreaCode());
				condition.setStatus(0); // 有效状态
				condition.setRegisterType(0); // 普通注册
				condition.setAuthenticateLevel(0); // 级别
				result = actorInfoService.insert(condition);
				
				if(result > 0)
				{
					//融云注册
					String token = RongCloudMethodUtil.AddUserInfo(memberId, "", "");
					ActorInfo umember = new ActorInfo();
					umember.setId(memberId);
					umember.setEasemobStatusCode(token);
					actorInfoService.update(umember);
					retInfo = getMyInfo(memberId);
					result = AppRetCode.NORMAL;
					msg = AppRetCode.NORMAL_TEXT;
				}else
				{
					result = AppRetCode.CREATE_ACCOUNT_FAILED;
					msg = AppRetCode.CREATE_ACCOUNT_FAILED_TEXT;
				}
				
			} else {
				retInfo = getMyInfo(member.getId());
			}
			writeJsonObject(response, result, msg, retInfo);
		} catch (Exception e) {
			e.printStackTrace();
			writeJsonObject(response, AppRetCode.SERVER_EXCEPTION, AppRetCode.SERVER_EXCEPTION_TEXT, null);
		}
	}
}
