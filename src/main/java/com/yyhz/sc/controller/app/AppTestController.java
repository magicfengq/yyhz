package com.yyhz.sc.controller.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yyhz.constant.AppRetCode;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.data.model.Advert;
import com.yyhz.sc.data.model.AnnounceInfo;
import com.yyhz.sc.services.AdvertService;

@Controller
@RequestMapping(value = "api")
public class AppTestController extends BaseController {

	@Resource
	private AdvertService advertService;
	
	/**
	 * 
	 * @Title: testDate
	 * @Description: 
	 * @return JSON
	 * @author CrazyT
	 * 
	 */	
	@RequestMapping(value = "testDate")
	public void testDate(HttpServletRequest request, HttpServletResponse response, 
			AnnounceInfo reqInfo) {
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, reqInfo);
	}
	@RequestMapping(value = "testArray")
	public void testArray(HttpServletRequest request, HttpServletResponse response, 
			String[] a, String[] b) {
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("a", a);
		ret.put("b", b);
		
		this.writeJsonObject(response, AppRetCode.NORMAL, AppRetCode.NORMAL_TEXT, ret);
	}

}
