package com.yyhz.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yyhz.constant.SessionConstants;
import com.yyhz.sc.data.model.SystemUserInfo;
import com.yyhz.utils.Utils;

public class CommonInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
            throws Exception {
        // if (!Utils.isPass) {
        // redirect(request,response);
        // }
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object arg2) throws Exception {
        Object obj = request.getSession().getAttribute(SessionConstants.SESSION_PERMISSURL);
        Object objectu = request.getSession().getAttribute(SessionConstants.SESSION_USER);
        SystemUserInfo user = objectu == null ? null : (SystemUserInfo) objectu;
        if (user != null) {
            if (user.getRoleId().equals("root")) {
                return true;
            }
        }
        List<Map<String, Object>> urls = (List<Map<String, Object>>) obj;
        String requestUri = request.getRequestURI();
        requestUri = requestUri.substring(requestUri.indexOf("/", 2) + 1, requestUri.length());
        Utils.isPass = false;
        if (urls != null) {
            for (Map<String, Object> map : urls) {
                if (map.get("url").equals(requestUri)) {
                    Utils.isPass = true;
                    break;
                }
            }
        }
        System.out.println(requestUri + "/" + Utils.isPass);
        if (Utils.isPass) {
            return true;
        } else {
            return false;
        }
    }

    private void redirect(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        try {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
