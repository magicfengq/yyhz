package com.yyhz.sc.controller.app;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yyhz.constant.AppRetCode;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.data.model.ActorComment;
import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.data.model.Advert;
import com.yyhz.sc.data.model.AnnouceEnroll;
import com.yyhz.sc.data.model.AnnounceInfo;
import com.yyhz.sc.data.model.AnnouncePicture;
import com.yyhz.sc.data.model.CardInfo;
import com.yyhz.sc.data.model.ServiceInfo;
import com.yyhz.sc.data.model.ShowInfo;
import com.yyhz.sc.data.model.SiteInfo;
import com.yyhz.sc.data.model.SiteList;
import com.yyhz.sc.data.model.SiteListPicture;
import com.yyhz.sc.services.ActorCommentService;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.AdvertService;
import com.yyhz.sc.services.AnnounceInfoService;
import com.yyhz.sc.services.AnnouncePictureService;
import com.yyhz.sc.services.CardInfoService;
import com.yyhz.sc.services.ServiceInfoService;
import com.yyhz.sc.services.ShowInfoService;
import com.yyhz.sc.services.SiteInfoService;
import com.yyhz.sc.services.SiteListPictureService;
import com.yyhz.sc.services.SiteListService;
import com.yyhz.utils.DateUtils;
import com.yyhz.utils.stream.config.Configurations;

@Controller
public class AppWebController extends BaseController {

	private static DecimalFormat formatter = new DecimalFormat("####0.0");
	
	@Resource
	private SiteListService siteListService;
	@Resource
	private SiteInfoService siteInfoService;
	@Resource
	private SiteListPictureService siteListPictureService;
	@Resource
	private CardInfoService cardInfoService;
	@Resource
	private ActorInfoService actorInfoService;
	@Resource
	private ActorCommentService actorCommentService;
	@Resource
	private ShowInfoService showInfoService;
	@Resource
	private ServiceInfoService serviceInfoService;
	@Resource
	private AdvertService advertService;
	@Resource
	private AnnounceInfoService announceInfoService;
	@Resource
	private AnnouncePictureService announcePictureService;

