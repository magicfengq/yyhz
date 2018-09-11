package com.yyhz.sc.controller.back;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.data.model.SystemMenuActUrl;
import com.yyhz.sc.services.SystemMenuActUrlService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: SystemMenuActUrlController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-02-25 11:04:10 
* @Copyright：
 */
@Controller
public class SystemMenuActUrlController extends BaseController {
	
    @Resource
    private SystemMenuActUrlService service;

    @RequestMapping(value = "/system/systemMenuActUrlList")
    public String systemMenuActUrlList(HttpServletRequest request,HttpServletResponse response) {
        return "system/system_menu_act_url_list";
    }

    @RequestMapping(value = "/system/systemMenuActUrlAjaxPage")
    @ResponseBody
    public JSONObject systemMenuActUrlAjaxPage(HttpServletRequest request,HttpServletResponse response, SystemMenuActUrl info, Integer page,Integer rows) {
        PageInfo<SystemMenuActUrl> pageInfo = new PageInfo<SystemMenuActUrl>();
        pageInfo.setPage(page);
        pageInfo.setPageSize(rows);
        service.selectAll(info, pageInfo);
        return (JSONObject) JSONObject.toJSON(pageInfo);
    }

    @RequestMapping(value = "/system/systemMenuActUrlAjaxAll")
    @ResponseBody
    public JSONArray systemMenuActUrlAjaxAll(HttpServletRequest request,HttpServletResponse response, SystemMenuActUrl info, Integer page,Integer rows) {
        List<SystemMenuActUrl> results = service.selectAll(info);
        return (JSONArray) JSON.toJSON(results);
    }

    @RequestMapping(value = "/system/systemMenuActUrlAjaxSave")
    @ResponseBody
    public Object systemMenuActUrlAjaxSave(HttpServletRequest request,HttpServletResponse response, SystemMenuActUrl info) {
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
        return getJsonResult(result, "操作成功", msg);
    }

    @RequestMapping(value = "/system/systemMenuActUrlAjaxDelete")
    @ResponseBody
    public Object systemMenuActUrlAjaxDelete(HttpServletRequest request,HttpServletResponse response, SystemMenuActUrl info) {
        int result = 0;
        try {
            result = service.delete(info);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return getJsonResult(result, "操作成功", "删除失败！");
    }
}
