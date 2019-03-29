/**
 * 
 */
package com.lw.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @author liwen
 *
 */
@Data
public class User {

	/**
	 *  定义controller中返回用户信息不显示密码
	 */
	public interface UserSimpleView {};

	/**
	 *  定义controller中返回所用用户信息（包括密码）
	 */
	public interface UserDetailView extends UserSimpleView {};

	@JsonView(UserSimpleView.class)
	private String username;

	@JsonView(UserDetailView.class)
	private String password;

	@JsonView(UserSimpleView.class)
	private Integer age;

	/**
	 *  前台参数传递时间戳，后台返回前台也是时间戳，保持一致
	 */
	@Past(message = "生日必须是过去的时间")
	@JsonView(UserSimpleView.class)
	private Date birthday;

}