	/**
	 * 
	 * @Title: 获取注册协议、帮助等信息内容
	 * @Description: 获取注册协议、帮助等信息内容
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	private String getServiceInfo(String type) {
		
		String content;
		
		ServiceInfo condition = new ServiceInfo();
		condition.setType(type);
		
		ServiceInfo info = serviceInfoService.selectEntity(condition);
		if(info != null) {
			content = info.getContext();
		}else {
			content = "";
		}
		
		return content;
	}
	
	/**
	 * 
	 * @Title: 获取轮播广告
	 * @Description: 获取轮播广告
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "webview/advert")
	public String getAdvert(HttpServletRequest request, HttpServletResponse response, String id, ModelMap model) {
		
		if(StringUtils.isNotBlank(id)) {
			Advert advert = advertService.selectById(id);
			if(advert != null) {
				model.addAttribute("content",advert.getParams());
			}else {
				model.addAttribute("content","");						
			}
		} else {
			model.addAttribute("content","");									
		}
		return "webview/advert";
	}
	
	/**
	 * 
	 * @Title: 获取关于我们
	 * @Description: 获取注册协议
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "webview/about")
	public String getAbout(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
		model.addAttribute("content", getServiceInfo("0"));
		return "webview/about";
	}

	/**
	 * 
	 * @Title: 获取注册协议
	 * @Description: 获取注册协议
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "webview/agreement")
	public String getAgreement(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
		model.addAttribute("content", getServiceInfo("1"));
		return "webview/agreement";
	}
	
	/**
	 * 
	 * @Title: 获取使用帮助
	 * @Description: 获取使用帮助
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "webview/help")
	public String getHelp(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
		model.addAttribute("content", getServiceInfo("2"));
		return "webview/help";
	}

	/**
	 * 
	 * @Title: 获取服务条款
	 * @Description: 获取服务条款
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "webview/serviceInfo")
	public String getServiceInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
		model.addAttribute("content", getServiceInfo("3"));
		return "webview/service";
	}
	
	/**
	 * 
	 * @Title: 获取注册协议
	 * @Description: 获取注册协议
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "webview/shareInfo")
	public String getShareInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
		model.addAttribute("content", getServiceInfo("4"));
		return "webview/share";
	}
	
	/**
	 * 
	 * @Title: 场地详情
	 * @Description: 场地详情
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "webview/getSiteDetail")
	public String getSiteDetail(HttpServletRequest request, HttpServletResponse response, String id, 
			@RequestParam(defaultValue="1") Integer page, @RequestParam(defaultValue="15")Integer pageSize, ModelMap model) {
		if(StringUtils.isBlank(id)) {
			model.put("msg", "场地id没有输入！");
			return "webview/error";
		}
		
		Map<String, Object> ret = new HashMap<String, Object>();

		SiteList siteList = siteListService.selectById(id);
		if(siteList == null) {
			model.put("msg", "场地不存在！");
			return "webview/error";		
		}
		
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

		// 加载评论
		SiteInfo condition = new SiteInfo();
		condition.setSiteId(siteList.getId());
		condition.setStatus(0); // 评论状态：0正常；1已删除；
		condition.setSort("createTime");
	
		PageInfo<SiteInfo> pageInfo = new PageInfo<SiteInfo>();
		
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);
		siteInfoService.selectAll(condition, pageInfo);
		ret.put("commentTotal", pageInfo.getTotal());
		
		List<Map<String, Object>> comments = new ArrayList<Map<String, Object>>();
		
		for(SiteInfo item : pageInfo.getRows()) {
			Map<String, Object> siteInfoMap = new HashMap<String, Object>();
			siteInfoMap.put("id", StringUtils.stripToEmpty(item.getId()));
			siteInfoMap.put("creater", StringUtils.stripToEmpty(item.getCreater()));
			siteInfoMap.put("content", StringUtils.stripToEmpty(item.getContent()));
			siteInfoMap.put("siteId", StringUtils.stripToEmpty(item.getSiteId()));
			siteInfoMap.put("createTime", StringUtils.stripToEmpty(DateUtils.getDateFormat(item.getCreateTime())));
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
		
		model.addAttribute("siteInfo", ret);
		
		if(siteList.getType() != null && "0".equals(siteList.getType())) {
			return "webview/site_detail";
		}else {
			return "webview/recommend_site_detail";
		}
	}
	
	/**
	 * 
	 * @Title: 获取场地评论
	 * @Description: 获取场地评论
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "webview/getSiteComments")
	public String getSiteComments(HttpServletRequest request, HttpServletResponse response, String id, Integer page, Integer pageSize, ModelMap model) {
		if(StringUtils.isBlank(id)) {
			model.put("msg", "场地id没有输入！");
			return "webview/error";
		}
		if(page == null) {
			model.put("msg", "场地page没有输入！");
			return "webview/error";
		}
		if(pageSize == null) {
			model.put("msg", "场地pageSize没有输入！");
			return "webview/error";
		}
		
		Map<String, Object> ret = new HashMap<String, Object>();

		SiteList siteList = siteListService.selectById(id);
		if(siteList == null) {
			model.put("msg", "场地不存在！");
			return "webview/error";		
		}
		
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

		// 加载评论
		SiteInfo condition = new SiteInfo();
		condition.setSiteId(siteList.getId());
		condition.setStatus(0); // 评论状态：0正常；1已删除；
		condition.setSort("createTime");
	
		PageInfo<SiteInfo> pageInfo = new PageInfo<SiteInfo>();
		
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);
		siteInfoService.selectAll(condition, pageInfo);
		ret.put("commentTotal", pageInfo.getTotal());
		
		List<Map<String, Object>> comments = new ArrayList<Map<String, Object>>();
		
		for(SiteInfo item : pageInfo.getRows()) {
			Map<String, Object> siteInfoMap = new HashMap<String, Object>();
			siteInfoMap.put("id", StringUtils.stripToEmpty(item.getId()));
			siteInfoMap.put("creater", StringUtils.stripToEmpty(item.getCreater()));
			siteInfoMap.put("content", StringUtils.stripToEmpty(item.getContent()));
			siteInfoMap.put("siteId", StringUtils.stripToEmpty(item.getSiteId()));
			siteInfoMap.put("createTime", StringUtils.stripToEmpty(DateUtils.getDateFormat(item.getCreateTime())));
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

			ret.put("imgUrls", imgUrls);
		}
		
		model.addAttribute("siteInfo", ret);
		
		return "webview/site_comments";
	}
	
	/**
	 * 
	 * @Title: 卡片详情
	 * @Description: 卡片详情
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "webview/getCardDetail")
	public String getCardDetail(HttpServletRequest request, HttpServletResponse response, String id, ModelMap model) {
		if(StringUtils.isBlank(id)) {
			model.put("msg", "卡片id没有输入！");
			return "webview/error";
		}
		
		// 查询卡片详情
		CardInfo cardInfo = cardInfoService.selectById(id);
		if(cardInfo == null || cardInfo.getStatus() == 1) {
			model.put("msg", "卡片id不存在！");
			return "webview/error";
		}

		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> cardInfoMap = new HashMap<String, Object>();
		Map<String, Object> actorInfoMap = new HashMap<String, Object>();
		ret.put("cardInfo", cardInfoMap);
		ret.put("actorInfo", actorInfoMap);
		
		cardInfoMap.put("id",StringUtils.stripToEmpty(cardInfo.getId()));
		cardInfoMap.put("type",cardInfo.getType());
		cardInfoMap.put("cardName",StringUtils.stripToEmpty(cardInfo.getCardName()));
		if(cardInfo.getSex() == null) {
			cardInfoMap.put("sex", 0);
		}else {
			cardInfoMap.put("sex",cardInfo.getSex());
		}
		cardInfoMap.put("cardRoleId",StringUtils.stripToEmpty(cardInfo.getCardRoleId()));
		cardInfoMap.put("detailRole",StringUtils.stripToEmpty(cardInfo.getDetailRole()));
		cardInfoMap.put("publicType",StringUtils.stripToEmpty(cardInfo.getPublicType()));
		cardInfoMap.put("publicTypeName",StringUtils.stripToEmpty(cardInfo.getPublicTypeNames()));
		if(cardInfo.getBirthDate() == null) {
			cardInfoMap.put("birthDate","");	
		}else {
			cardInfoMap.put("birthDate",DateUtils.getDateFormat(cardInfo.getBirthDate()));		
		}
		cardInfoMap.put("city",StringUtils.stripToEmpty(cardInfo.getCity()));
		cardInfoMap.put("actCities",StringUtils.replaceChars(StringUtils.stripToEmpty(cardInfo.getActCities()), ',', '、'));
		if(cardInfo.getPrice() == null) {
			cardInfoMap.put("price","");	
		}else {
			cardInfoMap.put("price",cardInfo.getPrice());
		}
		cardInfoMap.put("height",StringUtils.stripToEmpty(cardInfo.getHeight()));
		cardInfoMap.put("weight",StringUtils.stripToEmpty(cardInfo.getWeight()));
		cardInfoMap.put("shoesSize",StringUtils.stripToEmpty(cardInfo.getShoesSize()));
		cardInfoMap.put("size",StringUtils.stripToEmpty(cardInfo.getSize()));
		cardInfoMap.put("institution",StringUtils.stripToEmpty(cardInfo.getInstitution()));
		cardInfoMap.put("details",StringUtils.stripToEmpty(cardInfo.getDetails()));
		cardInfoMap.put("imgUuid",StringUtils.stripToEmpty(cardInfo.getImgUuid()));
		cardInfoMap.put("mutiImgUuid",StringUtils.stripToEmpty(cardInfo.getMutiImgUuid()));
		cardInfoMap.put("creater",StringUtils.stripToEmpty(cardInfo.getCreater()));
		cardInfoMap.put("createTime",DateUtils.getDateTimeMinFormat(cardInfo.getCreateTime()));
		cardInfoMap.put("status",cardInfo.getStatus());
		cardInfoMap.put("createrName",StringUtils.stripToEmpty(cardInfo.getCreaterName()));
		cardInfoMap.put("cardRoleName",StringUtils.stripToEmpty(cardInfo.getCardRoleName()));	

		if(StringUtils.isNotBlank(cardInfo.getCardImgUrls())) {

			ArrayList<String> urlList = new ArrayList<String> ();
			for(String url : StringUtils.split(cardInfo.getCardImgUrls(), ',')) {
				urlList.add(Configurations.buildDownloadUrl(url));
			}
			Collections.sort(urlList);
			cardInfoMap.put("cardImgUrls",urlList.toArray());	
		}

		// 查询卡片创建者信息
		ActorInfo actorInfo = actorInfoService.selectById(cardInfo.getCreater());
		if(actorInfo != null){
			actorInfoMap.put("name", StringUtils.stripToEmpty(actorInfo.getName()));
			if(actorInfo.getHeadImgUrl() != null) {
				actorInfoMap.put("headImgUrl", Configurations.buildDownloadUrl(actorInfo.getHeadImgUrl()));			
			}
			actorInfoMap.put("name", StringUtils.stripToEmpty(actorInfo.getName()));
			actorInfoMap.put("authenticateLevel", actorInfo.getAuthenticateLevel());
			actorInfoMap.put("roleName",StringUtils.split(actorInfo.getRoleName(), ','));

		}
		
		ActorComment commentReq = new ActorComment();
		commentReq.setActorId(cardInfo.getCreater());
		
		float avgScore = Math.max(actorCommentService.getAvgScore(commentReq), 1.0f); // 默认最少3分
		actorInfoMap.put("avgScore", formatter.format(avgScore));
		actorInfoMap.put("starCount", (int)Math.floor(avgScore));
		
		model.addAttribute("cardDetail", ret);
		
		switch(cardInfo.getType()){ //1艺人；2租借；3策划/创意；4婚礼/派对
		case 1:
			return "webview/card_detail_host";
		case 2:
			return "webview/card_detail_device";
		case 3:
			return "webview/card_detail_plan";
		case 4:
			return "webview/card_detail_wedding";
		default:
			model.put("msg", "卡片类型不存在！");
			return "webview/error";
		}
	}
	
	/**
	 * 
	 * @Title: 获取他的信息
	 * @Description: 获取他的信息
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	private Map<String, Object> getHisInfo(String id) {

		Map<String, Object> ret = new HashMap<String, Object>();

		ActorInfo actorInfo = actorInfoService.selectById(id);
		
		if(actorInfo == null || actorInfo.getStatus() == 1) {
			ret.put("city","");
			ret.put("actCities","");
			ret.put("height","");
			ret.put("weight","");
			ret.put("shoesSize","");
			ret.put("size","");
			ret.put("createTime","");
			ret.put("authenticateLevel",0);
			ret.put("roleName",StringUtils.split("", ','));
			ret.put("headImgUrl","");
			ret.put("introduction","");

			return ret;
		}


		String name = StringUtils.stripToEmpty(actorInfo.getName());
		String nameFmt = null;
		if(name.length() == 2) {// 名字字符为2个插入html空格
			StringBuffer buffer = new StringBuffer();
			buffer.append(name.charAt(0)).append("&nbsp;").append(name.charAt(1));
			nameFmt = buffer.toString();
		}else {
			nameFmt = name;
		}
		ret.put("id",StringUtils.stripToEmpty(actorInfo.getId()));
		ret.put("name", name);
		ret.put("nameFmt", nameFmt);
		ret.put("sex",actorInfo.getSex() == null ? 1 : actorInfo.getSex());
		
		if(actorInfo.getBirthDay() == null) {
			ret.put("birthDate","");	
		}else {
			ret.put("birthDate",DateUtils.getDateFormat(actorInfo.getBirthDay()));		
		}

		ret.put("city",StringUtils.stripToEmpty(actorInfo.getCity()));
		ret.put("actCities",StringUtils.stripToEmpty(actorInfo.getActCities()));
		ret.put("height",StringUtils.stripToEmpty(actorInfo.getHeight()));
		ret.put("weight",StringUtils.stripToEmpty(actorInfo.getWeight()));
		ret.put("shoesSize",StringUtils.stripToEmpty(actorInfo.getShoesSize()));
		ret.put("size",StringUtils.stripToEmpty(actorInfo.getSize()));
		ret.put("createTime",DateUtils.getDateTimeMinFormat(actorInfo.getCreateTime()));
		ret.put("authenticateLevel",actorInfo.getAuthenticateLevel() == null ? 0 : actorInfo.getAuthenticateLevel());
		ret.put("roleName",StringUtils.split(actorInfo.getRoleName(), ','));
		ret.put("introduction",StringUtils.stripToEmpty(actorInfo.getIntroduction()));
		
		if(actorInfo.getHeadImgUrl() != null) {
			ret.put("headImgUrl", Configurations.buildDownloadUrl(actorInfo.getHeadImgUrl()));			
		}else {
			ret.put("headImgUrl","");
		}
		
		return ret;
	}
	
	/**
	 * 
	 * @Title: 获取他的信息
	 * @Description: 获取他的信息
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "webview/getHisInfo")
	public String getHisInfo(HttpServletRequest request, HttpServletResponse response, String id, String actorId, ModelMap model) {
		if(StringUtils.isBlank(id)) {
			model.put("msg", "用户id没有输入！");
			return "webview/error";
		}
		
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> hisInfo = this.getHisInfo(id);
		Map<String, Object> hisCards = this.getHisCards(id, 1, 5);
		Map<String, Object> hisShow = this.getHisShow(id, 1, 5);
		
		ret.put("info", hisInfo);
		ret.put("cardInfo", hisCards);
		ret.put("showInfo", hisShow);
		ret.put("reqId", id);
		
		model.addAttribute("hisInfo", ret);
		
		if(StringUtils.equals(id, actorId)) {
			return "webview/my_info";
		}else {
			return "webview/his_info";
		}

	}
	
	/**
	 * 
	 * @Title: 获取他的卡片
	 * @Description: 获取他的卡片
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	private Map<String, Object> getHisCards(String id, Integer page, Integer pageSize) {
		
		PageInfo<CardInfo> pageInfo = new PageInfo<CardInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);
		
		CardInfo condition = new CardInfo();
		condition.setStatus(0); // 状态 0正常；1已删除；
		condition.setCreater(id);
		condition.setSort("createTime");
		
		cardInfoService.selectAll(condition, pageInfo, "selectAllSummary");
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		result.put("rows", dataList);
		result.put("page", pageInfo.getPage());
		result.put("pageSize", pageInfo.getPageSize());
		result.put("total", pageInfo.getTotal());
		result.put("pages", pageInfo.getPages());

		if(pageInfo.getRows() != null && !pageInfo.getRows().isEmpty())
		{
			for(CardInfo cardInfo : pageInfo.getRows())
			{
				Map<String, Object> cardMap = new HashMap<String, Object>();
				
				cardMap.put("id", StringUtils.stripToEmpty(cardInfo.getId()));
				cardMap.put("type", cardInfo.getType());
				cardMap.put("cardName", StringUtils.stripToEmpty(cardInfo.getCardName()));
				cardMap.put("sex", cardInfo.getSex() == null ? 0 : cardInfo.getSex());
				cardMap.put("cardRoleName", StringUtils.stripToEmpty(cardInfo.getCardRoleName()));
				cardMap.put("detailRole", StringUtils.stripToEmpty(cardInfo.getDetailRole()));
				cardMap.put("publicType", StringUtils.stripToEmpty(cardInfo.getPublicTypeNames())); // 发布类型名字
				
				if(cardInfo.getBirthDate() == null) {
					cardMap.put("birthDate","");	
				}else {
					cardMap.put("birthDate",DateUtils.getDateFormat(cardInfo.getBirthDate()));		
				}
			
				cardMap.put("city", StringUtils.stripToEmpty(cardInfo.getCity()));
				cardMap.put("actCities", StringUtils.stripToEmpty(cardInfo.getActCities()));
				
				if(cardInfo.getPrice() == null) {
					cardMap.put("price","");	
				}else {
					cardMap.put("price",cardInfo.getPrice());
				}
				
				cardMap.put("height", StringUtils.stripToEmpty(cardInfo.getHeight()));
				cardMap.put("weight", StringUtils.stripToEmpty(cardInfo.getWeight()));
				cardMap.put("shoesSize", StringUtils.stripToEmpty(cardInfo.getShoesSize()));
				cardMap.put("size", StringUtils.stripToEmpty(cardInfo.getSize()));
				cardMap.put("institution", StringUtils.stripToEmpty(cardInfo.getInstitution()));
				cardMap.put("details", StringUtils.stripToEmpty(cardInfo.getDetails()));
				cardMap.put("creater", StringUtils.stripToEmpty(cardInfo.getCreater()));
				cardMap.put("createrName", StringUtils.stripToEmpty(cardInfo.getCreaterName()));
				cardMap.put("createTime", StringUtils.stripToEmpty(DateUtils.getDateTimeMinFormat(cardInfo.getCreateTime())));
				cardMap.put("authenticateLevel", cardInfo.getAuthenticateLevel() == null ? 0 : cardInfo.getAuthenticateLevel());
				
				ActorComment commentReq = new ActorComment();
				commentReq.setActorId(cardInfo.getCreater());
				float avgScore = Math.max(actorCommentService.getAvgScore(commentReq), 1.0f); // 默认最少3分

				cardMap.put("avgScore", formatter.format(avgScore));
				
				String[] urls = StringUtils.split(cardInfo.getCardImgUrls(), ",");
				List<String> imgUrls = new ArrayList<String>();
				if(urls != null) {
					for(String url : urls) {
						imgUrls.add(Configurations.buildDownloadUrl(url));
					}
				}
				cardMap.put("cardImgUrls", imgUrls);
				// 第一张图片单独取出
				if(imgUrls.size() > 0) {
					cardMap.put("firstImgUrl", imgUrls.get(0));
				}
				
				dataList.add(cardMap);
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @Title: 获取他的卡片
	 * @Description: 获取他的卡片
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "webview/getHisCards")
	public void getHisCards(HttpServletRequest request, HttpServletResponse response, String id, Integer page, Integer pageSize, ModelMap model) {
		if(StringUtils.isBlank(id)) {		
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少id参数！", null);
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

		ActorInfo actorInfo = actorInfoService.selectById(id);
		if(actorInfo == null || actorInfo.getStatus() == 1) {
			writeJsonObject(response, AppRetCode.ERROR, "id错误，用户不存在！", null);
			return;
		}

		Map<String, Object> result = this.getHisCards(id, page, pageSize);
		
		writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, result);
	}
	

	/**
	 * 
	 * @Title: 获取他的秀场
	 * @Description: 获取他的秀场
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "webview/getHisShow")
	public void getHisShow(HttpServletRequest request, HttpServletResponse response, String id, Integer page, Integer pageSize) {
		if(StringUtils.isBlank(id)) {		
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少id参数！", null);
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

		ActorInfo actorInfo = actorInfoService.selectById(id);
		if(actorInfo == null || actorInfo.getStatus() == 1) {
			writeJsonObject(response, AppRetCode.ERROR, "id错误，用户不存在！", null);
			return;
		}

		Map<String, Object> result = getHisShow(id, page, pageSize);
		
		writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, result);

	}

	/**
	 * 
	 * @Title: 获取他的秀场
	 * @Description: 获取他的秀场
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	private Map<String, Object> getHisShow(String id, Integer page, Integer pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();

		PageInfo<ShowInfo> pageInfo = new PageInfo<ShowInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);
		
		ShowInfo condition = new ShowInfo();
		condition.setStatus("0"); // 状态 0正常；1已删除；
		condition.setCreater(id);
		condition.setSort("createTime");
		
		showInfoService.selectAll(condition, pageInfo, "selectAllSummary");
		
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		result.put("rows", dataList);
		result.put("page", pageInfo.getPage());
		result.put("pageSize", pageInfo.getPageSize());
		result.put("total", pageInfo.getTotal());
		result.put("pages", pageInfo.getPages());
		
		if(pageInfo.getRows() != null && !pageInfo.getRows().isEmpty())
		{
			for(ShowInfo showInfo : pageInfo.getRows())
			{
				Map<String, Object> showInfoMap = new HashMap<String, Object>();
				
				showInfoMap.put("id",StringUtils.stripToEmpty(showInfo.getId()));
				showInfoMap.put("content",StringUtils.stripToEmpty(showInfo.getContent()));
				showInfoMap.put("publicType",StringUtils.stripToEmpty(showInfo.getPublicType()));
				showInfoMap.put("city",StringUtils.stripToEmpty(showInfo.getCity()));
				showInfoMap.put("commentNum",showInfo.getCommentNum() == null ? 0 : showInfo.getCommentNum());
				showInfoMap.put("praiseNum",showInfo.getPraiseNum() == null ? 0 : showInfo.getPraiseNum());
				showInfoMap.put("shareNum",showInfo.getShareNum() == null ? 0 : showInfo.getShareNum());
				showInfoMap.put("creater",StringUtils.stripToEmpty(showInfo.getCreater()));
				showInfoMap.put("createTime",DateUtils.getDateTimeMinFormat(showInfo.getCreateTime()));
				showInfoMap.put("type",StringUtils.stripToEmpty(showInfo.getType()));
				showInfoMap.put("videoPreviewUrl",StringUtils.isBlank(showInfo.getVideoPreviewUrl()) ? "" : Configurations.buildDownloadUrl(showInfo.getVideoPreviewUrl()));
				
				List<String> urls = new ArrayList<String>();
				if("0".equals(showInfo.getType())) { // 图片类型
					for(String url : StringUtils.split(StringUtils.stripToEmpty(showInfo.getImgUrls()), ',')) {
						urls.add(Configurations.buildDownloadUrl(url));
					}
				}else { // 视频类型
					if(StringUtils.isNotBlank(showInfo.getVideoUrl())) {
						urls.add(Configurations.buildDownloadUrl(showInfo.getVideoUrl()));
					}
				}
				showInfoMap.put("resUrls", urls);

				dataList.add(showInfoMap);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @Title: getHostAnnouncementDetail
	 * @Description: 获取通告详情
	 * @return JSON
	 * @author CrazyT
	 * 
	 */	
	@RequestMapping(value = "webview/getAnnouncementDetail")
	public String getAnnouncementDetail(HttpServletRequest request, HttpServletResponse response, String id, ModelMap model) {
		if(StringUtils.isBlank(id)) {
			model.put("msg", "缺少通告id参数");
			return "webview/error";
		}
		
		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("id", id);
		condition.put("status", 0);

		// 查询通告信息
		AnnounceInfo announceInfo = announceInfoService.getAnnouncementDetails(condition);
		if(announceInfo == null)
		{
			model.put("msg", "通告详情不存在，请检查参数id");
			return "webview/error";
		}
		
		Map<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("id", StringUtils.trimToEmpty(announceInfo.getId()));
		ret.put("title", StringUtils.trimToEmpty(announceInfo.getTitle()));
		ret.put("type",  announceInfo.getType());
		ret.put("name",  StringUtils.trimToEmpty(announceInfo.getName())); // 艺人类型；需要装备；策划项目；定制内容；
		ret.put("sex",  announceInfo.getSex() == null ? 1 : announceInfo.getSex());
		ret.put("price",  announceInfo.getPrice());
		ret.put("publicType", StringUtils.trimToEmpty(announceInfo.getPublicTypeNames()));					
		ret.put("city",  StringUtils.trimToEmpty(announceInfo.getCity()));
		ret.put("showTime", StringUtils.trimToEmpty(DateUtils.getDateFormat(announceInfo.getShowTime())));
		ret.put("creater",  StringUtils.trimToEmpty(announceInfo.getCreater()));
		ret.put("createTime", StringUtils.trimToEmpty(DateUtils.getDateTimeMinFormat(announceInfo.getCreateTime())));
		ret.put("entranceTime", StringUtils.trimToEmpty(DateUtils.getDateTimeMinFormat(announceInfo.getEntranceTime())));
		ret.put("address",  StringUtils.trimToEmpty(announceInfo.getAddress()));
		ret.put("detail",  StringUtils.trimToEmpty(announceInfo.getDetail()));
		ret.put("enrollStatus", announceInfo.getEnrollStatus());
		if(StringUtils.isNotBlank(announceInfo.getHeadImgUrl())) {
			ret.put("headImgUrl", Configurations.buildDownloadUrl(announceInfo.getHeadImgUrl()));
		}else {
			ret.put("headImgUrl", "");
		}
		ret.put("createName", StringUtils.trimToEmpty(announceInfo.getCreaterName()));
		ret.put("authenticateLevel", announceInfo.getAuthenticateLevel() == null ? 0 : announceInfo.getAuthenticateLevel());
		ret.put("note", StringUtils.trimToEmpty(announceInfo.getNote()));
		
		// 查询创建者分数
		ActorComment commentReq = new ActorComment();
		commentReq.setActorId(announceInfo.getCreater());
		commentReq.setType(0); // 查询“发通告人”的平均分数
		float avgScore = Math.max(actorCommentService.getAvgScore(commentReq), 1.0f); // 默认最少3分

		ret.put("score", formatter.format(avgScore));
		
		ActorInfo actorInfo = actorInfoService.selectById(announceInfo.getCreater());
		if(actorInfo != null) {
			ret.put("roleName", StringUtils.split(actorInfo.getRoleName(), ','));
			ret.put("actorName", StringUtils.trimToEmpty(actorInfo.getName()));
		}else {
			ret.put("roleName", StringUtils.split("", ','));
			ret.put("actorName", "");
		}
		
		List<Map<String, Object>> applyList = new ArrayList<Map<String, Object>>();
			
		if(announceInfo.getEnrollInfos() != null)
		{
			for(AnnouceEnroll annouceEnroll : announceInfo.getEnrollInfos())
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
				
				applyList.add(actorMap);
			}
			
			ret.put("enrollActors", applyList);
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
		
		ret.put("imgUrls", imgUrls);
		
		// 更新浏览量数据
		AnnounceInfo updateCondition = new AnnounceInfo();
		updateCondition.setId(announceInfo.getId());
		updateCondition.setReadCount(announceInfo.getReadCount() + 1);
		
		announceInfoService.update(updateCondition);

		model.addAttribute("announceInfo", ret);
		
//		this.writeJsonObject(response, 0, "", ret);
		switch(announceInfo.getType()){ //1艺人；2租借；3策划/创意；4婚礼/派对
		case 1:
			return "webview/announce_share_host";
		case 2:
			return "webview/announce_share_device";
		case 3:
			return "webview/announce_share_plan";
		case 4:
			return "webview/announce_share_party";
		default:
			model.put("msg", "卡片类型不存在！");
			return "webview/error";
		}
	}
}
