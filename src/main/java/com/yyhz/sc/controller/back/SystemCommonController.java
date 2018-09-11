package com.yyhz.sc.controller.back;

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
import com.yyhz.sc.data.model.PublicType;
import com.yyhz.sc.services.PublicTypeService;

@Controller 
public class SystemCommonController extends BaseController{
	
	@Resource
	private PublicTypeService publicTypeService;
	
	@RequestMapping(value = "system/userSelect")
	public String actorInfoList(HttpServletRequest request,HttpServletResponse response) {
		return "back/common/user_select";
	}
	
	@RequestMapping(value = "system/userMutiSelect")
	public String userMutiSelect(HttpServletRequest request,HttpServletResponse response) {
		return "back/common/user_muti_select";
	}
	
	@RequestMapping(value = "system/publicTypeForCombo")
	@ResponseBody
	public List<PublicType> publicTypeForCombo(HttpServletRequest request,HttpServletResponse response,String type) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("type", type);
		return publicTypeService.selectAll(params);
	}
}
