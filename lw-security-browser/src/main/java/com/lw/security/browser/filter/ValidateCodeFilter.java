/**
 * 
 */
package com.lw.security.browser.filter;

import com.lw.security.browser.controller.ValidateCodeController;
import com.lw.security.browser.exception.ValidateCodeException;
import com.lw.security.core.code.image.ImageCode;
import com.lw.security.core.code.sms.ValidateCode;
import com.lw.security.core.config.SecurityProperties;
import com.lw.security.core.properties.SecurityConstants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 * 验证码过滤器,可以注入
 *
 */
@Slf4j
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

	/**
	 * 验证码校验失败处理器
	 */
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	private AntPathMatcher pathMatcher = new AntPathMatcher();

	//操作session
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	//将配置的url存放到集合urls中
	private Set<String> urls = new HashSet<>();

	/**
	 * 初始化要拦截的url配置信息
	 */
	@Override
	public void afterPropertiesSet() throws ServletException {
		log.info("-----执行ValidateCodeFilter的afterPropertiesSet方法---------");
		super.afterPropertiesSet();
//		String imgUrls = securityProperties.getCode().getImage().getUrl();
//		if(StringUtils.isNotBlank(imgUrls)){
//			String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(imgUrls, ",");
//			for (String configUrl : configUrls){
//				urls.add(configUrl);
//			}
//		}
		urls.add(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE);
		urls.add(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM);

	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		log.info("---------进入图形验证码过滤器--------");
		log.info("请求地址："+ request.getRequestURI());

		boolean action = false;
		for(String url : urls){
			if(pathMatcher.match(url, request.getRequestURI())){
				action = true;
				break;
			}
		}

		//访问地址：http://localhost:8060/authentication/require，requestURI:/authentication/require
		//获取请求的方式:req.getMethod() 返回GET/POST
		if(action && StringUtils.equalsIgnoreCase(request.getMethod(), "post")){
			try{
				validate(new ServletWebRequest(request));
			}catch (ValidateCodeException e){
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
		}

		chain.doFilter(request, response);

	}

	private void validate(ServletWebRequest request) throws ServletRequestBindingException{

		ValidateCode validateCode = null;
		String sessionKey = null;
		String codeInRequest = null;
		if(request.getRequest().getRequestURI().equals(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)){
			sessionKey = SecurityConstants.SESSION_KEY_IMAGE_CODE;
			validateCode = (ValidateCode)sessionStrategy.getAttribute(request, SecurityConstants.SESSION_KEY_IMAGE_CODE);
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
		}
		if(request.getRequest().getRequestURI().equals(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE)){
			sessionKey = SecurityConstants.SESSION_KEY_SMS_CODE;
			validateCode = (ValidateCode)sessionStrategy.getAttribute(request, SecurityConstants.SESSION_KEY_SMS_CODE);
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");
		}

		if(StringUtils.isBlank(codeInRequest)){
			throw  new ValidateCodeException("验证码的值不能为空");
		}

		if(validateCode == null){
			throw new ValidateCodeException("验证码不存在");
		}

		if(validateCode.isExpried()){
			sessionStrategy.removeAttribute(request, sessionKey);
			throw new ValidateCodeException("验证码已过期");
		}

		if(!StringUtils.equals(validateCode.getCode(), codeInRequest)){
			throw new ValidateCodeException("验证码不匹配");
		}

		//验证成功后，删除session存储的验证码
		sessionStrategy.removeAttribute(request, sessionKey);
	}

}
