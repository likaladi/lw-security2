/**
 * 
 */
package com.lw.security.core.code.sms;

import com.lw.security.core.code.common.BaseCode;
import com.lw.security.core.code.common.BaseCodeService;
import com.lw.security.core.config.SecurityProperties;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 *  生成短信验证码
 */
@Component
public class SmsCodeServiceImpl implements BaseCodeService {

	/**
	 * 系统配置
	 */
	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public BaseCode generate(ServletWebRequest request) {
		String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
		return new SmsCode(code, securityProperties.getCode().getSms().getExpireIn());
	}

}
