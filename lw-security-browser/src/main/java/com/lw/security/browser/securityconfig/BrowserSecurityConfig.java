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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * 配置security
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	//注入实现该接口的对象，这里是LwAuthenticationSuccessHandler
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	//注入实现该接口的对象，这里是LwAuthenctiationFailureHandler
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	//注入数据库数据源
	@Autowired
	private DataSource dataSource;

	//注入实现该接口的对象，这里是MyUserDetailsService
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * 注入密码加密
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	//初始化存储token的数据表
	@Bean
	public PersistentTokenRepository persistentTokenRepository(){
		//从JdbcTokenRepositoryImpl源码中拷贝创建表persistent_logins语句在数据库中执行
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		//启动时创建表
		//tokenRepository.setCreateTableOnStartup(true);
		return tokenRepository;
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
				//这里是"记住我"的功能
				.rememberMe()
				.tokenRepository(persistentTokenRepository())
				//设置token有效时间
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
				.userDetailsService(userDetailsService)
				//"记住我"功能配置结束
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
