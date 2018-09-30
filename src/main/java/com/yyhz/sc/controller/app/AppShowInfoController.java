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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yyhz.constant.AppRetCode;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.data.model.ActorCollect;
import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.data.model.ActorRole;
import com.yyhz.sc.data.model.AudioInfo;
import com.yyhz.sc.data.model.RoleInfo;
import com.yyhz.sc.data.model.ShowComment;
import com.yyhz.sc.data.model.ShowCommentPraise;
import com.yyhz.sc.data.model.ShowInfo;
import com.yyhz.sc.data.model.ShowInfoPictures;
import com.yyhz.sc.data.model.ShowPraise;
import com.yyhz.sc.data.model.SystemPictureInfo;
import com.yyhz.sc.services.ActorCollectService;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.ActorRoleService;
import com.yyhz.sc.services.AudioInfoService;
import com.yyhz.sc.services.RoleInfoService;
import com.yyhz.sc.services.ShowCommentPraiseService;
import com.yyhz.sc.services.ShowCommentService;
import com.yyhz.sc.services.ShowInfoPicturesService;
import com.yyhz.sc.services.ShowInfoService;
import com.yyhz.sc.services.ShowPraiseService;
import com.yyhz.utils.DateUtils;
import com.yyhz.utils.RelativeDateFormat;
import com.yyhz.utils.RongCloudMethodUtil;
import com.yyhz.utils.UUIDUtil;
import com.yyhz.utils.stream.config.Configurations;

import net.sf.json.JSONObject;

