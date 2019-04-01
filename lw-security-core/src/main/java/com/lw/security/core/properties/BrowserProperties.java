package com.lw.security.core.properties;

import lombok.Data;

@Data
public class BrowserProperties {

    /**
     * 登陆页面
     */
    private String loginPage;

    private LoginResponseType loginType = LoginResponseType.JSON;

    /**
     * 设置记住我功能默认一个小时过期，以秒为单位
     */
    private int rememberMeSeconds = 3600;
}
