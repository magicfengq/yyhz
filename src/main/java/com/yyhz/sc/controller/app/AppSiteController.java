package com.yyhz.sc.controller.app;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yyhz.constant.AppRetCode;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.data.model.SiteInfo;
import com.yyhz.sc.data.model.SiteList;
import com.yyhz.sc.data.model.SiteListPicture;
import com.yyhz.sc.data.model.SystemPictureInfo;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.RecommendsiteService;
import com.yyhz.sc.services.SiteInfoService;
import com.yyhz.sc.services.SiteListPictureService;
import com.yyhz.sc.services.SiteListService;
import com.yyhz.utils.DateUtils;
import com.yyhz.utils.UUIDUtil;
import com.yyhz.utils.stream.config.Configurations;

@Controller
@RequestMapping(value = "api")
public class AppSiteController extends BaseController {

	@Resource
	private SiteListService siteListService;
	@Resource
	private SiteInfoService siteInfoService;
	@Resource
	private ActorInfoService actorInfoService;
	@Resource
	private RecommendsiteService recommendsiteService;
	@Resource
	private SiteListPictureService siteListPictureService;
	
	//private float 

	/**
	 * 
	 * @Title: getSiteList
	 * @Description: 获取场地列表
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getSiteList")
	public void getSiteList(HttpServletRequest request, HttpServletResponse response, SiteList req, Integer page, Integer pageSize) {

		if(page == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "没有page参数", null);
		}
		
		if(pageSize == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "没有pageSize参数", null);
		}
		SiteList condition = new SiteList();		
		if(StringUtils.isNotBlank(req.getCity())) {
			condition.setCity(req.getCity());
		}
		
		if(StringUtils.isNotBlank(req.getPublicType())) {
			condition.setPublicType(req.getPublicType());
		}
		
		if(StringUtils.isNotBlank(req.getType())) {
			condition.setType(req.getType());
		}
		
		if(StringUtils.isNoneBlank(req.getKeyword())) {
			condition.setKeyword(req.getKeyword());
		}
		
		condition.setSort("createTime");
		
		PageInfo<SiteList> pageInfo = new PageInfo<SiteList>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);
		
		
		siteListService.selectAll(condition, pageInfo);
		
		Map<String, Object> ret = new HashMap<String, Object>();
		
		List<Map<String, Object>> siteList= new ArrayList<Map<String, Object>>();
		
		for(SiteList siteInfo : pageInfo.getRows()) {
			Map<String, Object> siteMap = new HashMap<String, Object>();
			siteMap.put("id",siteInfo.getId());
			siteMap.put("siteName",siteInfo.getSiteName());
			siteMap.put("phone",siteInfo.getPhone());
			siteMap.put("passageway",siteInfo.getPassageway());
			siteMap.put("alert",siteInfo.getAlert());
			siteMap.put("address",siteInfo.getAddress());
			siteMap.put("latitude",siteInfo.getLatitude());
			siteMap.put("longitude",siteInfo.getLongitude());
			siteMap.put("textArea",siteInfo.getTextArea());
			siteMap.put("type", siteInfo.getType());
			siteMap.put("publicType", siteInfo.getPublicType());
			siteMap.put("publicTypeName", siteInfo.getPublicTypeName());
			siteMap.put("refereeName", siteInfo.getRefereeName());
			siteMap.put("refereePhone", siteInfo.getRefereePhone());
			
			List<String> urlList = new ArrayList<String>();
			if(StringUtils.isNotBlank(siteInfo.getLogoUrl())) {
				urlList.add(Configurations.buildDownloadUrl(siteInfo.getLogoUrl()));
			}
			if(StringUtils.isNotBlank(siteInfo.getSiteImgUrls())) {
				String[] imgUrls = StringUtils.split(siteInfo.getSiteImgUrls(), ',');
				Arrays.sort(imgUrls);
				for(String url : imgUrls) {
					urlList.add(Configurations.buildDownloadUrl(url));
				}
			}
			siteMap.put("imageUrlArr",urlList);
			
			// 计算距离
			double lat1 = NumberUtils.toDouble(req.getLatitude()); // 纬度
			double lng1 = NumberUtils.toDouble(req.getLongitude()); // 经度
			double lat2 = NumberUtils.toDouble(siteInfo.getLatitude()); // 纬度
			double lng2 = NumberUtils.toDouble(siteInfo.getLongitude()); // 经度
			if(lat1 == 0 || lng1 == 0 || lat2 == 0 || lng2 == 0) { // 转换出错，在中国经纬度都不可能是0
				siteMap.put("distance", "");
			}else {
				double distance = com.yyhz.utils.Utils.getDistance(lng1, lat1, lng2, lat2);

				siteMap.put("distance", new DecimalFormat("#").format(distance));
			}
			
			siteList.add(siteMap);
		}
		
		ret.put("rows", siteList);
		ret.put("page", pageInfo.getPage());
		ret.put("pageSize", pageInfo.getPageSize());
		ret.put("total", pageInfo.getTotal());


		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);
	}
	
	/**
	 * 
	 * @Title: getSiteDetail
	 * @Description: 获取场地列表
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getSiteDetail")
	public void getSiteDetail(HttpServletRequest request, HttpServletResponse response, String id) {

		if(StringUtils.isBlank(id)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "没有id参数", null);
		}
				
		Map<String, Object> ret = new HashMap<String, Object>();

		SiteList siteList = siteListService.selectById(id);
		if(siteList != null) {
			ret.put("id", StringUtils.stripToEmpty(siteList.getId()));
			ret.put("siteName", StringUtils.stripToEmpty(siteList.getSiteName()));
			ret.put("phone", StringUtils.stripToEmpty(siteList.getPhone()));
			ret.put("passageway", StringUtils.stripToEmpty(siteList.getPassageway()));
			ret.put("alert", StringUtils.stripToEmpty(siteList.getAlert()));
			ret.put("address", StringUtils.stripToEmpty(siteList.getAddress()));
			ret.put("latitude", StringUtils.stripToEmpty(siteList.getLatitude()));
			ret.put("longitude", StringUtils.stripToEmpty(siteList.getLongitude()));
			ret.put("textArea", StringUtils.stripToEmpty(siteList.getTextArea()));
			ret.put("type", StringUtils.stripToEmpty(siteList.getType()));
			ret.put("publicType", StringUtils.stripToEmpty(siteList.getPublicType()));
			ret.put("publicTypeName", StringUtils.stripToEmpty(siteList.getPublicTypeName()));
			ret.put("refereeName", StringUtils.stripToEmpty(siteList.getRefereeName()));
			ret.put("refereePhone", StringUtils.stripToEmpty(siteList.getRefereePhone()));

			
			SiteInfo condition = new SiteInfo();
			condition.setSiteId(siteList.getId());
			condition.setStatus(0); // 评论状态：0正常；1已删除；
			condition.setSort("createTime");
		
			PageInfo<SiteInfo> pageInfo = new PageInfo<SiteInfo>();
			pageInfo.setPage(1);
			pageInfo.setPageSize(3);
			siteInfoService.selectAll(condition, pageInfo);
			
			List<Map<String, Object>> comments = new ArrayList<Map<String, Object>>();
			
			for(SiteInfo item : pageInfo.getRows()) {
				Map<String, Object> siteInfoMap = new HashMap<String, Object>();
				siteInfoMap.put("id", StringUtils.stripToEmpty(item.getId()));
				siteInfoMap.put("creater", StringUtils.stripToEmpty(item.getCreater()));
				siteInfoMap.put("content", StringUtils.stripToEmpty(item.getContent()));
				siteInfoMap.put("siteId", StringUtils.stripToEmpty(item.getSiteId()));
				siteInfoMap.put("createTime", StringUtils.stripToEmpty(DateUtils.getDateTimeMinFormat(item.getCreateTime())));
				siteInfoMap.put("createrName", StringUtils.stripToEmpty(item.getCreaterName()));
				if(item.getSystemPictureInfo() != null) {
					siteInfoMap.put("headImgUrl", Configurations.buildDownloadUrl(item.getSystemPictureInfo().getUrlPath()));
				}
				
				comments.add(siteInfoMap);
			}
					
			ret.put("comments", comments);
			
			ret.put("page", pageInfo.getPage());
			ret.put("pages", pageInfo.getPages());
			ret.put("pageSize", pageInfo.getPageSize());
			ret.put("total", pageInfo.getTotal());

			
			// 加载关联图片
			SiteListPicture sitePicture = new SiteListPicture();
			sitePicture.setSiteId(siteList.getId());
			
			List<SiteListPicture> sitePictureList = siteListPictureService.selectAll(sitePicture, "selectAllWithUrl");
			List<String> imgUrls = new ArrayList<String>();
			if(sitePictureList != null) {
				for(SiteListPicture item : sitePictureList) {
					imgUrls.add(Configurations.buildDownloadUrl(item.getImgUrl()));
				}

				Collections.sort(imgUrls);
				ret.put("imgUrls", imgUrls);
			}

		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "场地不存在", ret);
		}
		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);
	}
	
	/**
	 * 
	 * @Title: getCommentList
	 * @Description: 获取场地评论列表
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getCommentList")
	public void getCommentList(HttpServletRequest request, HttpServletResponse response, String id, Integer page, Integer pageSize) {

		if(StringUtils.isBlank(id)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "id参数错误", null);
		}

		if(page == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "page参数错误", null);
		}

		if(pageSize == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "pageSize参数错误", null);
		}

		Map<String, Object> ret = new HashMap<String, Object>();

		SiteInfo condition = new SiteInfo();
		condition.setSiteId(id);
		condition.setStatus(0); // 评论状态：0正常；1已删除；
		condition.setSort("createTime");
	
		PageInfo<SiteInfo> pageInfo = new PageInfo<SiteInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);
		siteInfoService.selectAll(condition, pageInfo);
		
		List<Map<String, Object>> comments = new ArrayList<Map<String, Object>>();
		
		for(SiteInfo item : pageInfo.getRows()) {
			Map<String, Object> siteInfoMap = new HashMap<String, Object>();
			siteInfoMap.put("id", item.getId());
			siteInfoMap.put("creater", item.getCreater());
			siteInfoMap.put("content", item.getContent());
			siteInfoMap.put("siteId", item.getSiteId());
			siteInfoMap.put("createTime", DateUtils.getDateTimeMinFormat(item.getCreateTime()));
			siteInfoMap.put("createrName", item.getCreaterName());
			if(item.getSystemPictureInfo() != null) {
				siteInfoMap.put("headImgUrl", Configurations.buildDownloadUrl(item.getSystemPictureInfo().getUrlPath()));
			}
			
			comments.add(siteInfoMap);
		}
		
		ret.put("comments", comments);
		ret.put("page", pageInfo.getPage());
		ret.put("pageSize", pageInfo.getPageSize());
		ret.put("total", pageInfo.getTotal());
		ret.put("pages", pageInfo.getPages());

				
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);
	}
	
	/**
	 * 
	 * @Title: AddComment
	 * @Description: 添加场地评论列表
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "AddComment")
	public void AddComment(HttpServletRequest request, HttpServletResponse response, SiteInfo req) {

		if(StringUtils.isBlank(req.getContent())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少content参数", null);
		}

		if(StringUtils.isBlank(req.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少creater参数", null);
		}

		if(StringUtils.isBlank(req.getSiteId())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少siteId参数", null);
		}
		
		ActorInfo actorInfo = actorInfoService.selectById(req.getCreater());
		if(actorInfo == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "创建者不存在", null);
		}
		
		SiteList site = siteListService.selectById(req.getSiteId());
		if(site == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "要评论的场地不存在", null);
		}
		
		SiteInfo siteInfo = new SiteInfo();
		siteInfo.setId(UUIDUtil.getUUID());
		siteInfo.setCreater(req.getCreater());
		siteInfo.setContent(req.getContent());
		siteInfo.setSiteId(req.getSiteId());
		siteInfo.setCreateTime(new Date());
		siteInfo.setImgUuid(actorInfo.getImgUuid());
		siteInfo.setStatus(0); // 评论状态：0正常；1已删除；
		
		int ret = siteInfoService.insert(siteInfo);
		if(ret > 0) {
			
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, siteInfo);
			
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "添加评论失败", null);	
		}	
	}
	
	/**
	 * 
	 * @Title: recommendSite
	 * @Description: 推荐场地
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "recommendSite")
	public void recommendSite(HttpServletRequest request, HttpServletResponse response, SiteList req, @RequestParam(required = false) MultipartFile[] imageFiles) {

		if(StringUtils.isBlank(req.getSiteName())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少siteName参数", null);
			return;
		}

		if(StringUtils.isBlank(req.getAddress())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少adderss参数", null);
			return;
		}
		/*
		 * 非必须参数
		if(StringUtils.isBlank(req.getPassageway())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少passageway参数", null);
			return;
		}
		
		if(StringUtils.isBlank(req.getPhone())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少phone参数", null);
			return;
		}
		 */	
		if(StringUtils.isBlank(req.getRefereeUuid())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少refereeUuid参数", null);
			return;
		}

