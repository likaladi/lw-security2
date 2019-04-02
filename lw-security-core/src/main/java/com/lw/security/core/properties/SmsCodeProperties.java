/**
 * 
 */
package com.lw.security.core.properties;

import lombok.Data;

/**
 * 手机验证对象信息
 */
@Data
public class SmsCodeProperties {

	/**
	 * 验证码长度
	 */
	private int length = 6;
	/**
	 * 设置手机验证吗过期时间60秒
	 */
	private int expireIn = 60;

	/**
	 * 配置对应的接口：表示访问配置的请求的接口 要进行手机验证码校验
	 * 多个接口之间用逗号分割，支持/user/* 的访问路径
	 */
	private String url;

}
