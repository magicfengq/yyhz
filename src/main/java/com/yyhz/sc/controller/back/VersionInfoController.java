package com.yyhz.sc.controller.back;

import java.util.Date;
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
import com.yyhz.sc.data.model.VersionInfo;
import com.yyhz.sc.services.VersionInfoService;
import com.yyhz.utils.UUIDUtil;
import com.yyhz.utils.stream.util.StreamVO;

/**
 * 
* @ClassName: VersionInfoController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-04-14 12:59:52 
* @Copyright：
 */
@Controller
public class VersionInfoController extends BaseController {
	
	@Resource
	private VersionInfoService versionInfoService;
	
	@RequestMapping(value = "system/versionInfoList")
	public String versionInfoList(HttpServletRequest request,HttpServletResponse response,String type) {
		if("1".equals(type)){
			return "back/version/android_version_list";
		}else{
			return "back/version/ios_version_list";
		}
	}

	@RequestMapping(value = "system/versionInfoAjaxPage")
	@ResponseBody
	public PageInfo<VersionInfo> versionInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, VersionInfo info, Integer page,
			Integer rows) {
		PageInfo<VersionInfo> pageInfo = new PageInfo<VersionInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		versionInfoService.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "system/versionInfoAjaxAll")
	@ResponseBody
	public List<VersionInfo> versionInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, VersionInfo info, Integer page,
			Integer rows) {
		List<VersionInfo> results= versionInfoService.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/versionInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> versionInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, VersionInfo info,StreamVO streamVO,String operType) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			info.setCreateTime(new Date());
			if("1".equals(info.getType())){
				result = versionInfoService.insertWithFile(info,streamVO);
			}else{
				result = versionInfoService.insert(info);
			}
			msg = "保存失败！";
		} else {
			if("1".equals(info.getType())){
				//根据opertyp判断是否需要上传
				if(StringUtils.isBlank(operType)){
					result = versionInfoService.update(info);
				}else{
					result = versionInfoService.updateWithFile(info,streamVO);
				}
			}else{
				result = versionInfoService.update(info);
			}
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "system/versionInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> versionInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, VersionInfo info) {
		int result = 0;
		try {
			result = versionInfoService.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
