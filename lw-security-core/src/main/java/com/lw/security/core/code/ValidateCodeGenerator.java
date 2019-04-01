/**
 * 
 */
package com.lw.security.core.code;

import com.lw.security.core.code.image.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 定义生成图像验证码的抽象类
 */
public interface ValidateCodeGenerator {

	/**
	 * 生成验证码，由子类实现
	 * @param request
	 * @return
	 */
	ImageCode generate(ServletWebRequest request);
	
}
