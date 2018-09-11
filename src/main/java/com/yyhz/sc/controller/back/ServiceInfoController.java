package com.yyhz.sc.controller.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyhz.sc.data.model.ServiceInfo;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.services.ServiceInfoService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: ServiceInfoController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-04-07 15:19:07 
* @Copyright：
 */
@Controller
@RequestMapping(value="/back")
public class ServiceInfoController extends BaseController {
	@Resource
	private ServiceInfoService service;

	@RequestMapping(value = "/serviceInfoEdit")
	public String serviceInfoEdit(HttpServletRequest request,
			HttpServletResponse response,String type) {
		request.setAttribute("type", type);
		ServiceInfo info = new ServiceInfo();
		info.setType(type);
		PageInfo<ServiceInfo> pageInfo = new PageInfo<ServiceInfo>();
		pageInfo.setPage(1);
		pageInfo.setPageSize(1);
		service.selectAll(info, pageInfo);
		String id="";
		String context = "";
		if(pageInfo.getRows().size()>0){
			id = pageInfo.getRows().get(0).getId();
			context = pageInfo.getRows().get(0).getContext();
		}
		request.setAttribute("id", id);
		request.setAttribute("context", context);
		return "/back/serviceinfo/service_info";
	}

	@RequestMapping(value = "/serviceInfoAjaxPage")
	@ResponseBody
	public PageInfo<ServiceInfo> serviceInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, ServiceInfo info, Integer page,
			Integer rows) {
		PageInfo<ServiceInfo> pageInfo = new PageInfo<ServiceInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "/serviceInfoAjaxAll")
	@ResponseBody
	public List<ServiceInfo> serviceInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, ServiceInfo info, Integer page,
			Integer rows) {
		List<ServiceInfo> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "/serviceInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> serviceInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, ServiceInfo info) {
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

	@RequestMapping(value = "/serviceInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> serviceInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, ServiceInfo info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
