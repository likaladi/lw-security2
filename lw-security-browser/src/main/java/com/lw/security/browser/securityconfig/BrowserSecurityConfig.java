/**
 * 
 */
package com.lw.security.browser.securityconfig;

import com.lw.security.browser.filter.ValidateCodeFilter;
import com.lw.security.core.config.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 配置security
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	/**
	 * 注入密码加密
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//自定义验证码filter
		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
		validateCodeFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
		validateCodeFilter.setSecurityProperties(securityProperties);
		validateCodeFilter.afterPropertiesSet();

		//开放拦截自定义页面
		String loginPage = securityProperties.getBrowser().getLoginPage();
//		http.httpBasic() //httpBasic 浏览器与服务器做认证授权
		//将自定义验证码filter加在用户名密码过滤器之前
		http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
				.formLogin() //使用fromLogin 表单提交认证模式
				.loginPage("/authentication/require") //拦截所有请求地址，先执行自定义接口操作
				.loginProcessingUrl("/authentication/form") //指定登录的接口使用Security内部的用户名密码过滤器处理
				.successHandler(authenticationSuccessHandler) //登陆成功处理LwAuthenticationSuccessHandler
				.failureHandler(authenticationFailureHandler) //登陆失败处理LwAuthenctiationFailureHandler
				.and()
				.authorizeRequests()
				//开放/authentication/require接口和demo-signIn.html页面及验证码接口，不进行拦截
				.antMatchers("/authentication/require", loginPage,"/code/image").permitAll()
				//.anyRequest().authenticated()表示其他的所有请求都必须认证
				.anyRequest()
				.authenticated()
				.and()
				.csrf().disable(); //禁用csrf
	}
	
}
