package com.yyhz.sc.controller.back;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.data.model.AnnounceInfo;
import com.yyhz.sc.data.model.AuthenticateApply;
import com.yyhz.sc.data.model.CardAnnouncePublicType;
import com.yyhz.sc.data.model.CardInfo;
import com.yyhz.sc.data.model.ContentReportInfo;
import com.yyhz.sc.data.model.PublicType;
import com.yyhz.sc.data.model.ShowInfo;
import com.yyhz.sc.data.model.ShowInfoPictures;
import com.yyhz.sc.services.ActorCommentService;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.AnnounceInfoService;
import com.yyhz.sc.services.CardAnnouncePublicTypeService;
import com.yyhz.sc.services.CardInfoService;
import com.yyhz.sc.services.ContentReportInfoService;
import com.yyhz.sc.services.PublicTypeService;
import com.yyhz.sc.services.ShowInfoPicturesService;
import com.yyhz.sc.services.ShowInfoService;
import com.yyhz.utils.UUIDUtil;
/**
 * 
* @ClassName: ContentReportInfoController 
* @Description: app首页后台管理控制层 
* @author fengq 
* @date 2019-01-22 15:46:01 
* @Copyright：app首页后台管理
 */
@Controller
public class ContentReportInfoController extends BaseController {
	@Resource
	private ContentReportInfoService service;
	@Resource
	private CardInfoService cardService;
	@Resource
	private ActorInfoService actorInfoService;
	
	@Resource
	private ActorCommentService actorCommentService;
	
	@Resource
	private AnnounceInfoService announceService;
	
	@Resource
	private CardAnnouncePublicTypeService cardAnnouncePublicTypeService;
	
	@Resource
	private PublicTypeService publicTypeService;
	
	@Resource
	private ShowInfoService showInfoService;
	
