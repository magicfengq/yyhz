package com.yyhz.sc.controller.app;

import java.text.DecimalFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yyhz.constant.AppRetCode;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.data.model.ActorCollect;
import com.yyhz.sc.data.model.ActorComment;
import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.data.model.ActorRole;
import com.yyhz.sc.data.model.AnnouceEnroll;
import com.yyhz.sc.data.model.AnnounceInfo;
import com.yyhz.sc.data.model.AuthenticateApply;
import com.yyhz.sc.data.model.BlackListInfo;
import com.yyhz.sc.data.model.ContentReportInfo;
import com.yyhz.sc.data.model.Feedback;
import com.yyhz.sc.data.model.SystemPictureInfo;
import com.yyhz.sc.services.ActorCollectService;
import com.yyhz.sc.services.ActorCommentService;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.ActorRoleService;
import com.yyhz.sc.services.AnnouceEnrollService;
import com.yyhz.sc.services.AnnounceInfoService;
import com.yyhz.sc.services.AuthenticateApplyService;
import com.yyhz.sc.services.BlackListInfoService;
import com.yyhz.sc.services.ContentReportInfoService;
import com.yyhz.sc.services.FeedbackService;
import com.yyhz.utils.DateFormatUtil;
import com.yyhz.utils.DateUtils;
import com.yyhz.utils.RongCloudMethodUtil;
import com.yyhz.utils.UUIDUtil;
import com.yyhz.utils.stream.config.Configurations;

import net.sf.json.JSONObject;

