package com.yyhz.sc.controller.app;

import java.util.ArrayList;
import java.util.Collections;
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
import com.yyhz.sc.data.model.ActorComment;
import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.data.model.AnnouceEnroll;
import com.yyhz.sc.data.model.AnnounceInfo;
import com.yyhz.sc.data.model.AnnouncePicture;
import com.yyhz.sc.data.model.PublicType;
import com.yyhz.sc.data.model.RoleInfo;
import com.yyhz.sc.data.model.SystemPictureInfo;
import com.yyhz.sc.services.ActorCommentService;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.AnnouceEnrollService;
import com.yyhz.sc.services.AnnounceInfoService;
import com.yyhz.sc.services.AnnouncePictureService;
import com.yyhz.sc.services.PublicTypeService;
import com.yyhz.sc.services.RoleInfoService;
import com.yyhz.utils.DateFormatUtil;
import com.yyhz.utils.DateUtils;
import com.yyhz.utils.EasemobUtil;
import com.yyhz.utils.UUIDUtil;
import com.yyhz.utils.stream.config.Configurations;

@Controller
@RequestMapping(value = "api")
public class AppAnnounceController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(AppAnnounceController.class);
	
	@Resource
	private AnnounceInfoService announceInfoService;
	@Resource
	private PublicTypeService publicTypeService;
	@Resource
	private RoleInfoService roleInfoService;
	@Resource
	private ActorInfoService actorInfoService;
	@Resource
	private AnnouceEnrollService annouceEnrollService;
	@Resource
	private AnnouncePictureService announcePictureService;
	@Resource
	private ActorCommentService actorCommentService;

	
	/**
	 * 
	 * @Title: getPublicType
	 * @Description: 获取通告卡片发布类型
	 * @param type 查询类别 // 1主持/模特；2设备/服装；3策划/创意；4婚礼/派对;其他返回全部结果
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getPublicType")
	public void getPublicType(HttpServletRequest request, HttpServletResponse response, Integer type) {
		if(type == null) // 1主持/模特；2设备/服装；3策划/创意；4婚礼/派对;其他查询全部结果
		{
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数type错误！", null);
			return;
		}

		
		PublicType condition = new PublicType();
		if(type > 0) // 1主持/模特；2设备/服装；3策划/创意；4婚礼/派对;其他查询全部结果
		{
			condition.setType(type);
		}
		condition.setStatus(0); // 0正常；1已删除
		List<PublicType> publicTypes = publicTypeService.selectAll(condition);
		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, publicTypes);
	}
	
	/**
	 * 
	 * @Title: getRoleInfo
	 * @Description: 获取角色类型类型
	 * @return JSON
	 * @author CrazyT
	 * 
	 */	
	@RequestMapping(value = "getRoleInfo")
	public void getRoleInfo(HttpServletRequest request, HttpServletResponse response) {
		
		RoleInfo condition = new RoleInfo();
		condition.setSort("createTime");
		condition.setOrder("asc");
		condition.setStatus(0);// 0正常；1已删除
		List<RoleInfo> roleInfos = roleInfoService.selectAll(condition);
		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, roleInfos);
	}

	/**
	 * 
	 * @Title: getAnnouncementList
	 * @Description: 获取通告列表
	 * @param type 通告类别// 1主持/模特；2设备/服装；3策划/创意；4婚礼/派对;其他返回全部结果
	 * @param page 分页查询的页码
	 * @param pageSize 每页记录数
	 * @return JSON
	 * @author CrazyT
	 * 
	 */	
	@RequestMapping(value = "getAnnouncementList")
	public void getAnnouncementList(HttpServletRequest request, HttpServletResponse response, AnnounceInfo req, String actorId, Integer page, Integer pageSize, Integer sortKey) {
		
		if (req == null) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少查询相关参数！", null);
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

		
		AnnounceInfo condition = new AnnounceInfo();
		condition.setStatus(0); // 状态 0正常；1已删除；
//		condition.setEnrollStatus(0);// 报名状态 0正常；1已关闭；
		
		if(req.getType() != null && req.getType() > 0) // 1主持/模特；2设备/服装；3策划/创意；4婚礼/派对;其他查询全部结果
		{
			condition.setType(req.getType());
		}
		if(StringUtils.isNotBlank(req.getCity())) {
			condition.setCity(req.getCity());
		}
		if(req.getSex() != null) {
			condition.setSex(req.getSex());
		}
		if(req.getAuthenticateLevel() != null) {
			condition.setAuthenticateLevel(req.getAuthenticateLevel());
		}
		if(StringUtils.isNotBlank(req.getPublicType())) {
			condition.setPublicType(req.getPublicType());
		}
		if(StringUtils.isNotBlank(req.getCreater())) {
			condition.setCreater(req.getCreater());
		}
		if(req.getEnrollStatus() != null) {
			condition.setEnrollStatus(req.getEnrollStatus());
		}

		condition.setSort("createTime");// 默认按照创建时间逆序
		
		if(sortKey != null) {
			if(sortKey == 1) {
				condition.setSort("createTime");
			}else {
				condition.setSort("avgScore");
			}
		}
		
		if(StringUtils.isNotBlank(req.getKeyword())) {
			condition.setKeyword(StringUtils.trim(req.getKeyword()));
		}

		// 查询我报名的通告
		Map<String, AnnounceInfo> myEnrollAnnounceMap = new HashMap<String, AnnounceInfo>();
		if(StringUtils.isNotBlank(actorId)) {
			AnnounceInfo myEnrollcond = new AnnounceInfo();
			myEnrollcond.setEnrollActorId(actorId); 
			
			List<AnnounceInfo> myEnrollAnnounceInfos = announceInfoService.selectAll(myEnrollcond, "selectMyEnroll");
			if(myEnrollAnnounceInfos != null) {
				for(AnnounceInfo info : myEnrollAnnounceInfos){
					myEnrollAnnounceMap.put(info.getId(), info);
				}
			}
		}
		
		PageInfo<AnnounceInfo> pageInfo = new PageInfo<AnnounceInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);

		announceInfoService.selectAll(condition, pageInfo, "selectAllSummary");
		
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
				annMap.put("publicType", annInfo.getPublicTypeNames());					
				annMap.put("city", annInfo.getCity());
				annMap.put("showTime", DateUtils.getDateFormat(annInfo.getShowTime()));
				annMap.put("createTime", DateUtils.getDateTimeMinFormat(annInfo.getCreateTime()));
				annMap.put("name", annInfo.getName());
				annMap.put("address", annInfo.getAddress());
				annMap.put("detail", annInfo.getDetail());
				annMap.put("enrollStatus", annInfo.getEnrollStatus());
				if(annInfo.getAvgScore() == null) {
					annMap.put("avgScore", new Integer(0));			
				}else {
					annMap.put("avgScore", annInfo.getAvgScore());
				}
				
				if(StringUtils.isNotBlank(annInfo.getHeadImgUrl())) {
					annMap.put("headImgUrl", Configurations.buildDownloadUrl(annInfo.getHeadImgUrl()));					
				}
				annMap.put("createName", annInfo.getCreaterName());
				annMap.put("authenticateLevel", annInfo.getAuthenticateLevel());
				//查看是否是我报名的通告，如果是，添加审核信息
				if(myEnrollAnnounceMap.size() > 0) {
					AnnounceInfo myEnrollAnnounce = myEnrollAnnounceMap.get(annInfo.getId());
					if(myEnrollAnnounce != null) {
						annMap.put("enrollActorCheckState", myEnrollAnnounce.getEnrollActorCheckState());
					}
				}
				annMap.put("isMinePublish", actorId.equals(annInfo.getCreater())?1:0);	
				dataList.add(annMap);
			}
		}
		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, result);
	}
	
	/**
	 * 
	 * @Title: addHostAnnouncement
	 * @Description: 添加(主持/模特)通告
	 * @return JSON
	 * @author CrazyT
	 * 
	 */	
	@RequestMapping(value = "addHostAnnouncement")
	public void addHostAnnouncement(HttpServletRequest request, HttpServletResponse response, 
			AnnounceInfo reqInfo,String publicTypeIds, @RequestParam(required = false) MultipartFile[] imageFiles) {
		if(reqInfo == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少通告相关参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getTitle())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少title参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getName())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少name参数", null);
			return;
		}

		if(StringUtils.isBlank(reqInfo.getShowTimeStr())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少showTimeStr参数", null);
			return;
		}

		if(StringUtils.isBlank(reqInfo.getCity())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少city参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少creater参数", null);
			return;
		}
		
		if(StringUtils.isNotBlank(reqInfo.getShowTimeStr())) {
			reqInfo.setShowTime(DateUtils.formatDate(DateFormatUtil.FormatDate(reqInfo.getShowTimeStr()), DateUtils.DATETIME_DEFAULT_FORMAT));
		}

		if(StringUtils.isNotBlank(reqInfo.getEntranceTimeStr())) {
			reqInfo.setEntranceTime(DateUtils.formatDate(DateFormatUtil.FormatDate(reqInfo.getEntranceTimeStr()), DateUtils.DATETIME_DEFAULT_FORMAT));
		}
		
		reqInfo.setType(1); // 1主持/模特；2设备/服装；3策划/创意；4婚礼/派对
		
		if(actorInfoService.selectById(reqInfo.getCreater()) == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "创建者不存在，请检查creater参数", null);
			return;		
		}
		
		List<String> picUuidList = new ArrayList<String>();
		if(imageFiles != null && imageFiles.length > 0){
			for(MultipartFile multipartFile : imageFiles){
				if(multipartFile == null){
					continue;
				}
				SystemPictureInfo pictureInfo = this.uploadFile2("hostAnnouncement", multipartFile);
				picUuidList.add(pictureInfo.getUuid());
			}
		}

		int ret = announceInfoService.addAnnouncement(reqInfo, StringUtils.split(publicTypeIds,','), picUuidList.toArray(new String[0]));

		if(ret > 0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, reqInfo);
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "添加通告失败！", null);
		}
	}
	
	/**
	 * 
	 * @Title: addDeviceAnnouncement
	 * @Description: 添加(设备)通告
	 * @return JSON
	 * @author CrazyT
	 * 
	 */	
	@RequestMapping(value = "addDeviceAnnouncement")
	public void addDeviceAnnouncement(HttpServletRequest request, HttpServletResponse response, 
			AnnounceInfo reqInfo,String publicTypeIds, @RequestParam(required = false) MultipartFile[] imageFiles) {
		if(reqInfo == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少通告相关参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getTitle())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少title参数", null);
			return;
		}
		
