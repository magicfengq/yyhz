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
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.data.model.SystemMenuAct;
import com.yyhz.sc.services.SystemMenuActService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: SystemMenuActController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-02-25 10:57:14 
* @Copyright：
 */
@Controller
public class SystemMenuActController extends BaseController {
	
    @Resource
    private SystemMenuActService service;

    @RequestMapping(value = "/system/systemMenuActAjaxAll")
    @ResponseBody
    public JSONArray systemMenuActAjaxAll(HttpServletRequest request, HttpServletResponse response,
                                          SystemMenuAct info) {
        info.setOrder("asc");
        info.setSort("orderList");
        List<SystemMenuAct> list = service.selectAll(info);
        return (JSONArray) JSON.toJSON(list);
    }

    @RequestMapping(value = "/system/systemMenuActAjaxSave")
    @ResponseBody
    public Object systemMenuActAjaxSave(HttpServletRequest request, HttpServletResponse response, SystemMenuAct info) {
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

    @RequestMapping(value = "/system/systemMenuActAjaxDelete")
    @ResponseBody
    public Object systemMenuActAjaxDelete(HttpServletRequest request, HttpServletResponse response,
                                          SystemMenuAct info) {
        int result = 0;
        try {
            result = service.delete(info);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return getJsonResult(result, "操作成功", "删除失败！");
    }
}
