/**
 * 
 */
package com.lw.security.core.code.image;

import com.lw.security.core.code.common.BaseCode;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;


/**
 * 图形验证码
 */
@Data
public class ImageCode extends BaseCode{

	/**
	 * 图像缓冲对象
	 */
	private BufferedImage image;
	
	public ImageCode(BufferedImage image, String code, int expireIn){
		super(code, expireIn);
		this.image = image;
	}
	
	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime){
		super(code, expireTime);
		this.image = image;
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
