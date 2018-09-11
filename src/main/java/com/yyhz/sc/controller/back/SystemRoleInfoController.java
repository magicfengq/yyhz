package com.yyhz.sc.controller.back;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.data.model.SystemRoleInfo;
import com.yyhz.sc.services.SystemRoleInfoService;
import com.yyhz.utils.UUIDUtil;

/**
 * 
* @ClassName: SystemRoleInfoController 
* @Description: 控制层 
* @author lipeng 
* @date 2017-02-25 11:05:57 
* @Copyright：
 */
@Controller
public class SystemRoleInfoController extends BaseController {

    @Resource
    private SystemRoleInfoService systemRoleService;

    @RequestMapping(value = "/system/systemRoleList")
    public String systemRoleList(HttpServletRequest request, HttpServletResponse response) {
        return "system/system_role_list";
    }

    @RequestMapping(value = "/system/systemRoleAjaxPage")
    @ResponseBody
    public JSONObject systemRoleAjaxPage(HttpServletRequest request, HttpServletResponse response,
                                         SystemRoleInfo systemRole, Integer page, Integer rows) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(page);
        pageInfo.setPageSize(rows);
        systemRoleService.selectAll(systemRole, pageInfo);
        return (JSONObject) JSONObject.toJSON(pageInfo);
    }

    @RequestMapping(value = "/system/systemRoleAjaxAll")
    @ResponseBody
    public JSONArray systemRoleAjaxAll(HttpServletRequest request, HttpServletResponse response, SystemRoleInfo systemRole,
                                       Integer page, Integer rows) {
        List<SystemRoleInfo> roles = systemRoleService.selectAll(systemRole);
        return (JSONArray) JSONArray.toJSON(roles);
    }

    @RequestMapping(value = "/system/systemRoleAjaxSave")
    @ResponseBody
    public Object systemRoleAjaxSave(HttpServletRequest request, HttpServletResponse response, SystemRoleInfo systemRole) {
        int result = 0;
        String msg = "";
        if (systemRole.getId() == null || systemRole.getId().equals("")) {
            systemRole.setId(UUIDUtil.getUUID());
            result = systemRoleService.insert(systemRole);
            msg = "保存失败！";
        } else {
            result = systemRoleService.update(systemRole);
            msg = "修改失败！";
        }
        return getJsonResult(result, "操作成功", msg);
    }

    @RequestMapping(value = "/system/systemRoleAjaxDelete")
    @ResponseBody
    public Object systemRoleAjaxDelete(HttpServletRequest request, HttpServletResponse response, SystemRoleInfo info) {
        int result = 0;
        try {
            result = systemRoleService.delete(info);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return getJsonResult(result, "操作成功", "删除失败！");
    }
}