	@Resource
	private ShowInfoPicturesService showInfoPicturesService;
	/**
	 * 列表页面跳转
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/back/contentReportInfoList")
	public String contentReportInfoList(HttpServletRequest request,
			HttpServletResponse response) {
		return "back/contentReport/content_report_info_list";
	}

	/**
	 * 分页
	 * @param request
	 * @param response
	 * @param info
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/back/contentReportInfoAjaxPage")
	@ResponseBody
	public Object contentReportInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, ContentReportInfo info, Integer page,
			Integer rows) {
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return JSONObject.toJSON(pageInfo);
	}

	/**
	 * 查询所有
	 * @param request
	 * @param response
	 * @param info
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/back/contentReportInfoAjaxAll")
	@ResponseBody
	public Object contentReportInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, ContentReportInfo info, Integer page,
			Integer rows) {
		List<ContentReportInfo> results= service.selectAll(info);
		return JSONObject.toJSON(results); 
	}
	
	/**
	 * 保存
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/back/contentReportInfoAjaxSave")
	@ResponseBody
	public Object contentReportInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, ContentReportInfo info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			result = service.insert(info);
			msg = "保存失败！";
		} else {
			result = service.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/back/contentReportInfoAjaxDelete")
	@ResponseBody
	public Object contentReportInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, ContentReportInfo info) {
		int result = 0;
		if (info.getId() == null) {
			return getJsonResult(result,"操作成功", "操作失败,ID为空！");
		}
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "操作失败！");
	}
	@RequestMapping(value = "/back/reportDetail")
	public String contentReportDetail(HttpServletRequest request,HttpServletResponse response,String id) {
		ContentReportInfo info = service.selectById(id);
		if(info.getType().equals("1"))//通告
		{
			AnnounceInfo announceInfo = announceService.selectById(info.getReportId());
			
			Map<String,Object> publicTypeParams = new HashMap<String,Object>();
			publicTypeParams.put("cardAnnounceId", info.getReportId());
			List<CardAnnouncePublicType> publicTypeList = cardAnnouncePublicTypeService.selectAll(publicTypeParams);
			List<String> publicIdList = new ArrayList<String>();
			if(publicTypeList != null && !publicTypeList.isEmpty()){
				for(CardAnnouncePublicType cardAnnouncePublicType : publicTypeList){
					publicIdList.add(cardAnnouncePublicType.getPublicTypeId());
				}
			}
			StringBuffer publicTypeNames = new StringBuffer("");
			if(publicIdList != null && !publicIdList.isEmpty()){
				List<PublicType> ptList = publicTypeService.selectByIds(publicIdList);
				if(ptList != null && !ptList.isEmpty()){
					for(PublicType publicType : ptList){
						publicTypeNames.append(publicType.getName()).append(",");
					}
				}
			}
			if(!"".equals(publicTypeNames.toString())){
				publicTypeNames = publicTypeNames.deleteCharAt(publicTypeNames.length() - 1); 
			}
			announceInfo.setPublicTypeNames(publicTypeNames.toString());
			ActorInfo actorInfo = actorInfoService.selectById(announceInfo.getCreater());
			float avgScore = actorCommentService.getAvgScore(announceInfo.getCreater());
			if(avgScore == 0.0f){
				actorInfo.setAvgScore(null);
			}else{
				actorInfo.setAvgScore(avgScore);
			}
			request.setAttribute("reportDetail", info);
			request.setAttribute("info", announceInfo);
			request.setAttribute("actorInfo", actorInfo);
			return "back/contentReport/report_announce_detail";
		}
		else if(info.getType().equals("2"))//卡片
		{
			CardInfo cardInfo = cardService.selectById(info.getReportId());
			String actorId = cardInfo.getCreater();
			ActorInfo actorInfo = actorInfoService.selectById(actorId);
			float avgScore = actorCommentService.getAvgScore(actorId);
			if(avgScore == 0.0f){
				actorInfo.setAvgScore(null);
			}else{
				actorInfo.setAvgScore(avgScore);
			}
			request.setAttribute("reportDetail", info);
			request.setAttribute("cardDetail", cardInfo);
			request.setAttribute("actorInfo", actorInfo);
			return "back/contentReport/report_card_detail";	
		}
		else//秀一秀
		{
			ShowInfo showInfo = showInfoService.selectById(info.getReportId());
			request.setAttribute("reportDetail", info);
			request.setAttribute("showInfo", showInfo);
			request.setAttribute("id", info.getReportId());
			if("0".equals(showInfo.getType())){
				//图片
				ShowInfoPictures pictures = new ShowInfoPictures();
				pictures.setShowId(info.getReportId());
				List<ShowInfoPictures> picList = showInfoPicturesService.selectAll(pictures);
				request.setAttribute("picList", picList);
			}
			return "back/contentReport/report_show_detail";
		}
	}
	/**
	 * 拒绝举报
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/back/refuseReport")
	@ResponseBody
	public Object refuseReport(HttpServletRequest request,
			HttpServletResponse response, String id) {
		int result = 0;
		String msg = "";
		ContentReportInfo info = new ContentReportInfo();
		info.setId(id);
		info.setStatus("2");
		result = service.update(info);
		msg = "修改失败！";
		
		return getJsonResult(result, "操作成功",msg);
	}
	/**
	 * 通过举报
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/back/passReport")
	@ResponseBody
	public Object passReport(HttpServletRequest request,
			HttpServletResponse response, String id) {
		int result = 0;
		String msg = "修改失败！";
		ContentReportInfo info = service.selectById(id);
		if(info!=null){			
			
			info.setStatus("1");
			result = service.update(info);
			if(result>0){
				if(info.getType().equals("1"))//通告
				{
					AnnounceInfo announceInfo = announceService.selectById(info.getReportId());
					announceInfo.setStatus(1);
					result=announceService.update(announceInfo);
				}
				else if(info.getType().equals("2"))//卡片
				{
					CardInfo cardInfo = cardService.selectById(info.getReportId());
					cardInfo.setStatus(1);
					result=cardService.update(cardInfo);
				}
				else//秀一秀
				{
					ShowInfo showInfo = showInfoService.selectById(info.getReportId());
					showInfo.setStatus("1");
					result=showInfoService.update(showInfo);
				}
			}
		}
		
		
		
		return getJsonResult(result, "操作成功",msg);
	}
}
