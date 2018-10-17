package com.yyhz.sc.controller.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yyhz.constant.AppRetCode;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.data.model.Advert;
import com.yyhz.sc.data.model.AnnounceInfo;
import com.yyhz.sc.data.model.CardInfo;
import com.yyhz.sc.data.model.SiteList;
import com.yyhz.sc.data.model.SystemCityRegion;
import com.yyhz.sc.data.model.VersionInfo;
import com.yyhz.sc.services.AdvertService;
import com.yyhz.sc.services.AnnounceInfoService;
import com.yyhz.sc.services.CardInfoService;
import com.yyhz.sc.services.SiteListService;
import com.yyhz.sc.services.SystemCityRegionService;
import com.yyhz.sc.services.VersionInfoService;
import com.yyhz.utils.stream.config.Configurations;

@Controller
@RequestMapping(value = "api")
public class AppIndexController extends BaseController {

	@Resource
	private AdvertService advertService;
	
	@Resource
	private SystemCityRegionService systemCityRegionService;
	@Resource
	private SiteListService siteListService;
	@Resource 
	private CardInfoService cardInfoService;
	@Resource 
	private AnnounceInfoService announceInfoService;
	@Resource
	private VersionInfoService versionInfoService;

	/**
	 * 
	 * @Title: getIndexImages
	 * @Description: 获取首页轮播图片
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getIndexImages")
	public void getIndexImages(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", 0);
		List<Advert> list = advertService.selectAll(params,"selectAdvertWithUrl");
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if (list != null && !list.isEmpty()) {
			for (Advert advert : list) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", advert.getId());
				if(StringUtils.isNotBlank(advert.getPicurl())) {
					dataMap.put("webImgUrl", Configurations.buildDownloadUrl(advert.getPicurl()));
				}
				dataMap.put("type", advert.getType());
				if(advert.getType() == 0) { // 广告
					dataMap.put("params", advert.getId());
				}else { // 个人秀
					dataMap.put("params", advert.getParams());				
				}
				dataList.add(dataMap);
			}
		}
		Map<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("sList", dataList);

		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, listMap);
	}
	
	/**
	 * 
	 * @Title: getCityRegion
	 * @Description: 获取省市信息
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getAllCities")
	public void getAllCities(HttpServletRequest request, HttpServletResponse response, String keyword) {
		
		SystemCityRegion condition = new SystemCityRegion();
		condition.setSort("code");
		condition.setOrder("asc");

		condition.setLevel("2");
		condition.setKeyword(keyword);
		List<SystemCityRegion> citys = systemCityRegionService.selectAll(condition, "selectAllSummary");
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("citys", citys);

		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);
	}
	
	/**
	 * 
	 * @Title: getCityRegion
	 * @Description: 获取省市信息
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getCityRegion")
	public void getCityRegion(HttpServletRequest request, HttpServletResponse response) {
		
		SystemCityRegion condition = new SystemCityRegion();
		condition.setSort("code");
		condition.setOrder("asc");
		
		condition.setLevel("1");		
		List<SystemCityRegion> provinces = systemCityRegionService.selectAll(condition, "selectAllSummary");
		
		condition.setLevel("2");		
		List<SystemCityRegion> citys = systemCityRegionService.selectAll(condition, "selectAllSummary");

		condition.setLevel("3");		
		List<SystemCityRegion> areas = systemCityRegionService.selectAll(condition, "selectAllSummary");
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("provinces", provinces);
		ret.put("citys", citys);
		ret.put("areas", areas);

		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);
	}
	
	/**
	 * 
	 * @Title: getProvinces
	 * @Description: 获取省市信息
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getProvinces")
	public void getProvinces(HttpServletRequest request, HttpServletResponse response) {
		
		SystemCityRegion condition = new SystemCityRegion();
		condition.setSort("code");
		condition.setOrder("asc");
		
		condition.setLevel("1");		
		List<SystemCityRegion> provinces = systemCityRegionService.selectAll(condition, "selectAllSummary");
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("provinces", provinces);

		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);
	}
	
	/**
	 * 
	 * @Title: getCitys
	 * @Description: 获取省市信息
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getCitys")
	public void getCitys(HttpServletRequest request, HttpServletResponse response, String provinceCode) {
		
		if(StringUtils.isBlank(provinceCode)) {
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, "省邮政编码为空");
			return;
		}
		
		SystemCityRegion condition = new SystemCityRegion();
		condition.setSort("code");
		condition.setOrder("asc");
		
		condition.setParentCode(provinceCode);	
		List<SystemCityRegion> citys = systemCityRegionService.selectAll(condition, "selectAllSummary");
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("citys", citys);

		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);
	}
	
	/**
	 * 
	 * @Title: getAreas
	 * @Description: 获取省市信息
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getAreas")
	public void getAreas(HttpServletRequest request, HttpServletResponse response, String cityCode) {
		
		if(StringUtils.isBlank(cityCode)) {
			this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, "市政编码为空");
			return;
		}
		
		SystemCityRegion condition = new SystemCityRegion();
		condition.setSort("code");
		condition.setOrder("asc");
		
		condition.setParentCode(cityCode);	
		List<SystemCityRegion> citys = systemCityRegionService.selectAll(condition, "selectAllSummary");
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("citys", citys);

		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);
	}
	
	
	/**
	 * 
	 * @Title: getCityList
	 * @Description: 获取卡片城市列表
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getCityList")
	public void getCityList(HttpServletRequest request, HttpServletResponse response, Integer catalog, Integer type) {
		if(catalog == null || catalog < 0 || catalog > 3) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "catalog参数错误。1:通告;2:卡片;3:场地", null);
			return;
		}
		
		switch(catalog) {
		case 1:
			getAnnouncementCityList(request, response, type);
			break;
		case 2:
			getCardCityList(request, response, type);
			break;
		case 3:
			getSiteCityList(request, response);
			break;
		}
	}
	
	/**
	 * 
	 * @Title: getCardCityList
	 * @Description: 获取卡片城市列表
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getCardCityList")
	public void getCardCityList(HttpServletRequest request, HttpServletResponse response, Integer type) {
		
		if(type == null || type < 0) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "type参数错误。0全部；1艺人；2租借；3策划/创意；4婚礼/派对", null);
			return;
		}

		CardInfo condition = new CardInfo();
		if(type > 0) {
			condition.setType(type);			
		}
		condition.setStatus(0); // 状态 0正常；1已删除；
		List<CardInfo> cardList = cardInfoService.selectAll(condition, "selectCityList");
		Map<String, Object> ret = new HashMap<String, Object>();
		List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
		
		if(cardList != null) {
			HashSet<String> hashSet = new HashSet<String>();
			for(CardInfo card : cardList) {
				
				String actCities = card.getActCities();
				
				String[] actCityArray = StringUtils.split(actCities, ',');
				
				hashSet.addAll(Arrays.asList(actCityArray));
			}
			
			String[] cities = hashSet.toArray(new String[0]);
			
			for(String cityName : cities) {
				Map<String, Object> city = new HashMap<String, Object>();
				city.put("city", cityName);
				cityList.add(city);
			}
		}
		
		ret.put("cities", cityList);
		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);
	}
	
	/**
	 * 
	 * @Title: getAnnouncementCityList
	 * @Description: 获取通告城市列表
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getAnnouncementCityList")
	public void getAnnouncementCityList(HttpServletRequest request, HttpServletResponse response, Integer type) {
		
		if(type == null || type < 0) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "type参数错误。0全部；1艺人；2租借；3策划/创意；4婚礼/派对", null);
			return;
		}

		AnnounceInfo condition = new AnnounceInfo();
		if(type > 0) {
			condition.setType(type);			
		}

		List<AnnounceInfo> announceList = announceInfoService.selectAll(condition, "selectCityList");
		Map<String, Object> ret = new HashMap<String, Object>();
		
		List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
		
		if(announceList != null) {
			for(AnnounceInfo announce :announceList) {
				Map<String, Object> city = new HashMap<String, Object>();
				if(announce == null) {
					continue;
				}
				city.put("city", announce.getCity());
				cityList.add(city);
			}
		}
		ret.put("cities", cityList);
		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);
	}
	
	/**
	 * 
	 * @Title: getSiteCityList
	 * @Description: 推荐场地
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "getSiteCityList")
	public void getSiteCityList(HttpServletRequest request, HttpServletResponse response) {
		SiteList condition = new SiteList();

		List<SiteList> siteList = siteListService.selectAll(condition, "selectCityList");
		Map<String, Object> ret = new HashMap<String, Object>();
		

		List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
		
		if(siteList != null) {
			for(SiteList site : siteList) {
				Map<String, Object> city = new HashMap<String, Object>();
				if(site == null) {
					continue;
				}
				city.put("city", site.getCity());
				cityList.add(city);
			}
		}
		
		ret.put("cities", cityList);
		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);
	}
	
	/**
	 * 
	 * @Title: 获取最新版本信息
	 * @Description: 获取最新版本信息
	 * @return JSON
	 * @author CrazyT
	 * 
	 */
	@RequestMapping(value = "versonUpdate")
	public void versonUpdate(HttpServletRequest request, HttpServletResponse response, Integer type) {
		VersionInfo versionInfo = null;
		Map<String, Object> ret = new HashMap<String, Object>();

		if(type == null) {
			this.writeJsonObject(response, AppRetCode.PARAM_ERROR, "参数type错误！", ret);
			return;
		}
		
		VersionInfo condition = new VersionInfo();
		if(type == 0) { // IOS
			condition.setType("2");		
		} else {
			condition.setType("1");		
		}
		condition.setSort("versionCode");
		condition.setOrder("desc");
		
		List<VersionInfo> verList = versionInfoService.selectAll(condition);
		if(verList != null && verList.size() > 0) {
			versionInfo = verList.get(0);
		}
		
		ret.put("verCode", versionInfo.getVersionCode());
		if(type == 0) { // IOS
			ret.put("downLoadPath", versionInfo.getDownloadUrl());			
		} else {
			ret.put("downLoadPath", Configurations.buildDownloadUrl(versionInfo.getDownloadUrl()));				
		}
		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);
	}
}
