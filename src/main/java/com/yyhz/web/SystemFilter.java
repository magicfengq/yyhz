package com.yyhz.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;

import com.yyhz.constant.SessionConstants;
import com.yyhz.sc.data.model.SystemUserInfo;

/**
 * @author yangml
 * @ClassName: CurrentContextFilter
 * @Description: CurrentContextFilter用于缓存用户请求过程中的HttpServletRequest和HttpServletResponse对象
 *               .
 * @date 2014-12-3 上午9:51:02
 */
public class SystemFilter implements Filter {

	private String[] strs = { "login", "getAuthImg", "welcome", "initSystemMenuTree", "rest/" };

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
			throw new ServletException("OncePerRequestFilter just supports HTTP requests");
		}
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		HttpSession session = httpRequest.getSession(true);

		// SystemUserInfo customUser = null;
		Object object = session.getAttribute(SessionConstants.SESSION_USER);
		SystemUserInfo customUser = object == null ? null : (SystemUserInfo) object;
		// if (session.getAttribute(SessionConstants.SESSION_BACK_USER_FLAG) !=
		// null) {
		// }

		String url = httpRequest.getRequestURI();
		// 如果是后台访问
		if (url.indexOf("/system/") > 0 || url.indexOf("main.do") > 0) {
			if (customUser == null) {
				boolean isAjaxRequest = isAjaxRequest(httpRequest);
				if (isAjaxRequest) {
					httpResponse.setCharacterEncoding("UTF-8");
					httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "您已经太长时间没有操作,请刷新页面");
				}
				redirect(httpRequest, httpResponse);
				return;
			}
		}

		if (url.indexOf("_if") >= 0) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
		if (url.indexOf("_view") >= 0) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
		if (strs != null && strs.length > 0) {
			for (String str : strs) {
				if (url.indexOf(str) >= 0) {
					filterChain.doFilter(servletRequest, servletResponse);
					return;
				}
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
		return;
	}

	/**
	 * 判断是否为Ajax请求
	 *
	 * @param request
	 *            HttpServletRequest
	 * @return 是true, 否false
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/api");
		// String requestType = request.getHeader("X-Requested-With");
		// return requestType != null && requestType.equals("XMLHttpRequest");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	private void redirect(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		try {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp");
		} catch (Exception e) {
		}

	}
}
