package com.yyhz.sc.controller.back;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyhz.sc.data.model.PublicType;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.services.PublicTypeService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: PublicTypeController 
* @Description: 控制层 
* @author crazyt 
* @date 2017-03-12 16:08:27 
* @Copyright：
 */
@Controller
public class PublicTypeController extends BaseController {
	
	@Resource
	private PublicTypeService publicTypeService;

	@RequestMapping(value = "system/publicTypeList")
	public String publicTypeList(HttpServletRequest request,HttpServletResponse response) {
		return "back/public_type_list";
	}

	@RequestMapping(value = "system/publicTypeAjaxPage")
	@ResponseBody
	public PageInfo<PublicType> publicTypeAjaxPage(HttpServletRequest request,
			HttpServletResponse response, PublicType info, Integer page,
			Integer rows) {
		PageInfo<PublicType> pageInfo = new PageInfo<PublicType>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setStatus(0);
		info.setSort(info.getSort());
		info.setOrder(info.getOrder());
		publicTypeService.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "system/publicTypeAjaxAll")
	@ResponseBody
	public List<PublicType> publicTypeAjaxAll(HttpServletRequest request,
			HttpServletResponse response, PublicType info, Integer page,
			Integer rows) {
		List<PublicType> results= publicTypeService.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/publicTypeAjaxSave")
	@ResponseBody
	public Map<String,Object> publicTypeAjaxSave(HttpServletRequest request,
			HttpServletResponse response, PublicType info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			result = publicTypeService.insert(info);
			msg = "保存失败！";
		} else {
			result = publicTypeService.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "system/publicTypeAjaxDelete")
	@ResponseBody
	public Map<String,Object> publicTypeAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, PublicType info) {
		int result = 0;
		try {
			info.setStatus(1);
			result = publicTypeService.update(info);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
