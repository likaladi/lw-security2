/**
 * 
 */
package com.lw.security.core.code.image;

import com.lw.security.core.code.sms.ValidateCode;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;


/**
 * 图形验证码
 */
@Data
public class ImageCode extends ValidateCode {
	
	private BufferedImage image;
	
	public ImageCode(BufferedImage image, String code, int expireIn){
		super(code, expireIn);
		this.image = image;
	}
	
	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime){
		super(code, expireTime);
		this.image = image;
	}

}
