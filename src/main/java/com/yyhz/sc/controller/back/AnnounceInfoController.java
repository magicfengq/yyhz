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

import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.data.model.AnnounceInfo;
import com.yyhz.sc.data.model.CardAnnouncePublicType;
import com.yyhz.sc.data.model.PublicType;
import com.yyhz.sc.services.ActorCommentService;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.AnnounceInfoService;
import com.yyhz.sc.services.CardAnnouncePublicTypeService;
import com.yyhz.sc.services.PublicTypeService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: AnnounceInfoController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-03-22 00:31:56 
* @Copyright：
 */
@Controller
@RequestMapping(value = "/back")
public class AnnounceInfoController extends BaseController {
	
	@Resource
	private AnnounceInfoService service;
	
	@Resource
	private ActorInfoService actorInfoService;
	
	@Resource
	private ActorCommentService actorCommentService;
	
	@Resource
	private CardAnnouncePublicTypeService cardAnnouncePublicTypeService;
	
	@Resource
	private PublicTypeService publicTypeService;

	@RequestMapping(value = "/zcmtAnnounceList")
	public String zcmtAnnounceList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/back/announce/zcmt_announce_list";
	}
	@RequestMapping(value = "/sbfzAnnounceList")
	public String sbfzAnnounceList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/back/announce/sbfz_announce_list";
	}
	@RequestMapping(value = "/chcyAnnounceList")
	public String chcyAnnounceList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/back/announce/chcy_announce_list";
	}
	@RequestMapping(value = "/hlpdAnnounceList")
	public String hlpdAnnounceList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/back/announce/hlpd_announce_list";
	}
	
	@RequestMapping(value = "/jzAnnounceList")
	public String jzAnnounceList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/back/announce/jz_announce_list";
	}

	@RequestMapping(value = "/zzAnnounceList")
	public String zzAnnounceList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/back/announce/zz_announce_list";
	}

	@RequestMapping(value = "/ahAnnounceList")
	public String ahAnnounceList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/back/announce/ah_announce_list";
	}

	@RequestMapping(value = "/zpAnnounceList")
	public String zpAnnounceList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/back/announce/zp_announce_list";
	}

	@RequestMapping(value = "/gyAnnounceList")
	public String gyAnnounceList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/back/announce/gy_announce_list";
	}

	@RequestMapping(value = "/shAnnounceList")
	public String shAnnounceList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/back/announce/sh_announce_list";
	}

	@RequestMapping(value = "/jyAnnounceList")
	public String jyAnnounceList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/back/announce/jy_announce_list";
	}
	@RequestMapping(value = "/sfAnnounceList")
	public String sfAnnounceList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/back/announce/sf_announce_list";
	}
	
	
	@RequestMapping(value = "/announceDetail")
	public String announceDetail(HttpServletRequest request,
			HttpServletResponse response,String type,String id) {
		AnnounceInfo info = service.selectById(id);
		
		Map<String,Object> publicTypeParams = new HashMap<String,Object>();
		publicTypeParams.put("cardAnnounceId", id);
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
		info.setPublicTypeNames(publicTypeNames.toString());
		ActorInfo actorInfo = actorInfoService.selectById(info.getCreater());
		float avgScore = actorCommentService.getAvgScore(info.getCreater());
		if(avgScore == 0.0f){
			actorInfo.setAvgScore(null);
		}else{
			actorInfo.setAvgScore(avgScore);
		}
		request.setAttribute("info", info);
		request.setAttribute("actorInfo", actorInfo);
		
		if("1".equals(type)){
			return "back/announce/zcmt_announce_detail";
		}else if("2".equals(type)){
			return "back/announce/sbfz_announce_detail";
		}else if("3".equals(type)){
			//return "back/announce/chcy_announce_detail";
			return "back/announce/jz_announce_detail";
		}else if("4".equals(type)){
			//return "back/announce/hlpd_announce_detail";
			return "back/announce/zz_announce_detail";
		}else if("6".equals(type)){
			return "back/announce/ah_announce_detail";
		}else if("7".equals(type)){
			return "back/announce/zp_announce_detail";
		}else if("8".equals(type)){
			return "back/announce/gy_announce_detail";
		}else if("9".equals(type)){
			return "back/announce/sh_announce_detail";
		}else if("10".equals(type)){
			return "back/announce/jy_announce_detail";
		}else if("11".equals(type)){
			return "back/announce/sf_announce_detail";
		}
		return null;
	}
	
	@RequestMapping(value = "/announceInfoAjaxPage")
	@ResponseBody
	public PageInfo<AnnounceInfo> announceInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, AnnounceInfo info, Integer page,
			Integer rows) {
		PageInfo<AnnounceInfo> pageInfo = new PageInfo<AnnounceInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "/announceInfoAjaxAll")
	@ResponseBody
	public List<AnnounceInfo> announceInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, AnnounceInfo info, Integer page,
			Integer rows) {
		List<AnnounceInfo> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "/announceInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> announceInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, AnnounceInfo info) {
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

	@RequestMapping(value = "/announceInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> announceInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, AnnounceInfo info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
