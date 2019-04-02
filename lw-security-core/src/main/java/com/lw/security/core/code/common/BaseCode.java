package com.lw.security.core.code.common;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 抽出验证码公用类
 */
@Data
public abstract class BaseCode {

    /**
     *  手机验证码
     */
    private String code;

    /**
     * 设置超时时间
     */
    private LocalDateTime expireTime;

    public BaseCode(String code, int expireIn){
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public BaseCode(String code, LocalDateTime expireTime){
        this.code = code;
        this.expireTime = expireTime;
    }

    /**
     * 判断时间是否过期，由子类实现
     * @return
     */
    public abstract boolean isExpried();
}