@RequestMapping(value = "api")
@Controller
public class AppMyController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(AppMyController.class);
	
	private static DecimalFormat formatter = new DecimalFormat("####.0");

	@Resource
	private ActorInfoService actorInfoService;
	@Resource
	private ActorRoleService actorRoleService;
	@Resource
	private AnnounceInfoService announceInfoService;
	@Resource
	private AnnouceEnrollService annouceEnrollService;
	@Resource
	private FeedbackService feedbackService;
	@Resource
	private ActorCommentService actorCommentService;
	@Resource
	private AuthenticateApplyService authenticateApplyService;
	@Resource
	private ActorCollectService actorCollectService;
	@Resource
	private BlackListInfoService blackListInfoService;
	@Resource
	private ContentReportInfoService contentReportInfoService;
	/**
	 * changePushState
	 * @Title: getMyInfo
	 * @Description: 获取我的信息
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getMyInfo")
	public void getMyInfo(HttpServletRequest request, HttpServletResponse response, String id) {
		
		if(StringUtils.isBlank(id)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "用户id为空", null);	
			return ;
		}
		
		Map<String, Object> ret = new HashMap<String, Object>();
		
		// 检索用户信息
		ActorInfo actorInfo = actorInfoService.selectById(id);
		if(actorInfo == null || actorInfo.getStatus() == 1) { // 状态 0正常；1已删除
			this.writeJsonObject(response, AppRetCode.ACCOUNT_NOT_EXIST, AppRetCode.ACCOUNT_NOT_EXIST_TEXT, null);	
			return ;
		}
		
		ret.put("id",actorInfo.getId());
		ret.put("name",actorInfo.getName());
		ret.put("realName",actorInfo.getRealName());
		ret.put("sex",actorInfo.getSex());
		ret.put("birthDay", DateUtils.getDateFormat(actorInfo.getBirthDay()));
		ret.put("city",actorInfo.getCity());
		ret.put("actCities",actorInfo.getActCities());
		ret.put("height",actorInfo.getHeight());
		ret.put("weight",actorInfo.getWeight());
		ret.put("shoesSize",actorInfo.getShoesSize());
		ret.put("size",actorInfo.getSize());
		ret.put("authenticateLevel",actorInfo.getAuthenticateLevel());
		//ret.put("level",actorInfo.getLevel());
		ret.put("introduction", actorInfo.getIntroduction());
		if(actorInfo.getHeadImgUrl() != null) {
			ret.put("headImgUrl",Configurations.buildDownloadUrl(actorInfo.getHeadImgUrl()));			
		}else {
			ret.put("headImgUrl","");						
		}
		ret.put("pushStatus",actorInfo.getPushStatus());
		
		// 计算平均分数
		//ret.put("avgScore", -1);
		float avgScore = actorCommentService.getAvgScore(id);
		ret.put("avgScore", avgScore);
		// 检索角色信息
		ActorRole condition = new ActorRole();
		condition.setActorId(actorInfo.getId());
		
		List<String> roles = new ArrayList<String>();
		List<ActorRole> roleList = actorRoleService.selectAll(condition, "selectAllWithName");
		for(ActorRole role : roleList) {
			roles.add(role.getRoleName());
		}
		
		ret.put("roleInfos", roles);
		
		
		// 我的发布数量
		AnnounceInfo annCondition = new AnnounceInfo();
		annCondition.setCreater(id);
		annCondition.setStatus(0); // 状态 0正常；1已删除；
		
		int announceCount = announceInfoService.selectCount(annCondition);
		ret.put("publishNumber", announceCount);

		// 我的报名数量
		AnnouceEnroll enrollCondition = new AnnouceEnroll();
		enrollCondition.setActorId(id);
		enrollCondition.setEnrollStatus(0); // 报名状态：0已报名；1已取消
		
		int enrollCount = annouceEnrollService.selectCount(enrollCondition);
		ret.put("applyNumber", enrollCount);
		//我的关注
		ActorCollect collectCondition = new ActorCollect();
		collectCondition.setCreater(id);
		int attentionCount = actorCollectService.selectCount(collectCondition);
		ret.put("attentionCount", attentionCount);
		//我的粉丝
		collectCondition = new ActorCollect();
		collectCondition.setActorId(id);
		int funsCount = actorCollectService.selectCount(collectCondition);
		ret.put("funsCount", funsCount);
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);	

	}
	
	/**
	 * @Title: updateMyInfo
	 * @Description: 更新个人信息
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "updateMyInfo")
	public void updateMyInfo(HttpServletRequest request, HttpServletResponse response, ActorInfo req, String roleIds) {
		
		if(StringUtils.isBlank(req.getId())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "用户id为空", null);	
			return ;
		}
		
		
		// 检索用户信息
		ActorInfo actorInfo = actorInfoService.selectById(req.getId());
		if(actorInfo == null || actorInfo.getStatus() == 1) { // 状态 0正常；1已删除
			this.writeJsonObject(response, AppRetCode.ACCOUNT_NOT_EXIST, AppRetCode.ACCOUNT_NOT_EXIST_TEXT, null);	
			return ;
		}
		
		if(StringUtils.isNotBlank(req.getBirthDayStr())) {
			req.setBirthDay(DateUtils.getDateFormat(DateFormatUtil.FormatDate(req.getBirthDayStr())));
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		int ret = actorInfoService.update(req);
		
		if(ret > 0) {
			if(StringUtils.isNotBlank(roleIds)) {
				
				ActorRole deleteRole = new ActorRole();
				deleteRole.setActorId(req.getId());
				actorRoleService.delete(deleteRole, "deleteByActorId");
				
				String[] roleList = StringUtils.split(StringUtils.trim(roleIds), ',');
				List<ActorRole> roles = new ArrayList<ActorRole>();
				for(String roleId : roleList) {
					ActorRole actorRole = new ActorRole();
					actorRole.setActorId(req.getId());
					actorRole.setRoleId(roleId);
					actorRole.setId(UUIDUtil.getUUID());
					
					roles.add(actorRole);
				}
				actorRoleService.insert(roles, "batchInsert");
			}
			
			// 重新查询用户信息，关联用户新更新的头像
			actorInfo = actorInfoService.selectById(req.getId());

			result.put("id",StringUtils.trimToEmpty(actorInfo.getId()));
			result.put("name",StringUtils.trimToEmpty(actorInfo.getName()));
			result.put("mobile",StringUtils.trimToEmpty(actorInfo.getMobile()));
			result.put("realName",StringUtils.trimToEmpty(actorInfo.getRealName()));
			result.put("sex",actorInfo.getSex() == null ? "" : actorInfo.getSex());
			result.put("birthDay", StringUtils.trimToEmpty(DateUtils.getDateFormat(actorInfo.getBirthDay())));
			result.put("city",StringUtils.trimToEmpty(actorInfo.getCity()));
			result.put("actCities",StringUtils.trimToEmpty(actorInfo.getActCities()));
			result.put("height",StringUtils.trimToEmpty(actorInfo.getHeight()));
			result.put("weight",StringUtils.trimToEmpty(actorInfo.getWeight()));
			result.put("shoesSize",StringUtils.trimToEmpty(actorInfo.getShoesSize()));
			result.put("size",StringUtils.trimToEmpty(actorInfo.getSize()));
			result.put("authenticateLevel",actorInfo.getAuthenticateLevel());
			//result.put("level", actorInfo.getLevel() == null ? "" : actorInfo.getLevel());
			result.put("introduction",actorInfo.getIntroduction());
			if(actorInfo.getHeadImgUrl() != null) {
				result.put("headImgUrl",Configurations.buildDownloadUrl(actorInfo.getHeadImgUrl()));			
			}else {
				result.put("headImgUrl","");						
			}
			result.put("pushStatus",actorInfo.getPushStatus() == null ? "" : actorInfo.getPushStatus());
		
			// 检索角色信息
			ActorRole condition = new ActorRole();
			condition.setActorId(actorInfo.getId());
			
			List<String> roles = new ArrayList<String>();
			List<ActorRole> roleList = actorRoleService.selectAll(condition, "selectAllWithName");
			for(ActorRole role : roleList) {
				roles.add(role.getRoleName());
			}
			
			result.put("roleInfos", roles);
			
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, result);	
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "服务器错误！", null);				
		}
	}
	
	/**
	 * 
	 * @Title: changePushState
	 * @Description: 消息推送开关控制
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "changePushState")
	public void changePushState(HttpServletRequest request, HttpServletResponse response, String id, Integer pushStatus) {
		
		if(StringUtils.isBlank(id)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "用户id为空", null);	
			return;
		}
		
		if(pushStatus == null || (pushStatus != 0 && pushStatus != 1)) { // 推送状态  0正常；1已关闭
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "pushStatus参数错误。推送状态  0正常；1已关闭", null);	
			return;
		}
	
		// 检索用户信息
		ActorInfo actorInfo = actorInfoService.selectById(id);
		if(actorInfo == null || actorInfo.getStatus() == 1) { // 状态 0正常；1已删除
			this.writeJsonObject(response, AppRetCode.ACCOUNT_NOT_EXIST, AppRetCode.ACCOUNT_NOT_EXIST_TEXT, null);	
			return;
		}
		
		// 更新用户状态
		ActorInfo condition = new ActorInfo();
		condition.setId(id);
		condition.setPushStatus(pushStatus);
		
		int result = actorInfoService.update(condition);
		if(result > 0) {
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("nowState", condition.getPushStatus());
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);	
		}else {
			this.writeJsonObject(response, AppRetCode.SERVER_EXCEPTION, AppRetCode.SERVER_EXCEPTION_TEXT, null);	
		}
	}
	
	/**
	 * 
	 * @Title: addfeedback
	 * @Description: 消息推送开关控制
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "addfeedback")
	public void addfeedback(HttpServletRequest request, HttpServletResponse response, Feedback req) {
		
		if(StringUtils.isBlank(req.getUserId())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "用户id为空", null);	
			return;
		}
		
		if(req.getTitle() == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数title为空", null);	
			return;
		}
		
		if(StringUtils.isBlank(req.getContent())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数content为空", null);	
			return;
		}
		
		req.setId(UUIDUtil.getUUID());
		req.setCreateDate(new Date());
		
		int ret = feedbackService.insert(req);
		if(ret > 0) {
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, null);	
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "服务器错误。", null);	
		}
	}

	/**
	 * 
	 * @Title: uploadMyAuthentication
	 * @Description: 上传认证信息
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "uploadMyAuthentication")
	public void uploadMyAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticateApply req,
			@RequestParam(required = false) MultipartFile imageFile1, @RequestParam(required = false) MultipartFile imageFile2,
			@RequestParam(required = false) MultipartFile imageFile3, @RequestParam(required = false) MultipartFile imageFile4) {
		
		if(StringUtils.isBlank(req.getActorId())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "用户id为空", null);	
			return;
		}
		
		// 检索用户信息
		ActorInfo actorInfo = actorInfoService.selectById(req.getActorId());
		if(actorInfo == null || actorInfo.getStatus() == 1) { // 状态 0正常；1已删除
			this.writeJsonObject(response, AppRetCode.ACCOUNT_NOT_EXIST, AppRetCode.ACCOUNT_NOT_EXIST_TEXT, null);	
			return;
		}
		
		SystemPictureInfo pInfo = null;
		if(imageFile1 != null) {
			pInfo = this.uploadFile2("authentication", imageFile1);
			if(pInfo != null) {
				req.setPhoto1(pInfo.getUuid());
			}
		}
		
		if(imageFile2 != null) {
			pInfo = this.uploadFile2("authentication", imageFile2);
			if(pInfo != null) {
				req.setPhoto2(pInfo.getUuid());
			}
		}
		
		if(imageFile3 != null) {
			pInfo = this.uploadFile2("authentication", imageFile3);
			if(pInfo != null) {
				req.setPhoto3(pInfo.getUuid());
			}
		}
		
		if(imageFile4 != null) {
			pInfo = this.uploadFile2("authentication", imageFile4);
			if(pInfo != null) {
				req.setPhoto4(pInfo.getUuid());
			}
		}

		AuthenticateApply condition = new AuthenticateApply();
		condition.setActorId(req.getActorId());
		AuthenticateApply applyInfo = authenticateApplyService.selectEntity(condition);
		
		req.setApplyTime(new Date());

		int ret = 0;
		if(applyInfo == null) {
			req.setId(UUIDUtil.getUUID());
			ret = authenticateApplyService.insert(req);

		}else {
			req.setId(applyInfo.getId());
			ret = authenticateApplyService.update(req);
		}
		
		if(ret > 0) {
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, null);	
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "服务器错误。", null);	
		}
	}
	
	/**
	 * 
	 * @Title: getMyAuthentication
	 * @Description: 获取认证信息
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getMyAuthentication")
	public void getMyAuthentication(HttpServletRequest request, HttpServletResponse response, String id) {
		
		if(StringUtils.isBlank(id)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "用户id为空", null);	
			return;
		}
		
		// 检索用户信息
		ActorInfo actorInfo = actorInfoService.selectById(id);
		if(actorInfo == null || actorInfo.getStatus() == 1) { // 状态 0正常；1已删除
			this.writeJsonObject(response, AppRetCode.ACCOUNT_NOT_EXIST, AppRetCode.ACCOUNT_NOT_EXIST_TEXT, null);	
			return;
		}

		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("id", actorInfo.getId());
		
		AuthenticateApply condition = new AuthenticateApply();
		condition.setActorId(id);
		List<AuthenticateApply> authList = authenticateApplyService.selectAll(condition, "selectAllWithUrl");
		
		
		Map<String, Object> authMap = new HashMap<String, Object>();
		authMap.put("authenticateLevel", actorInfo.getAuthenticateLevel());
		if(authList != null && authList.size() > 0) {
			AuthenticateApply auth = authList.get(0);
			
			authMap.put("id",auth.getId());
			authMap.put("realName",auth.getRealName());
			authMap.put("mobile",auth.getMobile());
			authMap.put("idcard",auth.getIdcard());
			authMap.put("checkStatus",auth.getCheckStatus());

			if(StringUtils.isNotBlank(auth.getPhoto1())){
				authMap.put("photo1",Configurations.buildDownloadUrl(auth.getPhoto1()));			
			}
			if(StringUtils.isNotBlank(auth.getPhoto2())){
				authMap.put("photo2",Configurations.buildDownloadUrl(auth.getPhoto2()));			
			}
			if(StringUtils.isNotBlank(auth.getPhoto3())){
				authMap.put("photo3",Configurations.buildDownloadUrl(auth.getPhoto3()));			
			}
			if(StringUtils.isNotBlank(auth.getPhoto4())){
				authMap.put("photo4",Configurations.buildDownloadUrl(auth.getPhoto4()));			
			}
		}
		
		ret.put("authInfo", authMap);

		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);	
	}
	
	/**
	 * 
	 * @Title: getMyAnttentionList
	 * @Description: 获取关注信息
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getMyAnttentionList")
	public void getMyAnttentionList(HttpServletRequest request, HttpServletResponse response, String id, Integer page, Integer pageSize, String keyword) {
		
		if(StringUtils.isBlank(id)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "用户id为空", null);	
			return;
		}
		
		if (page == null) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少page参数！", null);
			return;
		}
		
		if (pageSize == null) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少pageSize参数！", null);
			return;
		}

		// 检索用户信息
		ActorInfo actorInfo = actorInfoService.selectById(id);
		if(actorInfo == null || actorInfo.getStatus() == 1) { // 状态 0正常；1已删除
			this.writeJsonObject(response, AppRetCode.ACCOUNT_NOT_EXIST, AppRetCode.ACCOUNT_NOT_EXIST_TEXT, null);	
			return;
		}

		PageInfo<ActorCollect> pageInfo = new PageInfo<ActorCollect>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);

		
		ActorCollect condition = new ActorCollect();
		condition.setCreater(id);
		condition.setKeyword(keyword);
		actorCollectService.selectAll(condition, pageInfo, "selectAllDetails");
		Map<String, Object> ret = new HashMap<String, Object>();
		List<Map<String, Object>> actorCollectList = new ArrayList<Map<String, Object>>();
		for(ActorCollect item : pageInfo.getRows()) {
			Map<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put("id", item.getActorId());
			itemMap.put("name", item.getName());
			itemMap.put("authenticateLevel", item.getAuthenticateLevel());
			if(StringUtils.isNotEmpty(item.getHeadImgUrl())) {
				itemMap.put("headImgUrl", Configurations.buildDownloadUrl(item.getHeadImgUrl()));
			}
			itemMap.put("roles", StringUtils.split(item.getRoleInfos(), ","));
			
			actorCollectList.add(itemMap);
		}
		
		ret.put("rows", actorCollectList);
		ret.put("page", pageInfo.getPage());
		ret.put("pageSize", pageInfo.getPageSize());
		ret.put("total", pageInfo.getTotal());

		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);	
	}
	
	/**
	 * 
	 * @Title: changeAttentionState
	 * @Description: 更改关注状态
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "changeAttentionState")
	public void changeAttentionState(HttpServletRequest request, HttpServletResponse response, ActorCollect req, Integer type) {
		
		if(StringUtils.isBlank(req.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数creater为空", null);	
			return;
		}
		
		if(StringUtils.isBlank(req.getActorId())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数actorId为空", null);	
			return;
		}
		
		if(type == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数type为空", null);	
			return;
		}

		ActorInfo actorInfo = actorInfoService.selectById(req.getActorId());
		if(actorInfo == null || actorInfo.getStatus() == 1) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "关注的人不存在", null);
			return;		
		}
		
		actorInfo = actorInfoService.selectById(req.getCreater());
		if(actorInfo == null || actorInfo.getStatus() == 1) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "创建者不存在", null);
			return;		
		}

		
		ActorCollect condition = new ActorCollect();
		condition.setCreater(req.getCreater());
		condition.setActorId(req.getActorId());
		
		Map<String, Object> result = new HashMap<String ,Object>();
		
		ActorCollect actorCollect = actorCollectService.selectEntity(condition);
		if(type == 1) {// 关注
			if(actorCollect == null) {
				condition.setId(UUIDUtil.getUUID());
				condition.setCreateTime(new Date());
				
				actorCollectService.insert(condition);
			}
			
			result.put("nowState", 1);
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, result);	

		}else {
			if(actorCollect != null) {
				actorCollectService.delete(actorCollect);
			}
			
			result.put("nowState", 0);
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, result);	
		}
	}
	
	/**
	 * 
	 * @Title: closeMyAnnouncement
	 * @Description: 更改关注状态
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "closeMyAnnouncement")
	public void closeMyAnnouncement(HttpServletRequest request, HttpServletResponse response, String id, String creater) {
		
		if(StringUtils.isBlank(id)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数id为空", null);	
			return;
		}
		
		if(StringUtils.isBlank(creater)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数creater为空", null);	
			return;
		}
		
		AnnounceInfo announce = announceInfoService.selectById(id);
		if(announce == null || announce.getStatus() == 1 || !StringUtils.equals(announce.getCreater(), creater)) { // 已经删除
			this.writeJsonObject(response, AppRetCode.ERROR, "通告不存在", null);
			return;
		}

		AnnounceInfo condition = new AnnounceInfo();
		condition.setId(id);
		condition.setEnrollStatus(1); // 关闭
		
		int ret = announceInfoService.update(condition);
		if(ret > 0) {
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, null);
			
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "服务器错误！", null);
		}
	}
	
	/**
	 * 
	 * @Title: manageApplyList
	 * @Description: 报名管理
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "manageApplyList")
	public void manageApplyList(HttpServletRequest request, HttpServletResponse response,
			String id, Integer page, Integer pageSize, Integer checkStatus) {
		
		if(StringUtils.isBlank(id)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数id为空", null);	
			return;
		}
		
		PageInfo<AnnouceEnroll> pageInfo = new PageInfo<AnnouceEnroll>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);

		
		AnnouceEnroll condition = new AnnouceEnroll();
		condition.setAnnounceId(id);
		condition.setCheckStatus(checkStatus);
		
		annouceEnrollService.selectAll(condition, pageInfo, "selectAllDetail");
		
		Map<String ,Object> ret = new HashMap<String, Object>();
		ret.put("page", pageInfo.getPage());
		ret.put("pageSize", pageInfo.getPageSize());
		ret.put("total", pageInfo.getTotal());
		
		List<Map<String, Object>> enrollList = new ArrayList<Map<String, Object>>();
		for(AnnouceEnroll enroll : pageInfo.getRows()) {
			Map<String, Object> enrollMap = new HashMap<String, Object>();
			
			enrollMap.put("id",enroll.getId());
			enrollMap.put("announceId",enroll.getAnnounceId());
			enrollMap.put("actorId",enroll.getActorId());
			enrollMap.put("createTime",enroll.getCreateTime());
			enrollMap.put("checkTime",enroll.getCheckTime());
			enrollMap.put("checkStatus",enroll.getCheckStatus());
			enrollMap.put("enrollStatus",enroll.getEnrollStatus());
			if(enroll.getActorImgUrl() != null) {
				enrollMap.put("actorImgUrl",Configurations.buildDownloadUrl(enroll.getActorImgUrl()));			
			}
			enrollMap.put("actorName",enroll.getActorName());
			enrollMap.put("authenticateLevel",enroll.getAuthenticateLevel());
			enrollMap.put("avgScore",enroll.getAvgScore());
			
			// 查询已接受的报名人的评价状态
			ActorComment commentCondition = new ActorComment();
			commentCondition.setActorId(enroll.getActorId());
			commentCondition.setCreater(enroll.getAnnounceCreater());
			commentCondition.setAnnounceId(enroll.getAnnounceId());

			ActorComment actorComment = actorCommentService.selectEntity(commentCondition);
			if(actorComment != null) {
				enrollMap.put("commentStatus",1);
			}else {
				enrollMap.put("commentStatus",0);
			}

			enrollList.add(enrollMap);
		}
		
		ret.put("rows", enrollList);

		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);	
	}
	
	/**
	 * 
	 * @Title: AuditingApplies
	 * @Description: 审核报名
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "AuditingApplies")
	public void AuditingApplies(HttpServletRequest request, HttpServletResponse response, String id, Integer checkStatus) {
		
		if(StringUtils.isBlank(id)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数id为空", null);	
			return;
		}
		
		if(checkStatus == null || checkStatus < 1 || checkStatus > 2) { // 审核状态 1通过；2拒绝；审核人是通告的发布人
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数checkStatus错误。1通过；2拒绝", null);	
			return;
		}
		
		AnnouceEnroll enroll = annouceEnrollService.selectById(id);
		if(enroll == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "报名申请不存在，id错误！", null);			
		}
		
		AnnounceInfo announceInfo = announceInfoService.selectById(enroll.getAnnounceId());
		if(announceInfo == null || announceInfo.getStatus() == 1) // 不存在或已经删除
		{
			this.writeJsonObject(response, AppRetCode.ERROR, "要审核报名的通告不存在！", null);
			return;
		}

		
		AnnouceEnroll condition = new AnnouceEnroll();
		condition.setId(id);
		condition.setCheckStatus(checkStatus);
		condition.setCheckTime(new Date());
		
		int ret = annouceEnrollService.update(condition);
		if(ret > 0) {
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, null);
			
			// 发送通知信息

			String checkMessage = checkStatus == 1 ? "审核通过" : "审核没通过";
			
			String message = String.format("您报名的“%s”通告%s。[点击查看]", StringUtils.trimToEmpty(announceInfo.getTitle()), checkMessage);
			
			Map<String, Object> ext = new HashMap<String, Object>();
			ext.put("fromType", "annouceNotify");
			ext.put("atid", announceInfo.getId());
			ext.put("type", announceInfo.getType());
			ext.put("publicType", StringUtils.trimToEmpty(announceInfo.getPublicTypeNames()));

			//Object resp = EasemobUtil.sendAnnounceMessage(enroll.getActorId(), message, ext);
			
			logger.debug("---------------------- send message resp -----------------------------------");
			//logger.debug(resp.toString());
			
			String[] targetIds = {enroll.getActorId()};

			JSONObject json = JSONObject.fromObject(ext);
			RongCloudMethodUtil.privateMessage("announce",message,  targetIds, json.toString());

		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "服务器错误！", null);
		}
	}
	
	/**
	 * 
	 * @Title: publishComment
	 * @Description: 发布评价
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "publishComment")
	public void publishComment(HttpServletRequest request, HttpServletResponse response, ActorComment req) {
		if(StringUtils.isBlank(req.getActorId())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数actorId为空", null);	
			return;
		}

		if(req.getType() == null|| req.getType() < 0 || req.getType() > 1) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数type错误", null);	
			return;
		}

		if(StringUtils.isBlank(req.getAnnounceId())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数announceId为空", null);	
			return;
		}

		if(StringUtils.isBlank(req.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数creater为空", null);	
			return;
		}

		if(req.getScore() == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数score为空", null);	
			return;
		}
		
		// 检查通告是否已经关闭
		AnnounceInfo annInfo = announceInfoService.selectById(req.getAnnounceId());
		if(annInfo == null || annInfo.getStatus() == 1) { // 已删除
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "评价的通告不存在", null);	
			return;
		} 
		
		/*
		 * 不需要验证通告是否关闭，只要报名并通过审核就可以互相评论
		if(annInfo.getEnrollStatus() == 0) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "通告还没有关闭，不能评价", null);	
			return;
		}
		*/
		
		// 检查是否报名了此通告
		AnnouceEnroll enrollCondition = new AnnouceEnroll();
		enrollCondition.setAnnounceId(req.getAnnounceId());
		enrollCondition.setType(req.getType());
		if(req.getType() == 0) { // 评论类型：0评论发通告的人；
			enrollCondition.setActorId(req.getCreater());
		}else { // 1评论接通告的人
			enrollCondition.setActorId(req.getActorId());
		}
		
		AnnouceEnroll enrollInfo = annouceEnrollService.selectEntity(enrollCondition);
		if(enrollInfo == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "没有报名此通告，不能评价", null);	
			return;
		}

		if(enrollInfo.getCheckStatus() == 0) { // 审核没通过
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "报名通告没有通过，不能评价", null);	
			return;
		}
		
		ActorComment condition = new ActorComment();
		condition.setActorId(req.getActorId());
		condition.setCreater(req.getCreater());
		condition.setAnnounceId(req.getAnnounceId());

		ActorComment actorComment = actorCommentService.selectEntity(condition);
		if(actorComment != null) {
			this.writeJsonObject(response, AppRetCode.ERROR, "已经发布过评论", actorComment);
			return;
		}

		req.setId(UUIDUtil.getUUID());
		req.setCreateTime(new Date());
		req.setStatus(0);

		int ret = actorCommentService.insert(req);
		if(ret > 0) {
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, null);
			
			String createrName = null;
			ActorInfo actorInfo = actorInfoService.selectById(req.getCreater());
			if(actorInfo != null) {
				createrName = StringUtils.trimToEmpty(actorInfo.getName());
			}
			
			String message = null;
			// 发送通知消息
			if(req.getType() == 0) {
				message = String.format("“%s”对您发布的“%s”通告进行了评价。[点击查看] ", createrName, StringUtils.trimToEmpty(annInfo.getTitle()));
			}else {
				message = String.format("“%s”对您报名参与的“%s”通告进行了评价。[点击查看] ", createrName, StringUtils.trimToEmpty(annInfo.getTitle()));
			}
			
			Map<String, Object> ext = new HashMap<String, Object>();
			ext.put("fromType", "annouceCommentNotify");
			ext.put("actorId", req.getActorId());
			ext.put("type", req.getType());
			//Object resp = EasemobUtil.sendAnnounceMessage(req.getActorId(), message, ext);
			logger.debug("---------------------- send message resp -----------------------------------");
			//logger.debug(resp.toString());
			String[] targetIds = {req.getActorId()};

			JSONObject json = JSONObject.fromObject(ext);
			RongCloudMethodUtil.privateMessage("announce",message,  targetIds, json.toString());

		}else {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "插入评价失败", null);			
		}
	}

	/**
	 * 
	 * @Title: getMyApplyList
	 * @Description: 我的报名
	 * @param enrollActorId 我的id
	 * @param page 分页查询的页码
	 * @param pageSize 每页记录数
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getMyApplyList")
	public void getMyApplyList(HttpServletRequest request, HttpServletResponse response, String id, Integer page, Integer pageSize) {
			
		if (page == null) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少page参数！", null);
			return;
		}
		
		if (pageSize == null) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少pageSize参数！", null);
			return;
		}
		
		if(StringUtils.isBlank(id)) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少id参数！", null);
			return;
		}


		PageInfo<AnnounceInfo> pageInfo = new PageInfo<AnnounceInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);
		
		AnnounceInfo condition = new AnnounceInfo();
		condition.setEnrollActorId(id); 
		condition.setSort("createTime");
		
		announceInfoService.selectAll(condition, pageInfo, "selectMyEnroll");
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		result.put("rows", dataList);
		result.put("page", pageInfo.getPage());
		result.put("pageSize", pageInfo.getPageSize());
		result.put("total", pageInfo.getTotal());

		if(pageInfo.getRows() != null && !pageInfo.getRows().isEmpty())
		{
			for(AnnounceInfo annInfo : pageInfo.getRows())
			{
				Map<String, Object> annMap = new HashMap<String, Object>();

				annMap.put("id", annInfo.getId());
				annMap.put("type", annInfo.getType());
				annMap.put("sex", annInfo.getSex());
				annMap.put("price", annInfo.getPrice());
				annMap.put("title", annInfo.getTitle());
				if(annInfo.getType() != null && annInfo.getType() == 1) { // 艺人 使用艺人类型
					annMap.put("publicType", annInfo.getName());					
				}else {
					annMap.put("publicType", annInfo.getPublicTypeNames());					
				}
				annMap.put("city", annInfo.getCity());
				annMap.put("showTime", DateUtils.getDateFormat(annInfo.getShowTime()));
				annMap.put("showTime", annInfo.getShowTime());
				annMap.put("createTime", DateUtils.getDateTimeMinFormat(annInfo.getCreateTime()));
				annMap.put("name", annInfo.getName());
				annMap.put("address", annInfo.getAddress());
				annMap.put("detail", annInfo.getDetail());
				if(annInfo.getEnrollNum() == null) {
					annMap.put("enrollNum", new Integer(0));			
				}else {
					annMap.put("enrollNum", annInfo.getEnrollNum());
				}
				
				if(StringUtils.isNotBlank(annInfo.getHeadImgUrl())) {
					annMap.put("headImgUrl", Configurations.buildDownloadUrl(annInfo.getHeadImgUrl()));					
				}
				annMap.put("createName", annInfo.getCreaterName());
				annMap.put("authenticateLevel", annInfo.getAuthenticateLevel());
				annMap.put("enrollActorCheckState", annInfo.getEnrollActorCheckState());
					
				dataList.add(annMap);
			}
		}
		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, result);
	}
	
	/**
	 * 
	 * @Title: getMyCommentList
	 * @Description: 我的评价列表
	 * @param id 我的id
	 * @param page 分页查询的页码
	 * @param pageSize 每页记录数
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getMyCommentList")
	public void getMyCommentList(HttpServletRequest request, HttpServletResponse response, String id, Integer type, Integer page, Integer pageSize) {
			
		if (page == null) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少page参数！", null);
			return;
		}
		
		if (pageSize == null) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少pageSize参数！", null);
			return;
		}
		
		if(StringUtils.isBlank(id)) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少id参数！", null);
			return;
		}
		
//		if(type == null) {
//			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少type参数！", null);
//			return;			
//		}

		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		ActorComment condition = new ActorComment();
		condition.setActorId(id);
		condition.setType(type);
		int commentCount = actorCommentService.selectCount(condition);
		result.put("commentCount", commentCount);
		
		float avgScore = actorCommentService.getAvgScore(condition);
		result.put("avgScore", avgScore);
		
		PageInfo<ActorComment> pageInfo = new PageInfo<ActorComment>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);

		condition.setSort("createTime");
		actorCommentService.selectAll(condition, pageInfo, "selectMyComment");
		
		result.put("rows", dataList);
		result.put("page", pageInfo.getPage());
		result.put("pageSize", pageInfo.getPageSize());
		result.put("total", pageInfo.getTotal());
		
		for(ActorComment comment : pageInfo.getRows()) {
			Map<String, Object> commentMap = new HashMap<String, Object>();
			commentMap.put("id",comment.getId());
			commentMap.put("actorId",comment.getActorId());
			commentMap.put("type",comment.getType());
			commentMap.put("creater",comment.getCreater());
			commentMap.put("createTime",DateUtils.getDateTimeMinFormat(comment.getCreateTime()));
			commentMap.put("announceId",comment.getAnnounceId());
			commentMap.put("announceType",comment.getAnnounceType());
			commentMap.put("announcePublicType",comment.getAnnouncePublicTypeName());
			commentMap.put("content",comment.getContent());
			commentMap.put("score",comment.getScore());
			commentMap.put("status",comment.getStatus());
			commentMap.put("announceTitle",comment.getAnnounceTitle());
			commentMap.put("createrName",comment.getCreaterName());
			if(StringUtils.isNotBlank(comment.getCreaterHeadUrl())) {
				commentMap.put("headImgUrl", Configurations.buildDownloadUrl(comment.getCreaterHeadUrl()));					
			}
			
			dataList.add(commentMap);
		}
		

		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, result);
	}
	/**
	 * 
	 * @Title: getMyAnttentionList
	 * @Description: 获取关注信息
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getMyFunsList")
	public void getMyFunsList(HttpServletRequest request, HttpServletResponse response, String id, Integer page, Integer pageSize, String keyword) {
		
		if(StringUtils.isBlank(id)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "用户id为空", null);	
			return;
		}
		
		if (page == null) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少page参数！", null);
			return;
		}
		
		if (pageSize == null) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少pageSize参数！", null);
			return;
		}

		// 检索用户信息
		ActorInfo actorInfo = actorInfoService.selectById(id);
		if(actorInfo == null || actorInfo.getStatus() == 1) { // 状态 0正常；1已删除
			this.writeJsonObject(response, AppRetCode.ACCOUNT_NOT_EXIST, AppRetCode.ACCOUNT_NOT_EXIST_TEXT, null);	
			return;
		}

		PageInfo<ActorCollect> pageInfo = new PageInfo<ActorCollect>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);

		
		ActorCollect condition = new ActorCollect();
		condition.setActorId(id);
		condition.setKeyword(keyword);
		actorCollectService.selectAll(condition, pageInfo, "selectAllFansDetails");
		Map<String, Object> ret = new HashMap<String, Object>();
		List<Map<String, Object>> actorCollectList = new ArrayList<Map<String, Object>>();
		for(ActorCollect item : pageInfo.getRows()) {
			Map<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put("id", item.getCreater());
			itemMap.put("name", item.getName());
			itemMap.put("authenticateLevel", item.getAuthenticateLevel());
			if(StringUtils.isNotEmpty(item.getHeadImgUrl())) {
				itemMap.put("headImgUrl", Configurations.buildDownloadUrl(item.getHeadImgUrl()));
			}
			itemMap.put("roles", StringUtils.split(item.getRoleInfos(), ","));
			
			actorCollectList.add(itemMap);
		}
		
		ret.put("rows", actorCollectList);
		ret.put("page", pageInfo.getPage());
		ret.put("pageSize", pageInfo.getPageSize());
		ret.put("total", pageInfo.getTotal());

		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);	
	}
	/**
	 * 
	 * @Title: getMyAuthentication
	 * @Description: 获取认证信息
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getMyAuthenticationNew")
	public void getMyAuthenticationNew(HttpServletRequest request, HttpServletResponse response, String id) {
		
		if(StringUtils.isBlank(id)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "用户id为空", null);	
			return;
		}
		
		// 检索用户信息
		ActorInfo actorInfo = actorInfoService.selectById(id);
		if(actorInfo == null || actorInfo.getStatus() == 1) { // 状态 0正常；1已删除
			this.writeJsonObject(response, AppRetCode.ACCOUNT_NOT_EXIST, AppRetCode.ACCOUNT_NOT_EXIST_TEXT, null);	
			return;
		}

		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("id", actorInfo.getId());
		ret.put("realName",actorInfo.getRealName());
		ret.put("idcard",actorInfo.getIdcard());
		ret.put("mobile",actorInfo.getMobile());
		//ret.put("level",actorInfo.getLevel());
		ret.put("level",actorInfo.getAuthenticateLevel());
		if(StringUtils.isEmpty(actorInfo.getMobile())){			
			ret.put("type","0");
		}else{
			ret.put("type","1");
		}
		
		AuthenticateApply condition = new AuthenticateApply();
		condition.setActorId(id);
		List<AuthenticateApply> authList = authenticateApplyService.selectAll(condition, "selectAllWithUrl");
		if(authList != null && authList.size() > 0) {
			AuthenticateApply auth = authList.get(0);
			if(StringUtils.isNotBlank(auth.getPhoto1())){
				ret.put("photo1",Configurations.buildDownloadUrl(auth.getPhoto1()));			
			}
			if(StringUtils.isNotBlank(auth.getPhoto2())){
				ret.put("photo2",Configurations.buildDownloadUrl(auth.getPhoto2()));			
			}
			if(StringUtils.isNotBlank(auth.getPhoto3())){
				ret.put("photo3",Configurations.buildDownloadUrl(auth.getPhoto3()));			
			}
			int resultCheckStatus = 0;
			int level = auth.getUserCurrentLevel();
			int checkStatus = auth.getCheckStatus();	
			//实名未提交 0 实名待审核1 实名通过2 实名不通过3 企业待审核4 企业通过5 企业不通过6  
			if(level == 1&&checkStatus==0){
				resultCheckStatus = 1;
			}else if(level == 1&&checkStatus==1){
				resultCheckStatus = 2;
			}else if(level == 1&&checkStatus==2){
				resultCheckStatus = 3;
			}else if(level == 2&&checkStatus==0){
				resultCheckStatus = 4;
			}else if(level == 2&&checkStatus==1){
				resultCheckStatus = 5;
			}else if(level == 2&&checkStatus==2){
				resultCheckStatus = 6;
			}
			ret.put("checkStatus",resultCheckStatus);	
			
		}else{
			ret.put("checkStatus",0);	
		}
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);	
		
	}
	/**
	 * 
	 * @Title: realPersonAuthentication
	 * @Description: 实人认证
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "realPersonAuthentication")
	public void realPersonAuthentication(HttpServletRequest request, HttpServletResponse response, ActorInfo req) {
		
		if(StringUtils.isBlank(req.getId())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "用户id为空", null);	
			return;
		}
		if(StringUtils.isBlank(req.getRealName())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "真实姓名为空", null);	
			return;
		}
		if(StringUtils.isBlank(req.getIdcard())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "身份证为空", null);	
			return;
		}
		if(StringUtils.isBlank(req.getMobile())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "电话号为空", null);	
			return;
		}
		/*if(StringUtils.isBlank(req.getPassword())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "密码为空", null);	
			return;
		}*/
		// 检索用户信息
		ActorInfo param = new ActorInfo();
		param.setId(req.getId());
		param.setRealName(req.getRealName());
		param.setIdcard(req.getIdcard());
		param.setMobile(req.getMobile());
		//param.setLevel(1);//实名认证
		param.setAuthenticateLevel(1);//实名认证
		int result = actorInfoService.update(param);
		
		if(result > 0) {
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, null);	
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "服务器错误。", null);	
		}
	}
	/**
	 * 
	 * @Title: companyAuthentication
	 * @Description: 企业认证
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "companyAuthentication")
	public void companyAuthentication(HttpServletRequest request, HttpServletResponse response, ActorInfo req,
			@RequestParam(required = false) MultipartFile imageFile,String imageFileAgo) {
			
		if(StringUtils.isBlank(req.getId())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "用户id为空", null);	
			return;
		}
		if(StringUtils.isBlank(req.getRealName())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "真实姓名为空", null);	
			return;
		}
		if(StringUtils.isBlank(req.getIdcard())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "身份证为空", null);	
			return;
		}
		if(StringUtils.isBlank(req.getMobile())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "电话号为空", null);	
			return;
		}
		if(imageFile == null&&StringUtils.isBlank(imageFileAgo)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "营业执照图片为空", null);	
			return;
		}
		
		// 检索用户信息
		ActorInfo actorInfo = actorInfoService.selectById(req.getId());
		if(actorInfo == null || actorInfo.getStatus() == 1) { // 状态 0正常；1已删除
			this.writeJsonObject(response, AppRetCode.ACCOUNT_NOT_EXIST, AppRetCode.ACCOUNT_NOT_EXIST_TEXT, null);	
			return;
		}
		//上传执照图片
		String uuid = "";
		if(imageFile!=null){			
			SystemPictureInfo pInfo = this.uploadFile2("authentication", imageFile);
			if(pInfo != null) {
				uuid=pInfo.getUuid();
			}
		}
		//查询是否已经申请过
		AuthenticateApply param = new AuthenticateApply();
		param.setActorId(req.getId());
		
		AuthenticateApply applyInfo = authenticateApplyService.selectEntity(param);
		int ret = 0;
		if(applyInfo == null) {
			applyInfo = new AuthenticateApply();
			applyInfo.setId(UUIDUtil.getUUID());
			applyInfo.setActorId(req.getId());
			applyInfo.setPhoto3(uuid);
			applyInfo.setUserCurrentLevel(2);
			applyInfo.setApplyTime(new Date());
			applyInfo.setRealName(req.getRealName());
			applyInfo.setIdcard(req.getIdcard());
			applyInfo.setMobile(req.getMobile());
			applyInfo.setCheckStatus(0);
			//插入
			ret = authenticateApplyService.insert(applyInfo);
		}else {
			if(imageFile!=null){
				applyInfo.setPhoto3(uuid);
			} 	
			applyInfo.setUserCurrentLevel(2);
			applyInfo.setApplyTime(new Date());
			applyInfo.setRealName(req.getRealName());
			applyInfo.setIdcard(req.getIdcard());
			applyInfo.setMobile(req.getMobile());
			applyInfo.setCheckStatus(0);
			//更新
			ret = authenticateApplyService.update(applyInfo);
		}

		if(ret > 0) {
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, null);	
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "服务器错误。", null);	
		}
	}
	/**
	 * 
	 * @Title: idCardAuthentication
	 * @Description: 身份认证
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "idCardAuthentication")
	public void idCardAuthentication(HttpServletRequest request, HttpServletResponse response, ActorInfo req,
			@RequestParam(required = false) MultipartFile imageFile1,@RequestParam(required = false) MultipartFile imageFile2,String imageFileAgo1,String imageFileAgo2) {
			
		if(StringUtils.isBlank(req.getId())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "用户id为空", null);	
			return;
		}
		if(StringUtils.isBlank(req.getRealName())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "真实姓名为空", null);	
			return;
		}
		if(StringUtils.isBlank(req.getIdcard())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "身份证为空", null);	
			return;
		}
		if(StringUtils.isBlank(req.getMobile())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "电话号为空", null);	
			return;
		}
		if(imageFile1 == null&&StringUtils.isBlank(imageFileAgo1)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "身份证正面为空", null);	
			return;
		}
		if(imageFile2 == null&&StringUtils.isBlank(imageFileAgo2)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "身份证背面为空", null);	
			return;
		}
		
		// 检索用户信息
		ActorInfo actorInfo = actorInfoService.selectById(req.getId());
		if(actorInfo == null || actorInfo.getStatus() == 1) { // 状态 0正常；1已删除
			this.writeJsonObject(response, AppRetCode.ACCOUNT_NOT_EXIST, AppRetCode.ACCOUNT_NOT_EXIST_TEXT, null);	
			return;
		}
		//上传身份证正面图片
		String uuid1 = "";
		if(imageFile1!=null){			
			SystemPictureInfo pInfo = this.uploadFile2("authentication", imageFile1);
			if(pInfo != null) {
				uuid1=pInfo.getUuid();
			}
		}
		//上传身份证背面图片
		String uuid2 = "";
		if(imageFile2!=null){			
			SystemPictureInfo pInfo = this.uploadFile2("authentication", imageFile2);
			if(pInfo != null) {
				uuid2=pInfo.getUuid();
			}
		}
		//查询是否已经申请过
		AuthenticateApply param = new AuthenticateApply();
		param.setActorId(req.getId());
		
		AuthenticateApply applyInfo = authenticateApplyService.selectEntity(param);
		int ret = 0;
		if(applyInfo == null) {
			applyInfo = new AuthenticateApply();
			applyInfo.setId(UUIDUtil.getUUID());
			applyInfo.setActorId(req.getId());
			applyInfo.setPhoto1(uuid1);
			applyInfo.setPhoto2(uuid2);
			applyInfo.setUserCurrentLevel(1);
			applyInfo.setApplyTime(new Date());
			applyInfo.setRealName(req.getRealName());
			applyInfo.setIdcard(req.getIdcard());
			applyInfo.setMobile(req.getMobile());
			applyInfo.setCheckStatus(0);
			//插入
			ret = authenticateApplyService.insert(applyInfo);
		}else {
			if(imageFile1!=null){
				applyInfo.setPhoto1(uuid1);
			} 	
			if(imageFile2!=null){
				applyInfo.setPhoto2(uuid2);
			} 
			applyInfo.setUserCurrentLevel(1);
			applyInfo.setApplyTime(new Date());
			applyInfo.setRealName(req.getRealName());
			applyInfo.setIdcard(req.getIdcard());
			applyInfo.setMobile(req.getMobile());
			applyInfo.setCheckStatus(0);
			//更新
			ret = authenticateApplyService.update(applyInfo);
		}

		if(ret > 0) {
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, null);	
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "服务器错误。", null);	
		}
	}
	/**
	 * 
	 * @Title: getMyBlackList
	 * @Description: 获取黑名单
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getMyBlackList")
	public void getMyBlackList(HttpServletRequest request, HttpServletResponse response, Integer page, Integer pageSize, String actorId) {
		
		if(StringUtils.isBlank(actorId)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "用户id为空", null);	
			return;
		}
		
		if (page == null) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少page参数！", null);
			return;
		}
		
		if (pageSize == null) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少pageSize参数！", null);
			return;
		}

		// 检索用户信息
		ActorInfo actorInfo = actorInfoService.selectById(actorId);
		if(actorInfo == null || actorInfo.getStatus() == 1) { // 状态 0正常；1已删除
			this.writeJsonObject(response, AppRetCode.ACCOUNT_NOT_EXIST, AppRetCode.ACCOUNT_NOT_EXIST_TEXT, null);	
			return;
		}

		PageInfo<BlackListInfo> pageInfo = new PageInfo<BlackListInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);

		
		BlackListInfo condition = new BlackListInfo();
		condition.setActorId(actorId);
		blackListInfoService.selectAll(condition, pageInfo);
		Map<String, Object> ret = new HashMap<String, Object>();
		List<Map<String, Object>> blackActorList = new ArrayList<Map<String, Object>>();
		for(BlackListInfo item : pageInfo.getRows()) {
			ActorInfo blackActorInfo = actorInfoService.selectById(item.getBlackActorId());
			Map<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put("id", blackActorInfo.getId());
			itemMap.put("name", blackActorInfo.getName());
			
			if(StringUtils.isNotEmpty(blackActorInfo.getHeadImgUrl())) {
				itemMap.put("headImgUrl", Configurations.buildDownloadUrl(blackActorInfo.getHeadImgUrl()));
			}
			
			blackActorList.add(itemMap);
		}
		
		ret.put("rows", blackActorList);
		ret.put("page", pageInfo.getPage());
		ret.put("pageSize", pageInfo.getPageSize());
		ret.put("total", pageInfo.getTotal());

		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);	
	}
	/**
	 * 
	 * @Title: addBlackList
	 * @Description: 加入黑名单
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "addBlackList")
	public void addBlackList(HttpServletRequest request, HttpServletResponse response,String actorId,String blackActorId) {
		
		if(StringUtils.isBlank(actorId)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "用户id为空", null);	
			return;
		}
		
		if (StringUtils.isBlank(blackActorId)) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "拉黑用户id为空！", null);
			return;
		}
		
		
		// 检索用户信息
		ActorInfo actorInfo = actorInfoService.selectById(actorId);
		if(actorInfo == null || actorInfo.getStatus() == 1) { // 状态 0正常；1已删除
			this.writeJsonObject(response, AppRetCode.ACCOUNT_NOT_EXIST, AppRetCode.ACCOUNT_NOT_EXIST_TEXT, null);	
			return;
		}
		// 检索用户信息
		actorInfo = actorInfoService.selectById(blackActorId);
		if(actorInfo == null || actorInfo.getStatus() == 1) { // 状态 0正常；1已删除
			this.writeJsonObject(response, AppRetCode.ACCOUNT_NOT_EXIST, AppRetCode.ACCOUNT_NOT_EXIST_TEXT, null);	
			return;
		}
		BlackListInfo info = new BlackListInfo();
		info.setActorId(actorId);
		info.setBlackActorId(blackActorId);
		int count =blackListInfoService.selectCount(info);
		if(count>0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, null);	
		}else{
			
			//BlackListInfo info = new BlackListInfo();
			info.setId(UUIDUtil.getUUID());
			//info.setActorId(actorId);
			//info.setBlackActorId(blackActorId);
			info.setCreateTime(DateUtils.getDateTimeFormat(new Date()));
			int ret =blackListInfoService.insert(info);
			
			if(ret>0){
				this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, null);	
			}else {
				this.writeJsonObject(response, AppRetCode.ERROR, "服务器错误！", null);				
			}
		}
	}
	/**
	 * 
	 * @Title: removeBlackList
	 * @Description: 移除黑名单
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "removeBlackList")
	public void removeBlackList(HttpServletRequest request, HttpServletResponse response,String actorId,String blackActorId) {
		
		if(StringUtils.isBlank(actorId)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "actorId为空", null);	
			return;
		}
		if(StringUtils.isBlank(blackActorId)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "blackActorId为空", null);	
			return;
		}
		BlackListInfo info = new BlackListInfo();
		info.setActorId(actorId);
		info.setBlackActorId(blackActorId);
		int ret = blackListInfoService.delete(info);
		
		if(ret>0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, null);	
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "服务器错误！", null);				
		}
	}
	/**
	 * 
	 * @Title: report
	 * @Description: 添加举报
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "report")
	public void report(HttpServletRequest request, HttpServletResponse response,String actorId,String reportId,String type,String reason) {
		
		if(StringUtils.isBlank(actorId)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "用户id为空", null);	
			return;
		}
		
		if (StringUtils.isBlank(reportId)) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "举报内容id为空！", null);
			return;
		}
		
		
		// 检索用户信息
		ActorInfo actorInfo = actorInfoService.selectById(actorId);
		if(actorInfo == null || actorInfo.getStatus() == 1) { // 状态 0正常；1已删除
			this.writeJsonObject(response, AppRetCode.ACCOUNT_NOT_EXIST, AppRetCode.ACCOUNT_NOT_EXIST_TEXT, null);	
			return;
		}
		ContentReportInfo info = new ContentReportInfo();
		info.setReportId(reportId);
		info.setType(type);
		info.setCreater(actorId);
		int count =contentReportInfoService.selectCount(info);
		if(count>0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, null);	
		}else{
			
			info.setId(UUIDUtil.getUUID());
			info.setCreateTime(DateUtils.getDateTimeFormat(new Date()));
			info.setStatus("0");
			info.setReason(reason);
			int ret =contentReportInfoService.insert(info);
			
			if(ret>0){
				this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, null);	
			}else {
				this.writeJsonObject(response, AppRetCode.ERROR, "服务器错误！", null);				
			}
		}
	}
}
