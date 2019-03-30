package com.yyhz.sc.controller.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import com.yyhz.sc.data.model.CardInfo;
import com.yyhz.sc.data.model.SystemPictureInfo;
import com.yyhz.sc.services.ActorCollectService;
import com.yyhz.sc.services.ActorCommentService;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.CardInfoService;
import com.yyhz.utils.DateFormatUtil;
import com.yyhz.utils.DateUtils;
import com.yyhz.utils.stream.config.Configurations;

@Controller
@RequestMapping(value = "api")
public class AppCardController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(AppCardController.class);

	
	@Resource
	private CardInfoService cardInfoService;
	@Resource
	private ActorInfoService actorInfoService;
	@Resource
	private ActorCommentService actorCommentService;
	@Resource
	private ActorCollectService actorCollectService;

	/**
	 * 
	 * @Title: addHostCard
	 * @Description: 添加主持卡片
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "addHostCard")
	public void addHostCard(HttpServletRequest request, HttpServletResponse response, CardInfo reqInfo,
			@RequestParam(required = false) MultipartFile[] imageFiles, String publicTypeIds) {
		
		
		if(reqInfo == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少卡片相关参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCardName())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少name参数", null);
			return;
		}

//		if(StringUtils.isBlank(reqInfo.getCardRoleId())) {
//			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少cardRoleId参数", null);
//			return;
//		}
		
		if(reqInfo.getSex() == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少sex参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少creater参数", null);
			return;
		}	
		
		if(StringUtils.isNoneBlank(reqInfo.getBirthDateStr())){
			reqInfo.setBirthDate(DateUtils.formatDate(DateFormatUtil.FormatDate(reqInfo.getBirthDateStr()), DateUtils.DATE_DEFAULT_FORMAT));
		}
		
		reqInfo.setType(1); // 1艺人；2租借；3策划/创意；4婚礼/派对
		
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

		int ret = cardInfoService.addCard(reqInfo, picUuidList.toArray(new String[0]), StringUtils.split(publicTypeIds,','));

		if(ret > 0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, reqInfo);
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "添加卡片失败！", null);
		}	
	}
	
	/**
	 * 
	 * @Title: addDeviceCard
	 * @Description: 添加设备卡片
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "addDeviceCard")
	public void addDeviceCard(HttpServletRequest request, HttpServletResponse response, CardInfo reqInfo,
			@RequestParam(required = false) MultipartFile[] imageFiles, String publicTypeIds) {
		if(reqInfo == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少卡片相关参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCardName())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少name参数", null);
			return;
		}

		if(StringUtils.isBlank(reqInfo.getCity())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少city参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getActCities())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少actCities参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少creater参数", null);
			return;
		}	

		reqInfo.setType(2); // 1艺人；2租借；3策划/创意；4婚礼/派对
		
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

		int ret = cardInfoService.addCard(reqInfo, picUuidList.toArray(new String[0]), StringUtils.split(publicTypeIds,','));

		if(ret > 0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, reqInfo);
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "添加卡片失败！", null);
		}	
	}
	
	/**
	 * 
	 * @Title: addPlanCard
	 * @Description: 添加策划卡片
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "addPlanCard")
	public void addPlanCard(HttpServletRequest request, HttpServletResponse response, CardInfo reqInfo,
			@RequestParam(required = false) MultipartFile[] imageFiles, String publicTypeIds) {
		if(reqInfo == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少卡片相关参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCardName())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少name参数", null);
			return;
		}

		if(StringUtils.isBlank(reqInfo.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少creater参数", null);
			return;
		}	
		
		reqInfo.setType(3); // 1艺人；2租借；3策划/创意；4婚礼/派对
		
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

		int ret = cardInfoService.addCard(reqInfo, picUuidList.toArray(new String[0]), StringUtils.split(publicTypeIds,','));

		if(ret > 0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, reqInfo);
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "添加卡片失败！", null);
		}	
	}

	/**
	 * 
	 * @Title: addPlanCard
	 * @Description: 添加策划卡片
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "addWeddingCard")
	public void addWeddingCard(HttpServletRequest request, HttpServletResponse response, CardInfo reqInfo,
			@RequestParam(required = false) MultipartFile[] imageFiles, String publicTypeIds) {
		if(reqInfo == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少卡片相关参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCardName())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少name参数", null);
			return;
		}

		if(StringUtils.isBlank(reqInfo.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少creater参数", null);
			return;
		}	
		
		reqInfo.setType(4); // 1艺人；2租借；3策划/创意；4婚礼/派对
		
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

		int ret = cardInfoService.addCard(reqInfo, picUuidList.toArray(new String[0]), StringUtils.split(publicTypeIds,','));

		if(ret > 0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, reqInfo);
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "添加卡片失败！", null);
		}	
	}
	
	/**
	 * 
	 * @Title: getCardList
	 * @Description: 获取卡片列表
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getCardList")
	public void getCardList(HttpServletRequest request, HttpServletResponse response, 
			CardInfo req ,Integer page, Integer pageSize, Integer sortKey,String actorId) {
		
		if (page == null) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少page参数！", null);
			return;
		}
		
		if (pageSize == null) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少pageSize参数！", null);
			return;
		}

		PageInfo<CardInfo> pageInfo = new PageInfo<CardInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);
		
		CardInfo condition = new CardInfo();
		condition.setStatus(0); // 状态 0正常；1已删除；
		
		if(req.getType() != null && req.getType() > 0) // 1艺人；2租借；3策划/创意；4婚礼/派对;其他查询全部结果
		{
			condition.setType(req.getType());
		}
		
		if(StringUtils.isNotBlank(req.getCity())) { // 城市条件用来筛选活动城市
			condition.setActCities((req.getCity()));
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
		condition.setActorId(actorId);//当前谁在看，用于黑名单
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

		cardInfoService.selectAll(condition, pageInfo, "selectAllSummary");
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		result.put("rows", dataList);
		result.put("page", pageInfo.getPage());
		result.put("pageSize", pageInfo.getPageSize());
		result.put("total", pageInfo.getTotal());
		
		if(pageInfo.getRows() != null && !pageInfo.getRows().isEmpty())
		{
			for(CardInfo cardInfo : pageInfo.getRows())
			{
				Map<String, Object> cardMap = new HashMap<String, Object>();
				
				cardMap.put("id", cardInfo.getId());
				cardMap.put("type", cardInfo.getType());
				cardMap.put("cardName", cardInfo.getCardName());
				cardMap.put("sex", cardInfo.getSex());
				cardMap.put("cardRoleName", cardInfo.getCardRoleName());
				cardMap.put("detailRole", cardInfo.getDetailRole());
				cardMap.put("publicType", cardInfo.getPublicTypeNames()); // 发布类型名字
				cardMap.put("birthDate", DateUtils.getDateFormat(cardInfo.getBirthDate()));
				cardMap.put("city", cardInfo.getCity());
				cardMap.put("actCities", cardInfo.getActCities());
				cardMap.put("price", cardInfo.getPrice());
				cardMap.put("height", cardInfo.getHeight());
				cardMap.put("weight", cardInfo.getWeight());
				cardMap.put("shoesSize", cardInfo.getShoesSize());
				cardMap.put("size", cardInfo.getSize());
				cardMap.put("institution", cardInfo.getInstitution());
				cardMap.put("details", cardInfo.getDetails());
				cardMap.put("creater", cardInfo.getCreater());
				cardMap.put("createrName", cardInfo.getCreaterName());
				cardMap.put("createTime", DateUtils.getDateTimeMinFormat(cardInfo.getCreateTime()));
				cardMap.put("authenticateLevel", cardInfo.getAuthenticateLevel());
//				cardMap.put("avgScore", actorCommentService.getAvgScore(cardInfo.getCreater()));
				if(cardInfo.getAvgScore() == null) {
					cardMap.put("avgScore", new Integer(0));			
				}else {
					cardMap.put("avgScore", cardInfo.getAvgScore());
				}
				
				cardMap.put("createrHeadUrl", StringUtils.isBlank(cardInfo.getCreaterHeadUrl()) ? "" : Configurations.buildDownloadUrl(cardInfo.getCreaterHeadUrl()));
				
				String[] urls = StringUtils.split(cardInfo.getCardImgUrls(), ",");
				List<String> imgUrls = new ArrayList<String>();
				if(urls != null) {
					Arrays.sort(urls);
					for(String url : urls) {
						imgUrls.add(Configurations.buildDownloadUrl(url));
					}
				}

				cardMap.put("cardImgUrls", imgUrls);
	
				dataList.add(cardMap);
			}
		}
		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, result);
	}
	
	/**
	 * 
	 * @Title: searchCardtList
	 * @Description: 检索卡片列表
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	public void searchCard(HttpServletRequest request, HttpServletResponse response, 
			String keyword ,Integer page, Integer pageSize,String actorId) {
		
		if (StringUtils.isBlank(keyword)) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数keyWord错误！", null);
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

		PageInfo<CardInfo> pageInfo = new PageInfo<CardInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);
		
		CardInfo condition = new CardInfo();
		
		// 1艺人；2租借；3策划/创意；4婚礼/派对;其他查询全部结果
		if(StringUtils.contains("艺人", keyword)) {
			condition.setType(1);
		}
		if(StringUtils.contains("租借", keyword)) {
			condition.setType(2);
		}

		if(StringUtils.contains("策划/创意", keyword)) {
			condition.setType(3);
		}

		if(StringUtils.contains("婚礼/派对", keyword)) {
			condition.setType(4);
		}
		condition.setActorId(actorId);//当前谁在看，用于黑名单
		condition.setDetailRole(keyword);
		
		condition.setStatus(0); // 状态 0正常；1已删除；

		cardInfoService.selectAll(condition, pageInfo, "searchAllSummary");
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		result.put("rows", dataList);
		result.put("page", pageInfo.getPage());
		result.put("pageSize", pageInfo.getPageSize());
		result.put("total", pageInfo.getTotal());

		
		if(pageInfo.getRows() != null && !pageInfo.getRows().isEmpty())
		{
			for(CardInfo cardInfo : pageInfo.getRows())
			{
				Map<String, Object> cardMap = new HashMap<String, Object>();
				
				cardMap.put("id", cardInfo.getId());
				cardMap.put("type", cardInfo.getType());
				cardMap.put("cardName", cardInfo.getCardName());
				cardMap.put("sex", cardInfo.getSex());
				cardMap.put("cardRoleName", cardInfo.getCardRoleName());
				cardMap.put("detailRole", cardInfo.getDetailRole());
				cardMap.put("publicTypes", StringUtils.split(cardInfo.getPublicType(), ","));
				cardMap.put("birthDate", DateUtils.getDateFormat(cardInfo.getBirthDate()));
				cardMap.put("city", cardInfo.getCity());
				cardMap.put("actCities", cardInfo.getActCities());
				cardMap.put("price", cardInfo.getPrice());
				cardMap.put("height", cardInfo.getHeight());
				cardMap.put("weight", cardInfo.getWeight());
				cardMap.put("shoesSize", cardInfo.getShoesSize());
				cardMap.put("size", cardInfo.getSize());
				cardMap.put("institution", cardInfo.getInstitution());
				cardMap.put("details", cardInfo.getDetails());
				cardMap.put("creater", cardInfo.getCreater());
				cardMap.put("createTime", DateUtils.getDateTimeMinFormat(cardInfo.getCreateTime()));
				
				String[] urls = StringUtils.split(cardInfo.getCardImgUrls(), ",");
				List<String> imgUrls = new ArrayList<String>();
				if(urls != null) {
					for(String url : urls) {
						imgUrls.add(Configurations.buildDownloadUrl(url));
					}
				}
				cardMap.put("cardImgUrls", imgUrls);
	
				dataList.add(cardMap);
			}
		}
		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, result);
	}
	
	/**
	 * 
	 * @Title: getAnttentionByCardId
	 * @Description: 检索关注信息
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getAnttentionByCardId")
	public void getAnttentionByCardId(HttpServletRequest request, HttpServletResponse response, String cardId, String actorId) {
		if (StringUtils.isBlank(cardId)) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数cardId错误！", null);
			return;
		}
		
		if (StringUtils.isBlank(actorId)) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数actorId错误！", null);
			return;
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		// 查询卡片
		CardInfo cardInfo = cardInfoService.selectById(cardId);
		if(cardInfo == null || cardInfo.getStatus() == 1) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "卡片不存在错误！", null);
			return;
		}
		
		// 查询关注信息
		ActorCollect condition = new ActorCollect();
		condition.setActorId(cardInfo.getCreater());
		condition.setCreater(actorId);
		ActorCollect collectInfo = actorCollectService.selectEntity(condition);
		
		result.put("creater", actorId);
		result.put("actorId", cardInfo.getCreater());
		if(collectInfo == null) {
			result.put("followState", 0);
		} else {
			result.put("followState", 1);
		}
		
		writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, result);
	
	}
	
	/**
	 * 
	 * @Title: getAnttentionById
	 * @Description: 检索关注信息
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getAnttentionById")
	public void getAnttentionById(HttpServletRequest request, HttpServletResponse response, String actorId, String creater) {
		if (StringUtils.isBlank(actorId)) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数actorId错误！", null);
			return;
		}
		
		if (StringUtils.isBlank(creater)) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数creater错误！", null);
			return;
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
				
		// 查询关注信息
		ActorCollect condition = new ActorCollect();
		condition.setActorId(actorId);
		condition.setCreater(creater);
		ActorCollect collectInfo = actorCollectService.selectEntity(condition);
		
		result.put("creater", creater);
		result.put("actorId", actorId);
		
		if(collectInfo == null) {
			result.put("followState", 0);
		} else {
			result.put("followState", 1);
		}
		
		writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, result);
	
	}
	
	/**
	 * 
	 * @Title: deleteCard
	 * @Description: 删除卡片
	 * @return JSON
	 * @author CrazyT
	 * 
	 */	
	@RequestMapping(value = "deleteCard")
	public void deleteCard(HttpServletRequest request, HttpServletResponse response, String id) {
		
		if(StringUtils.isBlank(id)) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少id参数", null);
			return;
		}

		CardInfo condition = new CardInfo();
		condition.setId(id);
		condition.setStatus(1); // 删除
		
		cardInfoService.update(condition);
		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, null);
	}
	/**
	 * 
	 * @Title: getTaCardList
	 * @Description: 获取Ta的卡片列表
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getTaCardList")
	public void getTaCardList(HttpServletRequest request, HttpServletResponse response, Integer page, Integer pageSize, String actorId) {
		
		if (page == null) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少page参数！", null);
			return;
		}
		
		if (pageSize == null) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少pageSize参数！", null);
			return;
		}
		
		if (StringUtils.isEmpty(actorId)) {
			writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少actorId参数！", null);
			return;
		}

		PageInfo<CardInfo> pageInfo = new PageInfo<CardInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(pageSize);
		
		CardInfo condition = new CardInfo();
		condition.setStatus(0); // 状态 0正常；1已删除；
		condition.setCreater(actorId);
		condition.setSort("createTime");
		

		cardInfoService.selectAll(condition, pageInfo, "selectAllSummary");
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		result.put("rows", dataList);
		result.put("page", pageInfo.getPage());
		result.put("pageSize", pageInfo.getPageSize());
		result.put("total", pageInfo.getTotal());
		
		if(pageInfo.getRows() != null && !pageInfo.getRows().isEmpty())
		{
			for(CardInfo cardInfo : pageInfo.getRows())
			{
				Map<String, Object> cardMap = new HashMap<String, Object>();
				
				cardMap.put("id", cardInfo.getId());
				cardMap.put("type", cardInfo.getType());
				cardMap.put("cardName", cardInfo.getCardName());
				cardMap.put("sex", cardInfo.getSex());
				cardMap.put("cardRoleName", cardInfo.getCardRoleName());
				cardMap.put("detailRole", cardInfo.getDetailRole());
				cardMap.put("publicType", cardInfo.getPublicTypeNames()); // 发布类型名字
				cardMap.put("birthDate", DateUtils.getDateFormat(cardInfo.getBirthDate()));
				cardMap.put("city", cardInfo.getCity());
				cardMap.put("actCities", cardInfo.getActCities());
				cardMap.put("price", cardInfo.getPrice());
				cardMap.put("height", cardInfo.getHeight());
				cardMap.put("weight", cardInfo.getWeight());
				cardMap.put("shoesSize", cardInfo.getShoesSize());
				cardMap.put("size", cardInfo.getSize());
				cardMap.put("institution", cardInfo.getInstitution());
				cardMap.put("details", cardInfo.getDetails());
				cardMap.put("creater", cardInfo.getCreater());
				cardMap.put("createrName", cardInfo.getCreaterName());
				cardMap.put("createTime", DateUtils.getDateTimeMinFormat(cardInfo.getCreateTime()));
				cardMap.put("authenticateLevel", cardInfo.getAuthenticateLevel());
//				cardMap.put("avgScore", actorCommentService.getAvgScore(cardInfo.getCreater()));
				if(cardInfo.getAvgScore() == null) {
					cardMap.put("avgScore", new Integer(0));			
				}else {
					cardMap.put("avgScore", cardInfo.getAvgScore());
				}
				
				cardMap.put("createrHeadUrl", StringUtils.isBlank(cardInfo.getCreaterHeadUrl()) ? "" : Configurations.buildDownloadUrl(cardInfo.getCreaterHeadUrl()));
				
				String[] urls = StringUtils.split(cardInfo.getCardImgUrls(), ",");
				List<String> imgUrls = new ArrayList<String>();
				if(urls != null) {
					Arrays.sort(urls);
					for(String url : urls) {
						imgUrls.add(Configurations.buildDownloadUrl(url));
					}
				}

				cardMap.put("cardImgUrls", imgUrls);
	
				dataList.add(cardMap);
			}
		}
		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, result);
	}
	/**
	 * 
	 * @Title: addJzCard
	 * @Description: 添加兼职卡片
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "addJzCard")
	public void addJzCard(HttpServletRequest request, HttpServletResponse response, CardInfo reqInfo,
			@RequestParam(required = false) MultipartFile[] imageFiles, String publicTypeIds) {
		
		
		if(reqInfo == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少卡片相关参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCardName())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少name参数", null);
			return;
		}
		
		if(reqInfo.getSex() == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少sex参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少creater参数", null);
			return;
		}	
		
		if(StringUtils.isNoneBlank(reqInfo.getBirthDateStr())){
			reqInfo.setBirthDate(DateUtils.formatDate(DateFormatUtil.FormatDate(reqInfo.getBirthDateStr()), DateUtils.DATE_DEFAULT_FORMAT));
		}
		
		reqInfo.setType(3); // 1艺人；2租借；3策划/创意；4婚礼/派对
		
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

		int ret = cardInfoService.addCard(reqInfo, picUuidList.toArray(new String[0]), StringUtils.split(publicTypeIds,','));

		if(ret > 0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, reqInfo);
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "添加卡片失败！", null);
		}	
	}
	/**
	 * 
	 * @Title: addZzCard
	 * @Description: 添加专职卡片
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "addZzCard")
	public void addZzCard(HttpServletRequest request, HttpServletResponse response, CardInfo reqInfo,
			@RequestParam(required = false) MultipartFile[] imageFiles, String publicTypeIds) {
		
		
		if(reqInfo == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少卡片相关参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCardName())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少name参数", null);
			return;
		}
		
		if(reqInfo.getSex() == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少sex参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少creater参数", null);
			return;
		}	
		
		if(StringUtils.isNoneBlank(reqInfo.getBirthDateStr())){
			reqInfo.setBirthDate(DateUtils.formatDate(DateFormatUtil.FormatDate(reqInfo.getBirthDateStr()), DateUtils.DATE_DEFAULT_FORMAT));
		}
		
		reqInfo.setType(4); // 1艺人；2租借；3策划/创意；4婚礼/派对
		
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

		int ret = cardInfoService.addCard(reqInfo, picUuidList.toArray(new String[0]), StringUtils.split(publicTypeIds,','));

		if(ret > 0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, reqInfo);
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "添加卡片失败！", null);
		}	
	}
	/**
	 * 
	 * @Title: addAhCard
	 * @Description: 添加爱好卡片
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "addAhCard")
	public void addAhCard(HttpServletRequest request, HttpServletResponse response, CardInfo reqInfo,
			@RequestParam(required = false) MultipartFile[] imageFiles, String publicTypeIds) {
		
		
		if(reqInfo == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少卡片相关参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCardName())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少name参数", null);
			return;
		}
		
		if(reqInfo.getSex() == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少sex参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少creater参数", null);
			return;
		}	
		
		if(StringUtils.isNoneBlank(reqInfo.getBirthDateStr())){
			reqInfo.setBirthDate(DateUtils.formatDate(DateFormatUtil.FormatDate(reqInfo.getBirthDateStr()), DateUtils.DATE_DEFAULT_FORMAT));
		}
		
		reqInfo.setType(6); // 1艺人；2租借；3策划/创意；4婚礼/派对
		
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

		int ret = cardInfoService.addCard(reqInfo, picUuidList.toArray(new String[0]), StringUtils.split(publicTypeIds,','));

		if(ret > 0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, reqInfo);
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "添加卡片失败！", null);
		}	
	}
	/**
	 * 
	 * @Title: addZpCard
	 * @Description: 添加作品卡片
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "addZpCard")
	public void addZpCard(HttpServletRequest request, HttpServletResponse response, CardInfo reqInfo,
			@RequestParam(required = false) MultipartFile[] imageFiles, String publicTypeIds) {
		
		
		if(reqInfo == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少卡片相关参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCardName())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少name参数", null);
			return;
		}
		
		if(reqInfo.getSex() == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少sex参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少creater参数", null);
			return;
		}	
		
		if(StringUtils.isNoneBlank(reqInfo.getBirthDateStr())){
			reqInfo.setBirthDate(DateUtils.formatDate(DateFormatUtil.FormatDate(reqInfo.getBirthDateStr()), DateUtils.DATE_DEFAULT_FORMAT));
		}
		
		reqInfo.setType(7); // 1艺人；2租借；3策划/创意；4婚礼/派对
		
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

		int ret = cardInfoService.addCard(reqInfo, picUuidList.toArray(new String[0]), StringUtils.split(publicTypeIds,','));

		if(ret > 0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, reqInfo);
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "添加卡片失败！", null);
		}	
	}
	/**
	 * 
	 * @Title: addGyCard
	 * @Description: 添加公益卡片
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "addGyCard")
	public void addGyCard(HttpServletRequest request, HttpServletResponse response, CardInfo reqInfo,
			@RequestParam(required = false) MultipartFile[] imageFiles, String publicTypeIds) {
		
		
		if(reqInfo == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少卡片相关参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCardName())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少name参数", null);
			return;
		}
		
		if(reqInfo.getSex() == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少sex参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少creater参数", null);
			return;
		}	
		
		if(StringUtils.isNoneBlank(reqInfo.getBirthDateStr())){
			reqInfo.setBirthDate(DateUtils.formatDate(DateFormatUtil.FormatDate(reqInfo.getBirthDateStr()), DateUtils.DATE_DEFAULT_FORMAT));
		}
		
		reqInfo.setType(8); // 1艺人；2租借；3策划/创意；4婚礼/派对
		
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

		int ret = cardInfoService.addCard(reqInfo, picUuidList.toArray(new String[0]), StringUtils.split(publicTypeIds,','));

		if(ret > 0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, reqInfo);
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "添加卡片失败！", null);
		}	
	}
	/**
	 * 
	 * @Title: addShCard
	 * @Description: 添加生活卡片
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "addShCard")
	public void addShCard(HttpServletRequest request, HttpServletResponse response, CardInfo reqInfo,
			@RequestParam(required = false) MultipartFile[] imageFiles, String publicTypeIds) {
		
		
		if(reqInfo == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少卡片相关参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCardName())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少name参数", null);
			return;
		}
		
		if(reqInfo.getSex() == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少sex参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少creater参数", null);
			return;
		}	
		
		if(StringUtils.isNoneBlank(reqInfo.getBirthDateStr())){
			reqInfo.setBirthDate(DateUtils.formatDate(DateFormatUtil.FormatDate(reqInfo.getBirthDateStr()), DateUtils.DATE_DEFAULT_FORMAT));
		}
		
		reqInfo.setType(9); // 1艺人；2租借；3策划/创意；4婚礼/派对
		
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

		int ret = cardInfoService.addCard(reqInfo, picUuidList.toArray(new String[0]), StringUtils.split(publicTypeIds,','));

		if(ret > 0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, reqInfo);
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "添加卡片失败！", null);
		}	
	}
	/**
	 * 
	 * @Title: addJyCard
	 * @Description: 添加交友卡片
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "addJyCard")
	public void addJyCard(HttpServletRequest request, HttpServletResponse response, CardInfo reqInfo,
			@RequestParam(required = false) MultipartFile[] imageFiles, String publicTypeIds) {
		
		
		if(reqInfo == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少卡片相关参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCardName())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少name参数", null);
			return;
		}
		
		if(reqInfo.getSex() == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少sex参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少creater参数", null);
			return;
		}	
		
		if(StringUtils.isNoneBlank(reqInfo.getBirthDateStr())){
			reqInfo.setBirthDate(DateUtils.formatDate(DateFormatUtil.FormatDate(reqInfo.getBirthDateStr()), DateUtils.DATE_DEFAULT_FORMAT));
		}
		
		reqInfo.setType(10); // 1艺人；2租借；3策划/创意；4婚礼/派对
		
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

		int ret = cardInfoService.addCard(reqInfo, picUuidList.toArray(new String[0]), StringUtils.split(publicTypeIds,','));

		if(ret > 0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, reqInfo);
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "添加卡片失败！", null);
		}	
	}
	/**
	 * 
	 * @Title: addJyCard
	 * @Description: 添加交友卡片
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "addSfCard")
	public void addSfCard(HttpServletRequest request, HttpServletResponse response, CardInfo reqInfo,
			@RequestParam(required = false) MultipartFile[] imageFiles, String publicTypeIds) {
		
		
		if(reqInfo == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少卡片相关参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCardName())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少name参数", null);
			return;
		}
		
		if(reqInfo.getSex() == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少sex参数", null);
			return;
		}
		
		if(StringUtils.isBlank(reqInfo.getCreater())) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "缺少creater参数", null);
			return;
		}	
		
		if(StringUtils.isNoneBlank(reqInfo.getBirthDateStr())){
			reqInfo.setBirthDate(DateUtils.formatDate(DateFormatUtil.FormatDate(reqInfo.getBirthDateStr()), DateUtils.DATE_DEFAULT_FORMAT));
		}
		
		reqInfo.setType(11); // 1艺人；2租借；3策划/创意；4婚礼/派对
		
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

		int ret = cardInfoService.addCard(reqInfo, picUuidList.toArray(new String[0]), StringUtils.split(publicTypeIds,','));

		if(ret > 0){
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, reqInfo);
		}else {
			this.writeJsonObject(response, AppRetCode.ERROR, "添加卡片失败！", null);
		}	
	}
}
