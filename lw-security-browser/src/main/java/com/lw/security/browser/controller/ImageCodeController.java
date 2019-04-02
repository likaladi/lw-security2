/**
 * 
 */
package com.lw.security.browser.controller;

import com.lw.security.core.code.common.BaseCodeService;
import com.lw.security.core.code.image.ImageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author zhailiang
 *
 */
@RestController
public class ImageCodeController {

	public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

	//操作session
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	@Autowired
	private BaseCodeService imageCodeService;

	/**
	 * 创建验证码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/code/image")
	public void createCode(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ImageCode imageCode = (ImageCode)imageCodeService.generate(new ServletWebRequest(request));
		//设置图形验证码到session
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
		ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
	}

}
