package com.lw.security.core.properties;

import lombok.Data;

/**
 * @author zhailiang
 *
 */
@Data
public class ImageCodeProperties {

	private int width = 67;

	private int height = 23;

	private int length = 6;

	private int expireIn = 60;

	private String url;

}
