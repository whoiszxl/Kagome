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
	
	
	/**
	 * 通过用户名和md5加密密码查询用户是否存在
	 * @param username 用户名
	 * @param password md5加密后的密码
	 * @return 查询到的用户
	 */
	public Users queryUserByUsernameAndMd5Pwd(String username, String password);
	
	
	/**
	 * 更新用户信息
	 * @param user 用户实体
	 */
	public boolean updateUserInfo(Users user);
	
	
	/**
	 * 通过用户id查询到用户的个人信息
	 * @param userId 用户id
	 * @return 查询到的用户个人信息
	 */
	public Users queryUserInfo(String userId);
}
