package com.lw.security.core.properties;

import lombok.Data;

@Data
public class BrowserProperties {

    /**
     * 登陆页面
     */
    private String loginPage;

    private LoginResponseType loginType = LoginResponseType.JSON;
}
