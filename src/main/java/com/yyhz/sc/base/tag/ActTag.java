package com.yyhz.sc.base.tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.yyhz.constant.SessionConstants;
import com.yyhz.sc.data.model.SystemMenu;
import com.yyhz.sc.data.model.SystemMenuAct;
import com.yyhz.sc.data.model.SystemUserInfo;

public class ActTag extends TagSupport {

    private static final long serialVersionUID = 1L;
    private String optCode;
    private boolean hasPermission;

    public String getOptCode() {
        return optCode;
    }

    public void setOptCode(String optCode) {
        this.optCode = optCode;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int doStartTag() throws JspException {

        try {
            hasPermission = false;
            System.out.println(optCode);
            HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
            HttpServletResponse response = (HttpServletResponse) this.pageContext.getResponse();
            SystemUserInfo account = (SystemUserInfo) request.getSession().getAttribute(SessionConstants.SESSION_USER);
            if (null == account || optCode == null) {
                redirectLogin(request, response);
                return SKIP_BODY;
            }
            if (account.getRoleId().equals("root")) {
                return EVAL_BODY_INCLUDE;
            }
            String requestUri = request.getAttribute("javax.servlet.forward.servlet_path").toString();
            Object queryObj = request.getAttribute("javax.servlet.forward.query_string");
            String query_string = "";
            if (queryObj != null) {
                query_string = queryObj.toString();
                requestUri = requestUri + "?" + query_string;
            }

			/*
             * Enumeration<String> ss= request.getAttributeNames(); while
			 * (ss.hasMoreElements()) { String sssn=ss.nextElement(); try {
			 * System .out.println("#####"+sssn+"%%%"+pageContext.getRequest().
			 * getAttribute (sssn)); } catch (Exception e) { // TODO: handle
			 * exception }
			 * 
			 * }
			 */
            requestUri = requestUri.substring(1, requestUri.length());
            HttpSession session = request.getSession();
            Object obj = session.getAttribute(SessionConstants.SESSION_PERMISSACT);
            if (obj == null) {
                redirectLogin(request, response);
                return SKIP_BODY;
            }
            List<SystemMenu> menus = (List<SystemMenu>) obj;
            SystemMenu menu = getMenuByUrl(menus, requestUri);
            if (menu == null) {
                // redirectLogin(request, response);
                return SKIP_BODY;
            }
            if (menu.getSystemMenuActs() != null) {
                for (SystemMenuAct systemMenuAct : menu.getSystemMenuActs()) {
                    if (systemMenuAct.getActCode() != null && systemMenuAct.getActCode().equals(optCode)) {
                        hasPermission = true;
                        break;
                    }
                }
            } else {
                hasPermission = false;
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

		/*
		 * 如果授权成功则显示标签所包含的内容,否则跳过标签所包含的内容.
		 */
        if (hasPermission) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }

    private void redirectLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private SystemMenu getMenuByUrl(List<SystemMenu> menus, String url) {
        SystemMenu info = null;
        for (SystemMenu systemMenu : menus) {
            if (systemMenu.getMenuUrl() != null && (systemMenu.getMenuUrl().equals(url)
                    || systemMenu.getMenuUrl().indexOf("?") == -1 && url.indexOf("?") != -1
                    && systemMenu.getMenuUrl().equals(url.substring(0, url.indexOf("?"))))) {
                info = systemMenu;
                return info;
            } else if (systemMenu.getChildren().size() > 0) {
                info = getMenuByUrl(systemMenu.getChildren(), url);
            }
            if (info != null) {
                return info;
            }
        }
        return info;

    }
}
