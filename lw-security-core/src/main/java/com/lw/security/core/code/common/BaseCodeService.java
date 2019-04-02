package com.lw.security.core.code.common;

import com.lw.security.core.code.common.BaseCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface BaseCodeService {

    /**
     * 创建验证码
     * @param request
     * @return
     */
    BaseCode generate(ServletWebRequest request);
}
