package com.whoiszxl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.pojo.Users;
import com.whoiszxl.service.UserService;
import com.whoiszxl.utils.JSONResult;
import com.whoiszxl.utils.MD5Utils;

@RestController
public class RegisterLoginController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public JSONResult<String> register(@RequestBody Users user) throws Exception {
		//1. 校验用户名密码有效性
		if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
			return JSONResult.errorMsg("用户名密码不能为空");
		}
		//2. 校验用户名是否存在
		boolean userNameIsExist = userService.queryUsernameIsExist(user.getUsername());
		if(userNameIsExist) {
			return JSONResult.errorMsg("用户名已存在");
		}
		//3. 保存用户
		user.setNickname(user.getUsername());
		user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
		user.setFansCounts(0);
		user.setReceiveLikeCounts(0);
		user.setFollowCounts(0);
		userService.saveUser(user);		
		return JSONResult.ok();
	} 
	
}
