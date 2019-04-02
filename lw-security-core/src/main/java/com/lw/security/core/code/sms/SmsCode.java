package com.lw.security.core.code.sms;

import com.lw.security.core.code.common.BaseCode;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 短信验证码实体类
 */
@Data
public class SmsCode extends BaseCode{

	
	public SmsCode(String code, int expireIn){
		super(code, expireIn);
	}
	
	public SmsCode(String code, LocalDateTime expireTime){
		super(code, expireTime);
	}

	/**
	 * 判断是否时间过期
	 * @return
	 */
	@Override
	public boolean isExpried() {
		return LocalDateTime.now().isAfter(getExpireTime());
	}

}
