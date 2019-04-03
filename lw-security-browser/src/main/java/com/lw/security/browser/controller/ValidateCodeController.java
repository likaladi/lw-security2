/**
 * 
 */
package com.lw.security.browser.controller;
import com.lw.security.core.code.image.ImageCode;
import com.lw.security.core.code.image.ImageCodeService;
import com.lw.security.core.code.sms.ValidateCode;
import com.lw.security.core.code.sms.SmsCodeService;
import com.lw.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("code")
public class ValidateCodeController {

	//操作session
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	@Autowired
	private ImageCodeService imageCodeService;

	@Autowired
	private SmsCodeService smsCodeService;

	/**
	 * 创建图形验证码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("image")
	public void createImageCode(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ImageCode imageCode = (ImageCode) imageCodeService.generate(new ServletWebRequest(request));
		//设置图形验证码到session
		sessionStrategy.setAttribute(new ServletWebRequest(request), SecurityConstants.SESSION_KEY_IMAGE_CODE, imageCode);
		ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
	}

	/**
	 * 创建手机验证码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("sms")
	public void createSmsCode(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ValidateCode smsCode = smsCodeService.generate(new ServletWebRequest(request));
		//设置验证码对象
		sessionStrategy.setAttribute(new ServletWebRequest(request), SecurityConstants.SESSION_KEY_SMS_CODE, smsCode);
		//获取请求入参mobile
		String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
		smsCodeService.send(mobile, smsCode.getCode());
	}



}