//		if(StringUtils.isBlank(reqInfo.getName())) {
//			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少name参数", null);
//			return;
//		}

//		if(StringUtils.isBlank(reqInfo.getAddress())) {
//			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少address参数", null);
//			return;
//		}

		if(StringUtils.isBlank(reqInfo.getCity())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少city参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少creater参数", null);
			return;
		}
		
		if(StringUtils.isNotBlank(reqInfo.getShowTimeStr())) {
			reqInfo.setShowTime(DateUtils.formatDate(DateFormatUtil.FormatDate(reqInfo.getShowTimeStr()), DateUtils.DATETIME_DEFAULT_FORMAT));
		}
		
		if(StringUtils.isNotBlank(reqInfo.getEntranceTimeStr())) {
			reqInfo.setEntranceTime(DateUtils.formatDate(DateFormatUtil.FormatDate(reqInfo.getEntranceTimeStr()), DateUtils.DATETIME_DEFAULT_FORMAT));
		}

		reqInfo.setType(2); // 1主持/模特；2设备/服装；3策划/创意；4婚礼/派对
		
		if(actorInfoService.selectById(reqInfo.getCreater()) == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "创建者不存在，请检查creater参数", null);
			return;		
		}

		List<String> picUuidList = new ArrayList<String>();
		if(imageFiles != null && imageFiles.length > 0){
			for(MultipartFile multipartFile : imageFiles){
				if(multipartFile == null){
					continue;
				}
				SystemPictureInfo pictureInfo = this.uploadFile2("deviceAnnouncement", multipartFile);
				picUuidList.add(pictureInfo.getUuid());
			}
		}

		int ret = announceInfoService.addAnnouncement(reqInfo, StringUtils.split(publicTypeIds,','), picUuidList.toArray(new String[0]));

		if(ret > 0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, reqInfo);
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "添加通告失败！", null);
		}
		
	}
	
	/**
	 * 
	 * @Title: addPlanAnnouncement
	 * @Description: 添加(策划)通告
	 * @return JSON
	 * @author CrazyT
	 * 
	 */	
	@RequestMapping(value = "addPlanAnnouncement")
	public void addPlanAnnouncement(HttpServletRequest request, HttpServletResponse response, 
			AnnounceInfo reqInfo,String publicTypeIds, @RequestParam(required = false) MultipartFile[] imageFiles) {
		if(reqInfo == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少通告相关参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getTitle())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少title参数", null);
			return;
		}
		
