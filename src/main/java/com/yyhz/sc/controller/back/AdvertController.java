package com.yyhz.sc.controller.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.data.model.Advert;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.AdvertService;
import com.yyhz.utils.stream.util.StreamVO;

/**
 * 
* @ClassName: AdvertController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-02-25 13:03:33 
* @Copyright：
 */
@Controller
public class AdvertController extends BaseController {
	
	@Resource
	private AdvertService advertService;
	
	@Resource
	private ActorInfoService actorInfoService;

	@RequestMapping(value = "system/advertList")
	public String advertList(HttpServletRequest request,HttpServletResponse response) {
		return "back/advert_list";
	}

	@RequestMapping(value = "system/advertAjaxPage")
	@ResponseBody
	public PageInfo<Advert> advertAjaxPage(HttpServletRequest request,
			HttpServletResponse response, Advert info, Integer page,
			Integer rows) {
		PageInfo<Advert> pageInfo = new PageInfo<Advert>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setStatus(0);
		info.setSort("orderNum");
		info.setOrder("asc");
		advertService.selectAll(info, pageInfo);
		List<Advert> list = pageInfo.getRows();
		if(list != null && !list.isEmpty()){
			for(Advert advert : list ){
				if(advert.getType().equals(1)){
					//个人秀,查询用户信息
					ActorInfo actorInfo = actorInfoService.selectById(advert.getParams());
					if(actorInfo == null){
						continue;
					}
					advert.setUserId(actorInfo.getId());
					advert.setUserName(actorInfo.getName());
				}
			}
		}		
		return pageInfo;
	}
	
	@RequestMapping(value = "system/advertAjaxSave")
	@ResponseBody
	public Map<String,Object> advertAjaxSave(HttpServletRequest request,
			HttpServletResponse response, Advert info,StreamVO streamVO,String operType) {
		
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setCreater(getSessionUser(request).getId());
			info.setOperater(getSessionUser(request).getId());
			if(info.getType().equals(1)){
				info.setParams(info.getUserId());
			}			
			result = advertService.insertWithImage(info,streamVO);
			msg = "保存失败！";
		} else {
			if(info.getType().equals(1)){
				info.setParams(info.getUserId());
			}			
			//根据opertyp判断是否需要上传
			if(StringUtils.isBlank(operType)){
				result = advertService.update(info);
			}else{
				result = advertService.updateWithImage(info,streamVO);
			}
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "system/advertAjaxDelete")
	@ResponseBody
	public Map<String,Object> advertAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, Advert info) {
		int result = 0;
		try {
			info.setStatus(1);
			result = advertService.update(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
