/**
 * 
 */
package com.lw.dto;

import io.swagger.annotations.ApiModelProperty;
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
