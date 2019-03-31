/**
 * 
 */
package com.lw.web.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.lw.web.dto.UserQueryCondition;
import com.lw.web.entity.User;
import com.lw.web.error.UserNotExistException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author zhailiang
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	/**
	 *
	 * @param condition 接收查询条件
	 * @param pageable 接收分页参数
	 * @return
	 */
	@GetMapping
	@ApiOperation(value = "用户查询服务")
	@JsonView(User.UserSimpleView.class) //不显示密码
	public List<User> query(UserQueryCondition condition,
							@PageableDefault(page = 2, size = 17, sort = "username,asc") Pageable pageable) {

		System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));

		System.out.println(pageable.getPageSize());
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getSort());

		List<User> users = new ArrayList<>();
		for(int i=1;i<=3;i++){
			User user = new User();
			user.setUsername("黎闻"+i);
			user.setPassword("likaladi"+i);
			user.setAge(Integer.parseInt(2+""+i));
			users.add(user);
		}

		return users;
	}

	@GetMapping("/{id:\\d+}")  //使用正则表达式强制输入数字才能访问
	@JsonView(User.UserDetailView.class) //显示所有用户信息
	public User getInfo(@ApiParam("用户id") @PathVariable String id) {
//		throw new RuntimeException("user not exist");
		System.out.println("进入getInfo服务");
		User user = new User();
		user.setPassword("33");
		user.setUsername("tom");
		return user;
	}


	/**
	 * 参数加上@RequestBody注解表示前端必须以json格式传递参数
	 * @param user
	 * @return
	 */
	@PostMapping
		@ApiOperation(value = "创建用户")
		public User create(@Valid @RequestBody User user) {

			System.out.println(user.getUsername());
			System.out.println(user.getPassword());
			System.out.println(user.getBirthday());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(sdf.format(user.getBirthday()));
			return user;
	}

	public static void main(String[] args) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse("1989-04-07 00:00:00");
		System.out.println(date.getTime());
		System.out.println(new Date().getTime());
	}

	@PutMapping("/{id:\\d+}")
	public User update(@Valid @RequestBody User user) {

		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getBirthday());

		return user;
	}

	@DeleteMapping("/{id:\\d+}")
	public void delete(@PathVariable String id) {
//		throw new UserNotExistException("xxx");
	}
}
