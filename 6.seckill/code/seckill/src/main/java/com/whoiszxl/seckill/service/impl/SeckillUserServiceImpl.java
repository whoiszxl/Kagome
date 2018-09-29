package com.whoiszxl.seckill.service.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoiszxl.seckill.constants.Constants;
import com.whoiszxl.seckill.dao.SeckillUserDao;
import com.whoiszxl.seckill.domain.SeckillUser;
import com.whoiszxl.seckill.exception.GlobalException;
import com.whoiszxl.seckill.redis.RedisService;
import com.whoiszxl.seckill.redis.SeckillUserKey;
import com.whoiszxl.seckill.result.CodeMsg;
import com.whoiszxl.seckill.service.SeckillUserService;
import com.whoiszxl.seckill.util.MD5Util;
import com.whoiszxl.seckill.util.UUIDUtil;
import com.whoiszxl.seckill.vo.LoginVo;

@Service
public class SeckillUserServiceImpl implements SeckillUserService {

	@Autowired
	private SeckillUserDao seckillUserDao;

	@Autowired
	private RedisService redisService;

	@Override
	public SeckillUser getById(long id) {
		return seckillUserDao.getSeckillUserById(id);
	}

	@Override
	public SeckillUser getByToken(HttpServletResponse response, String token) {
		if (StringUtils.isEmpty(token)) {
			return null;
		}
		SeckillUser user = redisService.get(SeckillUserKey.token, token, SeckillUser.class);
		// 延长有效期
		if (user != null) {
			addCookie(response, token, user);
		}
		return null;
	}

	@Override
	public boolean login(HttpServletResponse response, LoginVo loginVo) {
		if (loginVo == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		String mobile = loginVo.getMobile();
		String formPass = loginVo.getPassword();

		SeckillUser user = getById(Long.parseLong(mobile));
		if (user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}

		// 验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
		if (!calcPass.equals(dbPass)) {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}
		// 生成cookie
		String token = UUIDUtil.uuid();
		addCookie(response, token, user);
		return true;
	}

	private void addCookie(HttpServletResponse response, String token, SeckillUser user) {
		redisService.set(SeckillUserKey.token, token, user);
		Cookie cookie = new Cookie(Constants.COOKI_NAME_TOKEN, token);
		cookie.setMaxAge(SeckillUserKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}

}
