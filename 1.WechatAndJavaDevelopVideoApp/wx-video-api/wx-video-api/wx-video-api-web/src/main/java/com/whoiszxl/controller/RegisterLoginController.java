package com.whoiszxl.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.pojo.Users;
import com.whoiszxl.service.UserService;
import com.whoiszxl.utils.JSONResult;
import com.whoiszxl.utils.MD5Utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "用户注册登录接口", tags = { "注册和登录的controller" })
public class RegisterLoginController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "用户注册", notes = "用户注册接口")
	@PostMapping("/register")
	public JSONResult<?> register(@RequestBody Users user) throws Exception {
		// 1. 校验用户名密码有效性
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
			return JSONResult.errorMsg("用户名密码不能为空");
		}
		// 2. 校验用户名是否存在
		boolean userNameIsExist = userService.queryUsernameIsExist(user.getUsername());
		if (userNameIsExist) {
			return JSONResult.errorMsg("用户名已存在");
		}
		// 3. 保存用户
		user.setNickname(user.getUsername());
		user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
		user.setFansCounts(0);
		user.setReceiveLikeCounts(0);
		user.setFollowCounts(0);
		userService.saveUser(user);

		user.setPassword("");
		return JSONResult.ok(user);
	}

	@ApiOperation(value = "用户登录", notes = "用户登录接口")
	@PostMapping("/login")
	public JSONResult<?> login(@RequestBody Users user) throws Exception {
		// 1. 校验用户名密码有效性
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
			return JSONResult.errorMsg("用户名密码不能为空");
		}
		// 2. 通过用户名和密码查询用户是否存在
		Users result = userService.queryUserByUsernameAndMd5Pwd(user.getUsername(), MD5Utils.getMD5Str(user.getPassword()));
		if(result != null) {
			return JSONResult.ok(result);
		}else {
			return JSONResult.errorMsg("用户名或密码不正确");
		}
	}
}
