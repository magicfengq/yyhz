package com.yyhz.sc.controller.back;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yyhz.utils.ValidateCode;

@Controller
public class AuthImgAction {

	private static final HttpHeaders HTTP_HEADERS;

	public final static Log log = LogFactory.getLog(AuthImgAction.class);

	static {
		HTTP_HEADERS = new HttpHeaders();
		HTTP_HEADERS.set("Pragma", "No-cache");
		HTTP_HEADERS.set("Cache-Control", "No-cache");
		HTTP_HEADERS.setExpires(0);
		HTTP_HEADERS.setContentType(MediaType.IMAGE_JPEG);
	}

	/**
	 * 进入系统的登陆页面
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getAuthImg", method = { RequestMethod.GET, RequestMethod.POST })
	public void getAuthImg(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
			response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expire", 0);
			log.info("AuthImgAction ----> getAuthImg");
			ValidateCode mValidateCode = new ValidateCode();
			String code = mValidateCode.getCode();
			BufferedImage img = mValidateCode.getImage();
			request.getSession().setAttribute("LoginAuthCode", code);
			ImageIO.write(img, "JPEG", response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
