package com.lw.security.core.code.sms;

import lombok.Data;

import java.time.LocalDateTime;


/**
 * 短信验证码实体类
 */
@Data
public class ValidateCode {

	/**
	 *  手机验证码
	 */
	private String code;

	/**
	 * 设置超时时间
	 */
	private LocalDateTime expireTime;

	
	public ValidateCode(String code, int expireIn){
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}
	
	public ValidateCode(String code, LocalDateTime expireTime){
		this.code = code;
		this.expireTime = expireTime;
	}

	/**
	 * 判断是否时间过期
	 * @return
	 */
	public boolean isExpried() {
		return LocalDateTime.now().isAfter(expireTime);
	}

}
