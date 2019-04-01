package com.lw.security.browser.controller;

import com.lw.security.browser.response.SimpleResponse;
import com.lw.security.core.config.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
public class BrowserSecurityController {

    //流程：浏览器输入请求地址比如http://localhost:8060/user，不是开发的接口地址会被拦截，执行/authentication/require接口
    //在执行/authentication/require接口之前，会把请求地址http://localhost:8060/user缓存到requestCache中也就是session中
    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 当需要身份认证时，跳转到这里
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String loginPage = securityProperties.getBrowser().getLoginPage();

        log.info("登陆页面："+loginPage);

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if(savedRequest != null){
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求是："+targetUrl);
            if(StringUtils.endsWithIgnoreCase(targetUrl, ".html")){
                redirectStrategy.sendRedirect(request, response, loginPage);
            }
        }
        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
    }

}
