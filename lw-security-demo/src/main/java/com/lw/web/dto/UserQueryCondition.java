/**
 * 
 */
package com.lw.web.dto;

import lombok.Data;

/**
 * @author zhailiang
 *
 */
@Data
public class UserQueryCondition {
	private String username;

	private String password;

	private Integer age;
}
