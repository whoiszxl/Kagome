package com.whoiszxl.service;

import com.whoiszxl.pojo.Users;

public interface UserService {

	/**
	 * 判断用户名是否存在
	 * @param username 用户名
	 * @return 是否存在
	 */
	public boolean queryUsernameIsExist(String username);
	
	/**
	 * 注册用户到数据库
	 * @param user
	 */
	public void saveUser(Users user);
}