//		if(StringUtils.isBlank(reqInfo.getName())) {
//			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少name参数", null);
//			return;
//		}

		if(StringUtils.isBlank(reqInfo.getCity())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少city参数", null);
			return;
		}
		
		if(StringUtils.isBlank(publicTypeIds)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少publicTypeIds参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少creater参数", null);
			return;
		}
		
		if(StringUtils.isNotBlank(reqInfo.getShowTimeStr())) {
			reqInfo.setShowTime(DateUtils.formatDate(DateFormatUtil.FormatDate(reqInfo.getShowTimeStr()), DateUtils.DATETIME_DEFAULT_FORMAT));
		}
		
		if(StringUtils.isNotBlank(reqInfo.getEntranceTimeStr())) {
			reqInfo.setEntranceTime(DateUtils.formatDate(DateFormatUtil.FormatDate(reqInfo.getEntranceTimeStr()), DateUtils.DATETIME_DEFAULT_FORMAT));
		}

		reqInfo.setType(3); // 1主持/模特；2设备/服装；3策划/创意；4婚礼/派对
		
		if(actorInfoService.selectById(reqInfo.getCreater()) == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "创建者不存在，请检查creater参数", null);
			return;		
		}

		List<String> picUuidList = new ArrayList<String>();
		if(imageFiles != null && imageFiles.length > 0){
			for(MultipartFile multipartFile : imageFiles){
				if(multipartFile == null){
					continue;
				}
				SystemPictureInfo pictureInfo = this.uploadFile2("planAnnouncement", multipartFile);
				picUuidList.add(pictureInfo.getUuid());
			}
		}

		int ret = announceInfoService.addAnnouncement(reqInfo, StringUtils.split(publicTypeIds,','), picUuidList.toArray(new String[0]));

		if(ret > 0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, reqInfo);
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "添加通告失败！", null);
		}
		
	}

	/**
	 * 
	 * @Title: addWeddingAnnouncement
	 * @Description: 添加(婚礼)通告
	 * @return JSON
	 * @author CrazyT
	 * 
	 */	
	@RequestMapping(value = "addWeddingAnnouncement")
	public void addWeddingAnnouncement(HttpServletRequest request, HttpServletResponse response, 
			AnnounceInfo reqInfo,String publicTypeIds, @RequestParam(required = false) MultipartFile[] imageFiles) {
		if(reqInfo == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少通告相关参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getTitle())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少title参数", null);
			return;
		}
		