@Controller
public class AppShowInfoController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(AppShowInfoController.class);

	@Resource
	private ShowInfoService showInfoService;
	
	@Resource
	private ShowInfoPicturesService showInfoPicturesService;
	
	@Resource
	private ActorCollectService actorCollectService;
	
	@Resource
	private ActorInfoService actorInfoService;
	
	@Resource
	private ActorRoleService actorRoleService;
	
	@Resource
	private ShowCommentService showCommentService;
	
	@Resource
	private ShowPraiseService showPraiseService;
	
	@Resource
	private ShowCommentPraiseService showCommentPraiseService;
	
	@Resource
	private RoleInfoService roleInfoService;
	
	@Resource
	private AudioInfoService audioInfoService;
	
	/**
	 * 发布秀一秀
	 * @param request
	 * @param response
	 * @param req
	 * @param showInfo
	 * @param imageFiles
	 * @param videoFile
	 * @return
	 */
	@RequestMapping(value = "api/addShow")
	@ResponseBody
	public Map<String,Object> addShow(HttpServletRequest request, HttpServletResponse response, SystemPictureInfo req, 
			ShowInfo showInfo,@RequestParam(required = false) MultipartFile[] imageFiles,@RequestParam(required = false) MultipartFile videoFile,
			@RequestParam(required = false) MultipartFile videoPreviewFile) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> picUuidList = new ArrayList<String>();
		if(imageFiles != null && imageFiles.length > 0){
			for(MultipartFile multipartFile : imageFiles){
				if(multipartFile == null){
					continue;
				}
				SystemPictureInfo pictureInfo = this.uploadFile2("yyhz", multipartFile, req);
				picUuidList.add(pictureInfo.getUuid());
			}
		}
		
		if(videoFile != null){
			SystemPictureInfo videoInfo = this.uploadVideo2QCloud(videoFile);
			showInfo.setVideoUuid(videoInfo.getUuid());
			
			SystemPictureInfo videoPreviewInfo = this.uploadFile2("yyhz", videoPreviewFile, req);
			showInfo.setVideoPreviewUuid(videoPreviewInfo.getUuid());
		}
		showInfo.setId(UUIDUtil.getUUID());
		showInfo.setCreateTime(new Date());
		showInfo.setStatus("0");
		int retCount = showInfoService.insert(showInfo);
		if(retCount > 0 && !picUuidList.isEmpty()){
			//添加图片与业务表关联
			for(String imgUuid : picUuidList){
				ShowInfoPictures showInfoPicture = new ShowInfoPictures();
				showInfoPicture.setId(UUIDUtil.getUUID());
				showInfoPicture.setShowId(showInfo.getId());
				showInfoPicture.setImgUuid(imgUuid);
				retCount = showInfoPicturesService.insert(showInfoPicture);
			}
		}
		if(retCount > 0){
			result.put("data", new HashMap<String,Object>());
			result.put("msg", "发布成功!");
			result.put("result", 1);
		}else{
			result.put("data", new HashMap<String,Object>());
			result.put("msg", "发布失败!");
			result.put("result", 0);
		}
		return result;
	}
	
	/**
	 * 秀一秀列表
	 * @param request
	 * @param response
	 * @param actorId
	 * @param roleType
	 * @param filterType
	 * @param mediaType
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "api/getShowList")
	@ResponseBody
	public Map<String,Object> getShowList(HttpServletRequest request, HttpServletResponse response,String actorId,
			String roleType,String filterType,String mediaType,String publicType,Integer page,Integer pageSize) {
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		PageInfo<ShowInfo> pageInfo = new PageInfo<ShowInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);
		ShowInfo info = new ShowInfo();
		info.setStatus("0");
		info.setSort("createTime");
		info.setOrder("desc");
		info.setPublicType(publicType);
		info.setType(mediaType);
		
		//过滤我关注的
		if("1".equals(filterType)){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("creater", actorId);
			List<ActorCollect> actorCollectList = actorCollectService.selectAll(params);
			if(actorCollectList == null || actorCollectList.isEmpty()){
				return buildEmptyData(pageInfo.getTotal(), pageInfo.getPage(), pageInfo.getPageSize());
			}
			List<String> actorIdList = new ArrayList<String>();
			for(ActorCollect actorCollect : actorCollectList){
				actorIdList.add(actorCollect.getActorId());
			}
			info.setActorIdList(actorIdList);
		}
		
		//过滤角色
		if(StringUtils.isNotBlank(roleType)){
			
			Map<String,Object> roleParams = new HashMap<String,Object>();
			roleParams.put("name", roleType);
			
			List<RoleInfo> roleList = roleInfoService.selectAll(roleParams);
			if(roleList == null || roleList.isEmpty()){
				return buildEmptyData(pageInfo.getTotal(), pageInfo.getPage(), pageInfo.getPageSize());
			}
			List<String> roleIdList = new ArrayList<String>();
			for(RoleInfo roleInfo : roleList){
				roleIdList.add(roleInfo.getId());
			}
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("roleIdList", roleIdList);
			List<ActorRole> acList = actorRoleService.selectAll(params);
			if(acList == null || acList.isEmpty()){
				return buildEmptyData(pageInfo.getTotal(), pageInfo.getPage(), pageInfo.getPageSize());
			}
			List<String> actorIdList = new ArrayList<String>();
			for(ActorRole actorRole : acList){
				actorIdList.add(actorRole.getActorId());
			}
			List<String> originalActorIdList = info.getActorIdList();
			if(originalActorIdList != null && originalActorIdList.isEmpty()){
				originalActorIdList.retainAll(actorIdList);
				info.setActorIdList(originalActorIdList);
			}else{
				info.setActorIdList(actorIdList);
			}
		}
		
		showInfoService.selectAll(info, pageInfo);
		List<ShowInfo> list = pageInfo.getRows();
		if(list == null || list.isEmpty()){
			return buildEmptyData(pageInfo.getTotal(), pageInfo.getPage(), pageInfo.getPageSize());
		}
		
		List<String> createrList = new ArrayList<String>();
		List<String> showIdList = new ArrayList<String>();
		for(ShowInfo showInfo : list){
			createrList.add(showInfo.getCreater());
			showIdList.add(showInfo.getId());
		}
		
		//关联用户
		List<ActorInfo> actorList = actorInfoService.selectByIds(createrList);
		if(actorList == null || actorList.isEmpty()){
			return buildEmptyData(pageInfo.getTotal(), pageInfo.getPage(), pageInfo.getPageSize());
		}
		Map<String,ActorInfo> actorMap = new HashMap<String,ActorInfo>();
		for(ActorInfo actorInfo : actorList){
			actorMap.put(actorInfo.getId(), actorInfo);
		}
		
		//关联评论
		Map<String,Object> commentParams = new HashMap<String,Object>();
		commentParams.put("showIdList", showIdList);
		commentParams.put("status", 0);
		List<ShowComment> showsCommentNum = showCommentService.selectShowCommentNum(commentParams);
		Map<String,Long> showCommentNumMap = new HashMap<String,Long>();
		if(showsCommentNum != null && !showsCommentNum.isEmpty()){
			for(ShowComment showCommentNum : showsCommentNum){
				showCommentNumMap.put(showCommentNum.getShowId(), showCommentNum.getCommentNum());
			}
		}
		
		//关联点赞
		Map<String,Object> praiseParams = new HashMap<String,Object>();
		praiseParams.put("showIdList", showIdList);
		List<ShowPraise> praiseCommentNum = showPraiseService.selectShowPraiseNum(commentParams);
		Map<String,Long> praiseCommentNumMap = new HashMap<String,Long>();
		if(praiseCommentNum != null && !praiseCommentNum.isEmpty()){
			for(ShowPraise showPraise : praiseCommentNum){
				praiseCommentNumMap.put(showPraise.getShowId(), showPraise.getPraiseNum());
			}
		}
		
		//关联图片
		Map<String,Object> picParams = new HashMap<String,Object>();
		picParams.put("showIdList", showIdList);
		List<ShowInfoPictures> showPicList = showInfoPicturesService.selectAll(picParams);
		Map<String,List<SystemPictureInfo>> picMap = new HashMap<String,List<SystemPictureInfo>>();
		if(showPicList != null && !showPicList.isEmpty()){
			for(ShowInfoPictures showInfoPictures : showPicList){
				String showId = showInfoPictures.getShowId();
				SystemPictureInfo systemPictureInfo = showInfoPictures.getSystemPictureInfo();
				if(systemPictureInfo == null){
					continue;
				}
				if(picMap.get(showId) == null){
					List<SystemPictureInfo> picList = new ArrayList<SystemPictureInfo>();
					picList.add(systemPictureInfo);
					picMap.put(showId, picList);
				}else{
					List<SystemPictureInfo> picList = picMap.get(showId);
					picList.add(systemPictureInfo);
				}
			}
		}
		
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		for(ShowInfo showInfo : list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			ActorInfo actorInfo = actorMap.get(showInfo.getCreater());
			dataMap.put("id", showInfo.getId());
			dataMap.put("publicType", showInfo.getPublicType());
			dataMap.put("mediaType", showInfo.getType());
			SystemPictureInfo pictureInfo = actorInfo.getSystemPictureInfo();
			if(pictureInfo != null){
				String urlPath = pictureInfo.getUrlPath();
				String headImageUrl = StringUtils.isBlank(urlPath) ? "" : Configurations.buildDownloadUrl(urlPath);
				dataMap.put("headImageUrl", headImageUrl);
			}else{
				dataMap.put("headImageUrl", "");
			}
			dataMap.put("name", actorInfo.getName() == null ? "" : actorInfo.getName());
			dataMap.put("authenticateLevel", String.valueOf(actorInfo.getAuthenticateLevel()));
			dataMap.put("showDetail", showInfo.getContent());
			dataMap.put("location", showInfo.getCity());
			dataMap.put("creater", showInfo.getCreater());
			//时间转换
			dataMap.put("publishTime", RelativeDateFormat.format(showInfo.getCreateTime()));
			dataMap.put("shareNumber", showInfo.getShareNum());
			
			//评论数
			Long commentNumber = showCommentNumMap.get(showInfo.getId());
			dataMap.put("commentNumber",commentNumber == null ? 0L : commentNumber);
			
			//点赞数
			Long praiseNumber = praiseCommentNumMap.get(showInfo.getId());
			dataMap.put("praiseNumber", praiseNumber == null ? 0L : praiseNumber);
			
			//图片信息
			List<SystemPictureInfo> picList = picMap.get(showInfo.getId());
			if(picList == null || picList.isEmpty()){
				dataMap.put("imageList", new ArrayList<Map<String,Object>>());
			}else{
				List<Map<String,Object>> imageList = new ArrayList<Map<String,Object>>();
				List<String>imgUrls = new ArrayList<String>();
				for(SystemPictureInfo systemPictureInfo : picList){
					imgUrls.add(systemPictureInfo.getUrlPath());
				}
				
				Collections.sort(imgUrls);
				for(String url : imgUrls) {
					Map<String,Object> imageMap = new HashMap<String,Object>();
					imageMap.put("imageUrl", Configurations.buildDownloadUrl(url));
					imageList.add(imageMap);
				}
				
				dataMap.put("imageList", imageList);
			}
			
			SystemPictureInfo systemVideoinfo = showInfo.getSystemVideoInfo();
			if(systemVideoinfo != null){
				String videoPathUrl = systemVideoinfo.getUrlPath();
				String videoUrl = StringUtils.isBlank(videoPathUrl) ? "" : Configurations.buildDownloadUrl(videoPathUrl);
				dataMap.put("videoUrl", videoUrl);
			}else{
				dataMap.put("videoUrl", "");
			}
			
			SystemPictureInfo systemVideoPreviewInfo = showInfo.getSystemVideoPreviewInfo();
			if(systemVideoPreviewInfo != null){
				String videoPreviewPathUrl = systemVideoPreviewInfo.getUrlPath();
				String videoPreviewUrl = StringUtils.isBlank(videoPreviewPathUrl) ? "" : Configurations.buildDownloadUrl(videoPreviewPathUrl);
				dataMap.put("videoPreviewUrl", videoPreviewUrl);
			}else{
				dataMap.put("videoPreviewUrl", "");
			}
			
			dataList.add(dataMap);
		}
		
		Map<String,Object> rowsMap = new HashMap<String,Object>();
		rowsMap.put("rows", dataList);
		rowsMap.put("total", pageInfo.getTotal());
		rowsMap.put("page", page);
		rowsMap.put("pageSize", pageSize);
		
		resultMap.put("msg", "操作成功!");
		resultMap.put("result", 1);
		resultMap.put("data", rowsMap);
		return resultMap;
	}
	

	/**
	 * 秀一秀详情分享h5
	 * @param request
	 * @param response
	 * @param showId
	 * @return
	 */
	@RequestMapping(value = "webview/getShowShare")
	public String getShowShare(HttpServletRequest request, HttpServletResponse response,String showId,String actorId, ModelMap model) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Object showDetail = this.getShowDetail(request, response, showId, actorId).get("data");
		Object showComment = this.getShowCommentList(request, response, showId, actorId,1, 10).get("data");
		Object showPraise = this.getShowPraiseList(request, response, showId, 1, 10).get("data");
		
		ret.put("showDetail", showDetail);
		ret.put("showComment", showComment);
		ret.put("showPraise", showPraise);
		
		model.addAttribute("showInfo", ret);
		
		return "webview/show_share"; 
	}

	/**
	 * 秀一秀详情
	 * @param request
	 * @param response
	 * @param showId
	 * @return
	 */
	@RequestMapping(value = "api/getShowDetail")
	@ResponseBody
	public Map<String,Object> getShowDetail(HttpServletRequest request, HttpServletResponse response,String showId,String actorId) {
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		ShowInfo showInfo = showInfoService.selectById(showId);
		if(showInfo == null){
			return buildEmptyDataForDetail();
		}
		
		//关联用户
		ActorInfo actorInfo = actorInfoService.selectById(showInfo.getCreater());
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("id", showInfo.getId());
		dataMap.put("mediaType", showInfo.getType());
		dataMap.put("shareNumber", showInfo.getShareNum());
		dataMap.put("userId", showInfo.getCreater());

		SystemPictureInfo pictureInfo = actorInfo.getSystemPictureInfo();
		if(pictureInfo != null){
			String urlPath = actorInfo.getSystemPictureInfo().getUrlPath();
			dataMap.put("headImageUrl", StringUtils.isBlank(urlPath) ? "" : Configurations.buildDownloadUrl(urlPath));
		}else{
			dataMap.put("headImageUrl", "");
		}
		dataMap.put("name", actorInfo.getName() == null ? "" : actorInfo.getName());
		dataMap.put("authenticateLevel", String.valueOf(actorInfo.getAuthenticateLevel()));
		dataMap.put("showDetail", showInfo.getContent());
		
		//图片
		Map<String,Object> picParams = new HashMap<String,Object>();
		picParams.put("showId", showInfo.getId());
		List<ShowInfoPictures> picList = showInfoPicturesService.selectAll(picParams);
		List<Map<String,Object>> imageList = new ArrayList<Map<String,Object>>();
		if(picList != null && !picList.isEmpty()){
			List<String> imgUrls = new ArrayList<String>(); 
			for(ShowInfoPictures showInfoPictures : picList){
				String imgUrl = showInfoPictures.getSystemPictureInfo().getUrlPath();
				if(StringUtils.isNotBlank(imgUrl)) {
					imgUrls.add(imgUrl);				
				}
			}
			
			Collections.sort(imgUrls);
			for(String url : imgUrls) {
				Map<String,Object> imageMap = new HashMap<String,Object>();
				imageMap.put("imageUrl", Configurations.buildDownloadUrl(url));
				imageList.add(imageMap);
			}
		}
		dataMap.put("imageList", imageList);
		SystemPictureInfo systemVideoInfo =  showInfo.getSystemVideoInfo();
		if(systemVideoInfo != null){
			String videoUrlPath = systemVideoInfo.getUrlPath();
			dataMap.put("videoUrl", StringUtils.isBlank(videoUrlPath) ? "" : Configurations.buildDownloadUrl(videoUrlPath));
		}else{
			dataMap.put("videoUrl", "");
		}
		SystemPictureInfo systemVideoPreviewInfo = showInfo.getSystemVideoPreviewInfo();
		if(systemVideoPreviewInfo != null){
			String videoPreviewUrlPath = systemVideoPreviewInfo.getUrlPath();
			dataMap.put("videoPreviewUrl", StringUtils.isBlank(videoPreviewUrlPath) ? "" : Configurations.buildDownloadUrl(videoPreviewUrlPath));
		}else{
			dataMap.put("videoPreviewUrl", "");
		}
		
		dataMap.put("location", showInfo.getCity());
		dataMap.put("publishTime", RelativeDateFormat.format(showInfo.getCreateTime()));
		
		//关联评论
		Map<String,Object> commentParams = new HashMap<String,Object>();
		commentParams.put("showId", showInfo.getId());
		List<ShowComment> showsCommentNum = showCommentService.selectShowCommentNum(commentParams);
		if(showsCommentNum != null && !showsCommentNum.isEmpty()){
			ShowComment showComment = showsCommentNum.get(0);
			dataMap.put("commentNumber", showComment.getCommentNum());
		}else{
			dataMap.put("commentNumber", 0L);
		}
		
		//关联点赞
		Map<String,Object> praiseParams = new HashMap<String,Object>();
		praiseParams.put("showId", showInfo.getId());
		List<ShowPraise> praiseCommentNum = showPraiseService.selectShowPraiseNum(commentParams);
		if(praiseCommentNum != null && !praiseCommentNum.isEmpty()){
			ShowPraise showPraise = praiseCommentNum.get(0);
			dataMap.put("praiseNumber", showPraise.getPraiseNum());
		}else{
			dataMap.put("praiseNumber", 0L);
		}
		
		//当前用户是否对该详情点赞
		Map<String,Object> isPraseParams = new HashMap<String,Object>();
		isPraseParams.put("showId", showId);
		isPraseParams.put("creater", actorId);
		List<ShowPraise> praiseList = showPraiseService.selectAll(isPraseParams);
		if(praiseList == null || praiseList.isEmpty()){
			dataMap.put("isPraise", "0");
		}else{
			dataMap.put("isPraise", "1");
		}
		
		resultMap.put("data", dataMap);
		resultMap.put("result", 1);
		resultMap.put("msg", "操作成功!");
		return resultMap;
	}
	
	@RequestMapping(value = "api/getShowCommentList")
	@ResponseBody
	public Map<String,Object> getShowCommentList(HttpServletRequest request, HttpServletResponse response,String showId,String actorId,
			Integer page,Integer pageSize){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		PageInfo<ShowComment> pageInfo = new PageInfo<ShowComment>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);
		ShowComment info = new ShowComment();
		info.setShowId(showId);
		info.setStatus(0);
		info.setSort("createTime");
		info.setOrder("desc");
		showCommentService.selectAll(info, pageInfo);
		List<ShowComment> list = pageInfo.getRows();
		if(list == null || list.isEmpty()){
			return buildEmptyData(pageInfo.getTotal(), pageInfo.getPage(), pageInfo.getPageSize());
		}
		
		List<String> createrList = new ArrayList<String>();
		List<String> toUserIdList = new ArrayList<String>();
		for(ShowComment showComment : list){
			createrList.add(showComment.getCreater());
			if(StringUtils.isNotBlank(showComment.getToUserId())){
				toUserIdList.add(showComment.getToUserId());
			}			
		}
		
		//关联发布人
		List<ActorInfo> createrInfoList = actorInfoService.selectByIds(createrList);
		Map<String,ActorInfo> createrMap = new HashMap<String,ActorInfo>();
		for(ActorInfo actorInfo : createrInfoList){
			createrMap.put(actorInfo.getId(), actorInfo);
		}
		
		//关联接受人
		Map<String,ActorInfo> toUserMap = new HashMap<String,ActorInfo>();
		if(toUserIdList != null && !toUserIdList.isEmpty()){
			List<ActorInfo> toUserInfoList = actorInfoService.selectByIds(toUserIdList);
			for(ActorInfo actorInfo : toUserInfoList){
				toUserMap.put(actorInfo.getId(), actorInfo);
			}
		}
		
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		for(ShowComment showComment : list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id", showComment.getId());
			dataMap.put("creater", showComment.getCreater());
			
			//发送人信息
			
			ActorInfo creater = createrMap.get(showComment.getCreater());
			SystemPictureInfo picInfo = creater.getSystemPictureInfo();
			String urlPath = "";
			if(picInfo != null) {
				urlPath = picInfo.getUrlPath();
			}

			dataMap.put("headImageUrl", StringUtils.isBlank(urlPath) ? "" : Configurations.buildDownloadUrl(urlPath));
			dataMap.put("name", creater.getName());

			//接受者信息
			ActorInfo toUserInfo = toUserMap.get(showComment.getToUserId());
			if(toUserInfo == null){
				dataMap.put("toUserHeadImageUrl", "");
				dataMap.put("toUserName", "");
			}else{
				SystemPictureInfo toUserPicInfo = toUserInfo.getSystemPictureInfo();
				String toUserUrlpath = "";
				if(toUserPicInfo != null) {
					toUserUrlpath = picInfo.getUrlPath();
				}
				dataMap.put("toUserHeadImageUrl", StringUtils.isBlank(toUserUrlpath) ? "" : Configurations.buildDownloadUrl(toUserUrlpath));
				dataMap.put("toUserName", toUserInfo.getName());
			}
			dataMap.put("commentDetail", showComment.getContent());
			dataMap.put("commentTime", DateUtils.getDateTimeMinFormat(showComment.getCreateTime()));
			ShowCommentPraise param = new ShowCommentPraise();
			param.setCommentId(showComment.getId());
			dataMap.put("praiseNum", showCommentPraiseService.selectCount(param));
			
			param = new ShowCommentPraise();
			param.setCommentId(showComment.getId());
			param.setCreater(actorId);
			dataMap.put("isPraise", showCommentPraiseService.selectCount(param));
			dataList.add(dataMap);
		}
		
		Map<String,Object> rowsMap = new HashMap<String,Object>();
		rowsMap.put("rows", dataList);
		rowsMap.put("total", pageInfo.getTotal());
		rowsMap.put("page", page);
		rowsMap.put("pages", pageInfo.getPages());
		rowsMap.put("pageSize", pageSize);
		resultMap.put("msg", "操作成功!");
		resultMap.put("result", 1);
		resultMap.put("data", rowsMap);
		return resultMap;
	}
	
	/**
	 * 发布评论
	 * @param request
	 * @param response
	 * @param info
	 * @param announceInfo 
	 * @return
	 */
	@RequestMapping(value = "api/addShowComment")
	@ResponseBody
	public Map<String,Object> addShowComment(HttpServletRequest request, HttpServletResponse response,ShowComment info, ActorCollect announceInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
			
		info.setId(UUIDUtil.getUUID());
		info.setCreateTime(new Date());
		info.setStatus(0);
		int retCount = showCommentService.insert(info);
		if(retCount > 0){
			result.put("data", new HashMap<String,Object>());
			result.put("msg", "发布评论成功!");
			result.put("result", 1);
			
			// 发送消息通知
			String creater = info.getCreater();
			String toUserId = info.getToUserId();
			String showCreater = null;
			String showType="";
			
			ShowInfo showInfo = showInfoService.selectById(info.getShowId());
			if(showInfo != null) {
				showCreater = showInfo.getCreater();
				showType = showInfo.getType();
			}
			
			// 判断是否是普通评论
			if(StringUtils.isBlank(toUserId)) {
				ActorInfo showCreaterInfo = actorInfoService.selectById(showCreater);
				ActorInfo commentCreaterInfo = actorInfoService.selectById(creater);
				if(showCreaterInfo != null && commentCreaterInfo != null && showInfo != null) {
					// 发送通知消息
					String message = String.format("“%s”评论了您发布的秀。[点击查看]", StringUtils.trimToEmpty(commentCreaterInfo.getName()));
					Map<String, Object> ext = new HashMap<String, Object>();
					ext.put("fromType", "showNotify");
					ext.put("atid", showInfo.getId());
					ext.put("showType", showType);
					ext.put("creater", showInfo.getCreater());
					//Object resp = EasemobUtil.sendShowMessage(showCreaterInfo.getId(), message, ext);
					String[] targetIds = {showCreaterInfo.getId()};
					JSONObject json = JSONObject.fromObject(ext);
					RongCloudMethodUtil.privateMessage("show",message,  targetIds, json.toString());
					logger.debug("---------------------- send message resp -----------------------------------");
					//logger.debug(resp.toString());
				}
			} else {
				ActorInfo commentCreaterInfo = actorInfoService.selectById(creater);
				if(commentCreaterInfo != null && showInfo != null) {
					// 发送通知消息
					String message = String.format("“%s”回复了您的评论。[点击查看]", StringUtils.trimToEmpty(commentCreaterInfo.getName()));
					Map<String, Object> ext = new HashMap<String, Object>();
					ext.put("fromType", "showNotify");
					ext.put("atid", showInfo.getId());
					ext.put("showType", showType);
					ext.put("creater", showInfo.getCreater());
					//Object resp = EasemobUtil.sendShowMessage(toUserId, message, ext);
					String[] targetIds = {toUserId};
					JSONObject json = JSONObject.fromObject(ext);
					
					RongCloudMethodUtil.privateMessage("show",message,  targetIds, json.toString());
					logger.debug("---------------------- send message resp -----------------------------------");
					//logger.debug(resp.toString());
				}
			}
		}else{
			result.put("data", new HashMap<String,Object>());
			result.put("msg", "发布评论失败!");
			result.put("result", 0);
		}
		return result;
	}
	
	/**
	 * 点赞/取消点赞
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "api/praiseShow")
	@ResponseBody
	public Map<String,Object> praiseShow(HttpServletRequest request, HttpServletResponse response,ShowPraise info) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ShowPraise> list = showPraiseService.selectAll(info);
		int retCount = 0;
		Map<String,Object> dataMap = new HashMap<String,Object>();
		if(list == null || list.isEmpty()){
			info.setId(UUIDUtil.getUUID());
			info.setCreateTime(new Date());
			retCount = showPraiseService.insert(info);
			if(retCount > 0){
				dataMap.put("nowState", "1");
				result.put("data", dataMap);
				result.put("msg", "点赞成功!");
				result.put("result", 1);
				
				// 发送点赞通知消息
				ShowInfo showInfo = showInfoService.selectById(info.getShowId());
				if(showInfo != null) {
					ActorInfo praiseCreaterInfo = actorInfoService.selectById(info.getCreater());
					if(praiseCreaterInfo != null && !StringUtils.equalsIgnoreCase(info.getCreater(), showInfo.getCreater()) ) {
						String message = String.format("“%s”点赞了您发布的秀。[点击查看]", StringUtils.trimToEmpty(praiseCreaterInfo.getName()));
						Map<String, Object> ext = new HashMap<String, Object>();
						ext.put("fromType", "showNotify");
						ext.put("atid", showInfo.getId());
						ext.put("showType", showInfo.getType());
						ext.put("creater", showInfo.getCreater());
						//Object resp = EasemobUtil.sendShowMessage(showInfo.getCreater(), message, ext);
						String[] targetIds = {showInfo.getCreater()};
						JSONObject json = JSONObject.fromObject(ext);
						RongCloudMethodUtil.privateMessage("show",message,  targetIds, json.toString());
						logger.debug("---------------------- send message resp -----------------------------------");
						//logger.debug(resp.toString());
					}
				}
			}else{
				dataMap.put("nowState", "0");
				result.put("data", dataMap);
				result.put("msg", "点赞失败!");
				result.put("result", 0);
			}
		}else{
			for(ShowPraise showPraise : list){
				retCount += showPraiseService.delete(showPraise);
			}
			if(retCount > 0){
				dataMap.put("nowState", "0");
				result.put("data", dataMap);
				result.put("msg", "取消点赞成功!");
				result.put("result", 1);
			}else{
				dataMap.put("nowState", "1");
				result.put("data", dataMap);
				result.put("msg", "取消点赞失败!");
				result.put("result", 0);
			}
		}
		return result;
	}
	
	
	@RequestMapping(value = "api/getShowPraiseList")
	@ResponseBody
	public Map<String,Object> getShowPraiseList(HttpServletRequest request, HttpServletResponse response,String showId,
			Integer page,Integer pageSize){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		PageInfo<ShowPraise> pageInfo = new PageInfo<ShowPraise>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);
		ShowPraise info = new ShowPraise();
		info.setShowId(showId);
		info.setSort("createTime");
		info.setOrder("desc");
		showPraiseService.selectAll(info, pageInfo);
		List<ShowPraise> list = pageInfo.getRows();
		if(list == null || list.isEmpty()){
			return buildEmptyData(pageInfo.getTotal(), pageInfo.getPage(), pageInfo.getPageSize());
		}
		
		List<String> createrList = new ArrayList<String>();
		for(ShowPraise showPraise : list){
			createrList.add(showPraise.getCreater());		
		}
		
		//关联发布人
		List<ActorInfo> createrInfoList = actorInfoService.selectByIds(createrList);
		Map<String,ActorInfo> createrMap = new HashMap<String,ActorInfo>();
		for(ActorInfo actorInfo : createrInfoList){
			createrMap.put(actorInfo.getId(), actorInfo);
		}
		
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		for(ShowPraise showPraise : list){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			//点赞人信息
			ActorInfo creater = createrMap.get(showPraise.getCreater());
			SystemPictureInfo picInfo = creater.getSystemPictureInfo();
			String urlPath = "";
			if(picInfo != null) {
				urlPath = picInfo.getUrlPath();
			}

			dataMap.put("headImageUrl", StringUtils.isBlank(urlPath) ? "" : Configurations.buildDownloadUrl(urlPath));
			dataMap.put("name", creater.getName());
			dataMap.put("userId", creater.getId());
			dataList.add(dataMap);
		}
		
		Map<String,Object> rowsMap = new HashMap<String,Object>();
		rowsMap.put("rows", dataList);
		rowsMap.put("total", pageInfo.getTotal());
		rowsMap.put("page", page);
		rowsMap.put("pageSize", pageSize);
		resultMap.put("msg", "操作成功!");
		resultMap.put("result", 1);
		resultMap.put("data", rowsMap);
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: deleteShow
	 * @Description: 删除秀
	 * @return JSON
	 * @author CrazyT
	 * 
	 */	
	@RequestMapping(value = "api/deleteShow")
	public void deleteShow(HttpServletRequest request, HttpServletResponse response, String id) {
		
		if(StringUtils.isBlank(id)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少id参数", null);
			return;
		}

		ShowInfo condition = new ShowInfo();
		condition.setId(id);
		condition.setStatus("1"); // 删除
		
		showInfoService.update(condition);
		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, null);
	}
	
	/**
	 * 
	 * @Title: addShareCount
	 * @Description: 点赞数量+1
	 * @return JSON
	 * @author CrazyT
	 * 
	 */	
	@RequestMapping(value = "api/addShareCount")
	public void addShareCount(HttpServletRequest request, HttpServletResponse response, String id, String usrId) {
		
		if(StringUtils.isBlank(id)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少id参数", null);
			return;
		}

		ShowInfo info = showInfoService.selectById(id);
		if(info == null || "1".equals(info.getStatus())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "指定的秀不存在，请检查id", null);
			return;
		}
		
		ShowInfo condition = new ShowInfo();
		condition.setId(id);
		condition.setShareNum(info.getShareNum() + 1);
		
		showInfoService.update(condition);
		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, null);
	}
	
	private Map<String, Object> buildEmptyData(int total, int page, int pageSize) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> rowsMap = new HashMap<String, Object>();
		rowsMap.put("rows", new ArrayList<Map<String,Object>>());
		rowsMap.put("total", total);
		rowsMap.put("page", page);
		rowsMap.put("pageSize", pageSize);
		resultMap.put("data", rowsMap);
		resultMap.put("result", 1);
		resultMap.put("msg", "操作成功!");
		return resultMap;
	}

	
	private Map<String,Object> buildEmptyDataForDetail(){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("data", new HashMap<String,Object>());
		resultMap.put("result", 1);
		resultMap.put("msg", "操作成功!");
		return resultMap;
	}
	
	/**
	 * 秀一秀详情
	 * @param request
	 * @param response
	 * @param showId
	 * @return
	 */
	@RequestMapping(value = "api/getShowDetailNew")
	@ResponseBody
	public Map<String,Object> getShowDetailNew(HttpServletRequest request, HttpServletResponse response,String showId,String actorId,String orderstr,String rowCount) {
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String, Object>> dataMapList = new ArrayList<Map<String,Object>>();
		//通过showId得出当前所在顺序号
				
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("showId", showId);
		param.put("orderstr", orderstr);
		param.put("rowCount", rowCount);
		List<ShowInfo> list = showInfoService.selectTurnList(param);
		for(ShowInfo temp:list){
			ShowInfo showInfo = showInfoService.selectById(temp.getId());
			if(showInfo == null){
				return buildEmptyDataForDetail();
			}
			
			//关联用户
			ActorInfo actorInfo = actorInfoService.selectById(showInfo.getCreater());
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("id", showInfo.getId());
			dataMap.put("mediaType", showInfo.getType());
			dataMap.put("shareNumber", showInfo.getShareNum());
			dataMap.put("userId", showInfo.getCreater());
			
			SystemPictureInfo pictureInfo = actorInfo.getSystemPictureInfo();
			if(pictureInfo != null){
				String urlPath = actorInfo.getSystemPictureInfo().getUrlPath();
				dataMap.put("headImageUrl", StringUtils.isBlank(urlPath) ? "" : Configurations.buildDownloadUrl(urlPath));
			}else{
				dataMap.put("headImageUrl", "");
			}
			dataMap.put("name", actorInfo.getName() == null ? "" : actorInfo.getName());
			dataMap.put("authenticateLevel", String.valueOf(actorInfo.getAuthenticateLevel()));
			dataMap.put("showDetail", showInfo.getContent());
			
			//图片
			Map<String,Object> picParams = new HashMap<String,Object>();
			picParams.put("showId", showInfo.getId());
			List<ShowInfoPictures> picList = showInfoPicturesService.selectAll(picParams);
			List<Map<String,Object>> imageList = new ArrayList<Map<String,Object>>();
			if(picList != null && !picList.isEmpty()){
				List<String> imgUrls = new ArrayList<String>(); 
				for(ShowInfoPictures showInfoPictures : picList){
					String imgUrl = showInfoPictures.getSystemPictureInfo().getUrlPath();
					if(StringUtils.isNotBlank(imgUrl)) {
						imgUrls.add(imgUrl);				
					}
				}
				
				Collections.sort(imgUrls);
				for(String url : imgUrls) {
					Map<String,Object> imageMap = new HashMap<String,Object>();
					imageMap.put("imageUrl", Configurations.buildDownloadUrl(url));
					imageList.add(imageMap);
				}
			}
			dataMap.put("imageList", imageList);
			SystemPictureInfo systemVideoInfo =  showInfo.getSystemVideoInfo();
			if(systemVideoInfo != null){
				String videoUrlPath = systemVideoInfo.getUrlPath();
				dataMap.put("videoUrl", StringUtils.isBlank(videoUrlPath) ? "" : Configurations.buildDownloadUrl(videoUrlPath));
			}else{
				dataMap.put("videoUrl", "");
			}
			SystemPictureInfo systemVideoPreviewInfo = showInfo.getSystemVideoPreviewInfo();
			if(systemVideoPreviewInfo != null){
				String videoPreviewUrlPath = systemVideoPreviewInfo.getUrlPath();
				dataMap.put("videoPreviewUrl", StringUtils.isBlank(videoPreviewUrlPath) ? "" : Configurations.buildDownloadUrl(videoPreviewUrlPath));
			}else{
				dataMap.put("videoPreviewUrl", "");
			}
			
			dataMap.put("location", showInfo.getCity());
			dataMap.put("publishTime", RelativeDateFormat.format(showInfo.getCreateTime()));
			
			//关联评论
			Map<String,Object> commentParams = new HashMap<String,Object>();
			commentParams.put("showId", showInfo.getId());
			List<ShowComment> showsCommentNum = showCommentService.selectShowCommentNum(commentParams);
			if(showsCommentNum != null && !showsCommentNum.isEmpty()){
				ShowComment showComment = showsCommentNum.get(0);
				dataMap.put("commentNumber", showComment.getCommentNum());
			}else{
				dataMap.put("commentNumber", 0L);
			}
			
			//关联点赞
			Map<String,Object> praiseParams = new HashMap<String,Object>();
			praiseParams.put("showId", showInfo.getId());
			List<ShowPraise> praiseCommentNum = showPraiseService.selectShowPraiseNum(commentParams);
			if(praiseCommentNum != null && !praiseCommentNum.isEmpty()){
				ShowPraise showPraise = praiseCommentNum.get(0);
				dataMap.put("praiseNumber", showPraise.getPraiseNum());
			}else{
				dataMap.put("praiseNumber", 0L);
			}
			
			//当前用户是否对该详情点赞
			Map<String,Object> isPraseParams = new HashMap<String,Object>();
			isPraseParams.put("showId", showInfo.getId());
			isPraseParams.put("creater", actorId);
			List<ShowPraise> praiseList = showPraiseService.selectAll(isPraseParams);
			if(praiseList == null || praiseList.isEmpty()){
				dataMap.put("isPraise", "0");
			}else{
				dataMap.put("isPraise", "1");
			}
			
			ActorCollect actorparam = new ActorCollect();
			actorparam.setActorId(showInfo.getCreater());
			actorparam.setCreater(actorId);
			
			dataMap.put("isAtenttion",actorCollectService.selectCount(actorparam) );
			
			dataMapList.add(dataMap);
		}
		
		
		
		resultMap.put("data", dataMapList);
		resultMap.put("result", 1);
		resultMap.put("msg", "操作成功!");
		return resultMap;
	}
	/**
	 * 秀一秀详情
	 * @param request
	 * @param response
	 * @param showId
	 * @return
	 */
	@RequestMapping(value = "api/getShowDetailBoth")
	@ResponseBody
	public Map<String,Object> getShowDetailBoth(HttpServletRequest request, HttpServletResponse response,String showId,String actorId,String ascCount,String descCount) {
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String, Object>> dataMapList = new ArrayList<Map<String,Object>>();
		//通过showId得出当前所在顺序号
		
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("showId", showId);
			param.put("orderstr", "asc");
			param.put("rowCount", ascCount);
			List<ShowInfo> list = showInfoService.selectTurnList(param);
			int upCount= list.size();
			list.add(showInfoService.selectById(showId));
			param.put("showId", showId);
			param.put("orderstr", "desc");
			param.put("rowCount", descCount);
			List<ShowInfo> list2 = showInfoService.selectTurnList(param);
			list.addAll(list2);
			int rownum = 0;
			for(ShowInfo temp:list){
				ShowInfo showInfo = showInfoService.selectById(temp.getId());
				if(showInfo == null){
					return buildEmptyDataForDetail();
				}
				
				//关联用户
				ActorInfo actorInfo = actorInfoService.selectById(showInfo.getCreater());
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", showInfo.getId());
				dataMap.put("mediaType", showInfo.getType());
				dataMap.put("shareNumber", showInfo.getShareNum());
				dataMap.put("userId", showInfo.getCreater());
				
				if(rownum<upCount){
					dataMap.put("flag", "asc");
				}else if(rownum==upCount){
					dataMap.put("flag", "current");
				}else{
					dataMap.put("flag", "desc");
				}
				rownum++;
				
				SystemPictureInfo pictureInfo = actorInfo.getSystemPictureInfo();
				if(pictureInfo != null){
					String urlPath = actorInfo.getSystemPictureInfo().getUrlPath();
					dataMap.put("headImageUrl", StringUtils.isBlank(urlPath) ? "" : Configurations.buildDownloadUrl(urlPath));
				}else{
					dataMap.put("headImageUrl", "");
				}
				dataMap.put("name", actorInfo.getName() == null ? "" : actorInfo.getName());
				dataMap.put("authenticateLevel", String.valueOf(actorInfo.getAuthenticateLevel()));
				dataMap.put("showDetail", showInfo.getContent());
				
				//图片
				Map<String,Object> picParams = new HashMap<String,Object>();
				picParams.put("showId", showInfo.getId());
				List<ShowInfoPictures> picList = showInfoPicturesService.selectAll(picParams);
				List<Map<String,Object>> imageList = new ArrayList<Map<String,Object>>();
				if(picList != null && !picList.isEmpty()){
					List<String> imgUrls = new ArrayList<String>(); 
					for(ShowInfoPictures showInfoPictures : picList){
						String imgUrl = showInfoPictures.getSystemPictureInfo().getUrlPath();
						if(StringUtils.isNotBlank(imgUrl)) {
							imgUrls.add(imgUrl);				
						}
					}
					
					Collections.sort(imgUrls);
					for(String url : imgUrls) {
						Map<String,Object> imageMap = new HashMap<String,Object>();
						imageMap.put("imageUrl", Configurations.buildDownloadUrl(url));
						imageList.add(imageMap);
					}
				}
				dataMap.put("imageList", imageList);
				SystemPictureInfo systemVideoInfo =  showInfo.getSystemVideoInfo();
				if(systemVideoInfo != null){
					String videoUrlPath = systemVideoInfo.getUrlPath();
					dataMap.put("videoUrl", StringUtils.isBlank(videoUrlPath) ? "" : Configurations.buildDownloadUrl(videoUrlPath));
				}else{
					dataMap.put("videoUrl", "");
				}
				SystemPictureInfo systemVideoPreviewInfo = showInfo.getSystemVideoPreviewInfo();
				if(systemVideoPreviewInfo != null){
					String videoPreviewUrlPath = systemVideoPreviewInfo.getUrlPath();
					dataMap.put("videoPreviewUrl", StringUtils.isBlank(videoPreviewUrlPath) ? "" : Configurations.buildDownloadUrl(videoPreviewUrlPath));
				}else{
					dataMap.put("videoPreviewUrl", "");
				}
				
				dataMap.put("location", showInfo.getCity());
				dataMap.put("publishTime", RelativeDateFormat.format(showInfo.getCreateTime()));
				
				//关联评论
				Map<String,Object> commentParams = new HashMap<String,Object>();
				commentParams.put("showId", showInfo.getId());
				List<ShowComment> showsCommentNum = showCommentService.selectShowCommentNum(commentParams);
				if(showsCommentNum != null && !showsCommentNum.isEmpty()){
					ShowComment showComment = showsCommentNum.get(0);
					dataMap.put("commentNumber", showComment.getCommentNum());
				}else{
					dataMap.put("commentNumber", 0L);
				}
				
				//关联点赞
				Map<String,Object> praiseParams = new HashMap<String,Object>();
				praiseParams.put("showId", showInfo.getId());
				List<ShowPraise> praiseCommentNum = showPraiseService.selectShowPraiseNum(commentParams);
				if(praiseCommentNum != null && !praiseCommentNum.isEmpty()){
					ShowPraise showPraise = praiseCommentNum.get(0);
					dataMap.put("praiseNumber", showPraise.getPraiseNum());
				}else{
					dataMap.put("praiseNumber", 0L);
				}
				
				//当前用户是否对该详情点赞
				Map<String,Object> isPraseParams = new HashMap<String,Object>();
				isPraseParams.put("showId", showInfo.getId());
				isPraseParams.put("creater", actorId);
				List<ShowPraise> praiseList = showPraiseService.selectAll(isPraseParams);
				if(praiseList == null || praiseList.isEmpty()){
					dataMap.put("isPraise", "0");
				}else{
					dataMap.put("isPraise", "1");
				}
				ActorCollect actorparam = new ActorCollect();
				actorparam.setActorId(showInfo.getCreater());
				actorparam.setCreater(actorId);
				
				dataMap.put("isAtenttion",actorCollectService.selectCount(actorparam) );
				dataMapList.add(dataMap);
			}
			
		

		
		
		resultMap.put("data", dataMapList);
		resultMap.put("result", 1);
		resultMap.put("msg", "操作成功!");
		return resultMap;
	}
	/**
	 * 点赞/取消点赞
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "api/praiseShowComment")
	@ResponseBody
	public Map<String,Object> praiseShow(HttpServletRequest request, HttpServletResponse response,ShowCommentPraise info) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ShowCommentPraise> list = showCommentPraiseService.selectAll(info);
		int retCount = 0;
		Map<String,Object> dataMap = new HashMap<String,Object>();
		if(list == null || list.isEmpty()){
			info.setId(UUIDUtil.getUUID());
			info.setCreateTime(new Date());
			retCount = showCommentPraiseService.insert(info);
			if(retCount > 0){
				dataMap.put("nowState", "1");
				result.put("data", dataMap);
				result.put("msg", "点赞成功!");
				result.put("result", 1);
				
				/*// 发送点赞通知消息
				ShowInfo showInfo = showInfoService.selectById(info.getShowId());
				if(showInfo != null) {
					ActorInfo praiseCreaterInfo = actorInfoService.selectById(info.getCreater());
					if(praiseCreaterInfo != null && !StringUtils.equalsIgnoreCase(info.getCreater(), showInfo.getCreater()) ) {
						String message = String.format("“%s”点赞了您发布的秀。[点击查看]", StringUtils.trimToEmpty(praiseCreaterInfo.getName()));
						Map<String, Object> ext = new HashMap<String, Object>();
						ext.put("fromType", "showNotify");
						ext.put("atid", showInfo.getId());
						ext.put("showType", showInfo.getType());
						ext.put("creater", showInfo.getCreater());
						//Object resp = EasemobUtil.sendShowMessage(showInfo.getCreater(), message, ext);
						String[] targetIds = {showInfo.getCreater()};
						RongCloudMethodUtil.privateMessage("show",message,  targetIds, null);
						logger.debug("---------------------- send message resp -----------------------------------");
						//logger.debug(resp.toString());
					}
				}*/
			}else{
				dataMap.put("nowState", "0");
				result.put("data", dataMap);
				result.put("msg", "点赞失败!");
				result.put("result", 0);
			}
		}else{
			for(ShowCommentPraise showCommentPraise : list){
				retCount += showCommentPraiseService.delete(showCommentPraise);
			}
			if(retCount > 0){
				dataMap.put("nowState", "0");
				result.put("data", dataMap);
				result.put("msg", "取消点赞成功!");
				result.put("result", 1);
			}else{
				dataMap.put("nowState", "1");
				result.put("data", dataMap);
				result.put("msg", "取消点赞失败!");
				result.put("result", 0);
			}
		}
		return result;
	}
	@RequestMapping(value = "api/getAudioInfoList")
	@ResponseBody
	public Map<String,Object> getAudioInfoList(HttpServletRequest request, HttpServletResponse response,String name,
			Integer page,Integer pageSize){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		PageInfo<AudioInfo> pageInfo = new PageInfo<AudioInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);
		AudioInfo info = new AudioInfo();
		info.setAudioName(name);
		audioInfoService.selectAll(info, pageInfo);
		for(AudioInfo temp:pageInfo.getRows()){
			temp.setDownloadUrl(Configurations.buildDownloadUrl(temp.getDownloadUrl()));
		}
		Map<String,Object> rowsMap = new HashMap<String,Object>();
		rowsMap.put("rows", pageInfo.getRows());
		rowsMap.put("total", pageInfo.getTotal());
		rowsMap.put("page", page);
		rowsMap.put("pageSize", pageSize);
		resultMap.put("msg", "操作成功!");
		resultMap.put("result", 1);
		resultMap.put("data", rowsMap);
		//resultMap.put("data", pageInfo);
		return resultMap;
	}
}
