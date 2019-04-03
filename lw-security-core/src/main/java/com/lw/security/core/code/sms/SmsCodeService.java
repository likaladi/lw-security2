/**
 * 
 */
package com.lw.security.core.code.sms;

import com.lw.security.core.config.SecurityProperties;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 *  生成短信验证码
 */
@Component
public class SmsCodeService {

	/**
	 * 系统配置
	 */
	@Autowired
	private SecurityProperties securityProperties;

	public ValidateCode generate(ServletWebRequest request) {
		String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
		return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
	}

	/**
	 * 发送手机验证码，待实现
	 * @param mobile
	 * @param code
	 */
	public void send(String mobile, String code){
		System.out.println("手机号："+mobile+"接收验证码："+code);
	}

}