//		if(StringUtils.isBlank(reqInfo.getName())) {
//			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少name参数", null);
//			return;
//		}

		if(StringUtils.isBlank(reqInfo.getCity())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少city参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getShowTimeStr())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少showTimeStr参数", null);
			return;
		}

		if(StringUtils.isBlank(publicTypeIds)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少publicTypeIds参数", null);
			return;
		}

		if(StringUtils.isBlank(reqInfo.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少creater参数", null);
			return;
		}
		
		if(StringUtils.isNotBlank(reqInfo.getShowTimeStr())) {
			reqInfo.setShowTime(DateUtils.formatDate(DateFormatUtil.FormatDate(reqInfo.getShowTimeStr()), DateUtils.DATETIME_DEFAULT_FORMAT));
		}
		
		if(StringUtils.isNotBlank(reqInfo.getEntranceTimeStr())) {
			reqInfo.setEntranceTime(DateUtils.formatDate(DateFormatUtil.FormatDate(reqInfo.getEntranceTimeStr()), DateUtils.DATETIME_DEFAULT_FORMAT));
		}

		reqInfo.setType(4); // 1主持/模特；2设备/服装；3策划/创意；4婚礼/派对
		
		if(actorInfoService.selectById(reqInfo.getCreater()) == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "创建者不存在，请检查creater参数", null);
			return;		
		}

		List<String> picUuidList = new ArrayList<String>();
		if(imageFiles != null && imageFiles.length > 0){
			for(MultipartFile multipartFile : imageFiles){
				if(multipartFile == null){
					continue;
				}
				SystemPictureInfo pictureInfo = this.uploadFile2("weddingAnnouncement", multipartFile);
				picUuidList.add(pictureInfo.getUuid());
			}
		}

		int ret = announceInfoService.addAnnouncement(reqInfo, StringUtils.split(publicTypeIds,','), picUuidList.toArray(new String[0]));

		if(ret > 0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, reqInfo);
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "添加通告失败！", null);
		}	
	}
	
	/**
	 * 
	 * @Title: getHostAnnouncementDetail
	 * @Description: 获取通告详情-主持
	 * @return JSON
	 * @author CrazyT
	 * 
	 */	
	@RequestMapping(value = {"getHostAnnouncementDetail","getDeviceAnnouncementDetail","getPlanAnnouncementDetail","getWeddingAnnouncementDetail"})
	public void getAnnouncementDetail(HttpServletRequest request, HttpServletResponse response, String id, String actorId) {
		if(StringUtils.isBlank(id)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少通告id参数", null);
			return;
		}

		if(StringUtils.isBlank(actorId)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少当前用户actorId参数", null);
			return;
		}
		
		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("id", id);
		condition.put("status", 0);
		condition.put("actorId", actorId);

		// 查询通告信息
		AnnounceInfo announceInfo = announceInfoService.getAnnouncementDetails(condition);
		if(announceInfo == null)
		{
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "通告详情不存在，请检查参数id", null);
			return;
		}
		
		Map<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("id", announceInfo.getId());
		ret.put("type", announceInfo.getType());
		ret.put("title", StringUtils.trimToEmpty(announceInfo.getTitle()));
		ret.put("name", announceInfo.getName()); // 艺人类型；需要装备；策划项目；定制内容；
		ret.put("sex", announceInfo.getSex());
		ret.put("price", announceInfo.getPrice());
		ret.put("publicType", announceInfo.getPublicTypeNames());					
		ret.put("city", announceInfo.getCity());
		ret.put("showTime", DateUtils.getDateFormat(announceInfo.getShowTime()));
		ret.put("creater", announceInfo.getCreater());
		ret.put("createTime", DateUtils.getDateTimeMinFormat(announceInfo.getCreateTime()));
		ret.put("entranceTime", announceInfo.getEntranceTime());
		ret.put("address", announceInfo.getAddress());
		ret.put("detail", announceInfo.getDetail());
		ret.put("enrollStatus", announceInfo.getEnrollStatus());
		if(StringUtils.isNotBlank(announceInfo.getHeadImgUrl())) {
			ret.put("headImgUrl", Configurations.buildDownloadUrl(announceInfo.getHeadImgUrl()));
		}
		ret.put("createName", announceInfo.getCreaterName());
		ret.put("authenticateLevel", announceInfo.getAuthenticateLevel());
		ret.put("note", announceInfo.getNote());
		if(actorId.equals(announceInfo.getCreater())) {
			ret.put("isMinePublish", 1);
		}else {
			ret.put("isMinePublish", 0);
		}
		
		// 查询创建者分数
		ActorComment commentReq = new ActorComment();
		commentReq.setActorId(announceInfo.getCreater());
		commentReq.setType(0); // 查询发通告者
		
		float avgScore = actorCommentService.getAvgScore(commentReq);
		ret.put("score", avgScore);
		
		ActorInfo actorInfo = actorInfoService.selectById(announceInfo.getCreater());
		if(actorInfo != null) {
			ret.put("roleName", StringUtils.split(actorInfo.getRoleName(), ','));
		}else {
			ret.put("roleName", StringUtils.split("", ','));
		}
		
		// 查询报名信息
		AnnouceEnroll enrollCondition = new AnnouceEnroll();
		enrollCondition.setAnnounceId(announceInfo.getId());
		
		
		List<Map<String, Object>> applyList = new ArrayList<Map<String, Object>>();
		
		int myEnrollStatus = 1; // 未报名
		int checkStatus = 0; // 审核装填， 0待审核；1通过；2拒绝；
		
		List<AnnouceEnroll> enrollList = annouceEnrollService.selectAll(enrollCondition, "selectAllDetail");

		if(enrollList != null)
		{
			for(AnnouceEnroll annouceEnroll : enrollList)
			{
				Map<String, Object> actorMap = new HashMap<String, Object>();
				actorMap.put("id", annouceEnroll.getId());
				actorMap.put("announceId", annouceEnroll.getAnnounceId());
				actorMap.put("actorId", annouceEnroll.getActorId());
				actorMap.put("createTime", DateUtils.getDateTimeFormat(annouceEnroll.getCreateTime()));
				actorMap.put("checkTime", DateUtils.getDateTimeFormat(annouceEnroll.getCheckTime()));
				actorMap.put("checkStatus", annouceEnroll.getCheckStatus());
				actorMap.put("enrollStatus", annouceEnroll.getEnrollStatus());
				actorMap.put("name", annouceEnroll.getActorName());
				if(StringUtils.isNotBlank(annouceEnroll.getActorImgUrl())) {
					actorMap.put("headImgUrl", Configurations.buildDownloadUrl(annouceEnroll.getActorImgUrl()));
				}
				
				// 如果当前用户actor，报名了此通告，则单独返回相关信息
				if(actorId.equals(annouceEnroll.getActorId())) {
					ret.put("myEnroll", new HashMap<String, Object>(actorMap));
					myEnrollStatus = annouceEnroll.getEnrollStatus();
					checkStatus = annouceEnroll.getCheckStatus();
				}
				
				applyList.add(actorMap);
			}
			
			ret.put("enrollActors", applyList);
		}
		
		ret.put("myEnrollStatus", myEnrollStatus);
		ret.put("myCheckStatus", checkStatus);
		
		// 查询评论信息
		ActorComment commentCondition = new ActorComment();
		commentCondition.setActorId(announceInfo.getCreater());
		commentCondition.setCreater(actorId);
		commentCondition.setAnnounceId(id);

		ActorComment actorComment = actorCommentService.selectEntity(commentCondition);
		if(actorComment != null) {
			ret.put("myComment", 1);
		}else {
			ret.put("myComment", 0);
		}

		// 查询通告关联图片
		AnnouncePicture announcePicture = new AnnouncePicture();
		announcePicture.setAnnounceId(announceInfo.getId());
		
		List<AnnouncePicture> announcePictureList = announcePictureService.selectAll(announcePicture, "selectAllWithUrl");
		List<String> imgUrls = new ArrayList<String>();
		if(announcePictureList != null) {
			for(AnnouncePicture item : announcePictureList) {
				imgUrls.add(Configurations.buildDownloadUrl(item.getImgUrl()));
			}
		}
		
		Collections.sort(imgUrls);
		ret.put("imgUrls", imgUrls);
		
		// 更新浏览量数据
		AnnounceInfo updateCondition = new AnnounceInfo();
		updateCondition.setId(announceInfo.getId());
		updateCondition.setReadCount(announceInfo.getReadCount() + 1);
		
		announceInfoService.update(updateCondition);

		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);
	}
	
	/**
	 * 
	 * @Title: applyAnnouncement
	 * @Description: 报名通告
	 * @return JSON
	 * @author CrazyT
	 * 
	 */	
	@RequestMapping(value = "applyAnnouncement")
	public void applyAnnouncement(HttpServletRequest request, HttpServletResponse response, AnnouceEnroll req) {
		if(StringUtils.isBlank(req.getAnnounceId())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少announceId参数", null);
			return;
		}

		if(StringUtils.isBlank(req.getActorId())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少actorId参数", null);
			return;
		}
		
		if(req.getEnrollStatus() == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少enrollStatus参数", null);
			return;		
		}
		
		AnnounceInfo announceInfo = announceInfoService.selectById(req.getAnnounceId());
		if(announceInfo == null || announceInfo.getStatus() == 1) // 不存在或已经删除
		{
			this.writeJsonObject(response, AppRetCode.ERROR, "要报名的通告不存在！", null);
			return;
		}
		
		if(req.getActorId().equals(announceInfo.getCreater())) {
			this.writeJsonObject(response, AppRetCode.ERROR, "不能报名自己的通告！", null);
			return;			
		}
		
		if(announceInfo.getEnrollStatus() == 1) // 报名已关闭
		{
			this.writeJsonObject(response, AppRetCode.ERROR, "报名已经结束！", null);
			return;
		}
		
		// 查询报名人信息
		ActorInfo eactorInfo = actorInfoService.selectById(req.getActorId());
		if(eactorInfo == null || eactorInfo.getStatus() == 1) { // 不存在或已删除
			this.writeJsonObject(response, AppRetCode.ERROR, "报名人不存在", null);
			return;
		}
		
		AnnouceEnroll enrollCondition = new AnnouceEnroll();
		enrollCondition.setAnnounceId(req.getAnnounceId());
		enrollCondition.setActorId(req.getActorId());
		
		AnnouceEnroll announceEnroll = annouceEnrollService.selectEntity(enrollCondition);
		if(announceEnroll != null && announceEnroll.getCheckStatus() == 0) {
					
			AnnouceEnroll condition = new AnnouceEnroll();
			condition.setId(announceEnroll.getId());
			condition.setEnrollStatus(req.getEnrollStatus() > 0 ? 1 : 0); //报名状态：0已报名；1已取消
			
			int ret = annouceEnrollService.update(condition);
			if(ret > 0)
			{
				this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, condition);
				
				// 发送通知消息
				String message = String.format("“%s”报名了您发布的“%s”通告。[点击查看] ", StringUtils.trimToEmpty(eactorInfo.getName())
						, StringUtils.trimToEmpty(announceInfo.getTitle()));
				Map<String, Object> ext = new HashMap<String, Object>();
				ext.put("fromType", "annouceNotify");
				ext.put("atid", announceInfo.getId());
				ext.put("type", announceInfo.getType());
				ext.put("publicType", StringUtils.trimToEmpty(announceInfo.getPublicTypeNames()));
				Object resp = EasemobUtil.sendAnnounceMessage(announceInfo.getCreater(), message, ext);
				logger.debug("---------------------- send message resp -----------------------------------");
				logger.debug(resp.toString());
				return;				
			}else {
				this.writeJsonObject(response, AppRetCode.ERROR, "更新数据库失败！", null);
				return;							
			}
		}else if(announceEnroll == null) {
			AnnouceEnroll condition = new AnnouceEnroll();
			
			condition.setId(UUIDUtil.getUUID());
			condition.setActorId(req.getActorId());
			condition.setAnnounceId(req.getAnnounceId());
			condition.setCreateTime(new Date());

			int ret = annouceEnrollService.insert(condition);
			if(ret > 0){
				this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, condition);

				// 发送通知消息
				String message = String.format("“%s”报名了您发布的“%s”通告。[点击查看]", StringUtils.trimToEmpty(eactorInfo.getName())
						, StringUtils.trimToEmpty(announceInfo.getTitle()));
				
				Map<String, Object> ext = new HashMap<String, Object>();
				ext.put("fromType", "annouceNotify");
				ext.put("atid", announceInfo.getId());
				ext.put("type", announceInfo.getType());
				ext.put("publicType", StringUtils.trimToEmpty(announceInfo.getPublicTypeNames()));
				Object resp = EasemobUtil.sendAnnounceMessage(announceInfo.getCreater(), message, ext);
				logger.debug("---------------------- send message resp -----------------------------------");
				logger.debug(resp.toString());

				return;				
			}else {
				this.writeJsonObject(response, AppRetCode.ERROR, "插入数据库失败！", null);
				return;							
			}
		}else{
			this.writeJsonObject(response, AppRetCode.ERROR, "审核完成，不能修改报名状态。", null);
			return;										
		}
	}
	
	/**
	 * 
	 * @Title: deleteAnnouncement
	 * @Description: 删除通告
	 * @return JSON
	 * @author CrazyT
	 * 
	 */	
	@RequestMapping(value = "deleteAnnouncement")
	public void deleteAnnouncement(HttpServletRequest request, HttpServletResponse response, String id) {
		
		if(StringUtils.isBlank(id)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少id参数", null);
			return;
		}

		AnnounceInfo condition = new AnnounceInfo();
		condition.setId(id);
		condition.setStatus(1); // 删除
		
		announceInfoService.update(condition);
		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, null);
	}
	
}