		if(StringUtils.isBlank(req.getPublicType())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少publicType参数", null);
			return;
		}

		if(StringUtils.isBlank(req.getCity())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少city参数", null);
			return;
		}

		Map<String, Object> result = new HashMap<String, Object>();
		List<String> imgUrls = new ArrayList<String>();
		result.put("imageUrl", imgUrls);
		
		req.setId(UUIDUtil.getUUID());
		req.setCreateTime(new Date());
		req.setType("1"); // 类型：0后台创建1推荐（客户端上传）

		int ret = siteListService.insert(req);
		if(ret > 0) {
			
			List<String> picUuidList = new ArrayList<String>();
			if(imageFiles != null && imageFiles.length > 0){
				for(MultipartFile multipartFile : imageFiles){
					if(multipartFile == null){
						continue;
					}
					SystemPictureInfo pictureInfo = this.uploadFile2("recommendSite", multipartFile);
					picUuidList.add(pictureInfo.getUuid());
					imgUrls.add(Configurations.buildDownloadUrl(pictureInfo.getUrlPath()));
				}
			}
			

			String[] imgUuids = picUuidList.toArray(new String[0]);
			
			List<SiteListPicture> imgIdList = new ArrayList<SiteListPicture>();
			
			for(String uuid : imgUuids)
			{
				SiteListPicture siteListPicture = new SiteListPicture();
				siteListPicture.setId(UUIDUtil.getUUID());
				siteListPicture.setSiteId(req.getId());
				siteListPicture.setImgUuid(uuid);
				
				imgIdList.add(siteListPicture);
			}
			
			ret = siteListPictureService.insert(imgIdList, "batchInsert");
			
			result.put("id",req.getId());
			result.put("siteName",req.getSiteName());
			result.put("phone",req.getPhone());
			result.put("passageway",req.getPassageway());
			result.put("address",req.getAddress());
			result.put("textArea",req.getTextArea());
			result.put("refereeUuid",req.getRefereeUuid());
			result.put("refereeName",req.getRefereeName());
			result.put("refereePhone",req.getRefereePhone());
			result.put("publicType",req.getPublicType());
			result.put("city",req.getCity());
			
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, result);
			
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "添加推荐场地失败", result);	
		}
				
	}
}