/**
 * 
 */
package com.lw.security.browser.securityconfig;

import com.lw.security.browser.propetiesconfig.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 配置security
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

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
		//开放拦截自定义页面
		String loginPage = securityProperties.getBrowser().getLoginPage();
//		http.httpBasic() //httpBasic 浏览器与服务器做认证授权
		http.formLogin() //使用fromLogin 表单提交认证模式
				.loginPage("/authentication/require") //拦截所有请求地址，先执行自定义接口操作
				.loginProcessingUrl("/authentication/form") //指定登录的接口使用Security内部的用户名密码过滤器处理
				.and()
				.authorizeRequests()
				//开放/authentication/require接口和demo-signIn.html页面，不进行拦截
				.antMatchers("/authentication/require", loginPage).permitAll()
				//.anyRequest().authenticated()表示其他的所有请求都必须认证
				.anyRequest()
				.authenticated()
				.and()
				.csrf().disable(); //禁用csrf
	}
	
}
