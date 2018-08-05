package com.whoiszxl.service.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.whoiszxl.config.SecretConfig;
import com.whoiszxl.mapper.UsersMapper;
import com.whoiszxl.pojo.Users;
import com.whoiszxl.service.UserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private Sid sid;
	
	@Autowired
	private SecretConfig secretConfig;

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean queryUsernameIsExist(String username) {
		Users user = new Users();
		user.setUsername(username);
		Users result = usersMapper.selectOne(user);
		return result == null ? false : true;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveUser(Users user) {
		String userId = sid.nextShort();
		user.setId(userId);
		usersMapper.insert(user);
	}

	@Override
	public Users queryUserByUsernameAndMd5Pwd(String username, String password) {
		Example userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("username", username);
		criteria.andEqualTo("password", password);
		Users result = usersMapper.selectOneByExample(userExample);
		result.setPassword("");
		return result;
	}

	@Override
	public boolean updateUserInfo(Users user) {
//		Example userExample = new Example(Users.class);
//		Criteria criteria = userExample.createCriteria();
//		criteria.andEqualTo("id", user.getId());
		
		int result = usersMapper.updateByPrimaryKeySelective(user);
		return result > 0;
	}

	@Override
	public Users queryUserInfo(String userId) {
		Users user = usersMapper.selectByPrimaryKey(userId);
		if(user.getFaceImage() != null) {
			user.setFaceImage(secretConfig.getQiniuHttpBase() + user.getFaceImage());
		}
		return user;
	}

}
