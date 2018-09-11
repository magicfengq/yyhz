package com.yyhz.sc.controller.back;

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
import com.yyhz.sc.data.model.Feedback;
import com.yyhz.sc.services.FeedbackService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: FeedbackController 
* @Description: 控制层 
* @author crazyt 
* @date 2017-03-27 16:30:37 
* @Copyright：
 */
@Controller
public class FeedbackController extends BaseController {
	@Resource
	private FeedbackService service;

	@RequestMapping(value = "/back/feedbackList")
	public String feedbackList(HttpServletRequest request,
			HttpServletResponse response) {
		return "/back/feedback/feedback_list";
	}
	
	@RequestMapping(value = "/back/feedbackDetail")
	public String feedbackDetail(HttpServletRequest request,
			HttpServletResponse response,String id) {
		Feedback info = service.selectById(id);
		request.setAttribute("info", info);
		return "/back/feedback/feedback_detail";
	}

	@RequestMapping(value = "/back/feedbackAjaxPage")
	@ResponseBody
	public PageInfo<Feedback> feedbackAjaxPage(HttpServletRequest request,
			HttpServletResponse response, Feedback info, Integer page,
			Integer rows) {
		PageInfo<Feedback> pageInfo = new PageInfo<Feedback>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "/feedbackAjaxAll")
	@ResponseBody
	public List<Feedback> feedbackAjaxAll(HttpServletRequest request,
			HttpServletResponse response, Feedback info, Integer page,
			Integer rows) {
		List<Feedback> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "/feedbackAjaxSave")
	@ResponseBody
	public Map<String,Object> feedbackAjaxSave(HttpServletRequest request,
			HttpServletResponse response, Feedback info) {
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

	@RequestMapping(value = "back/feedbackAjaxDelete")
	@ResponseBody
	public Map<String,Object> feedbackAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, Feedback info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
