package com.whoiszxl.seckill.service;

import javax.servlet.http.HttpServletResponse;

import com.whoiszxl.seckill.domain.SeckillUser;
import com.whoiszxl.seckill.vo.LoginVo;

public interface SeckillUserService {

	SeckillUser getById(long id);
	
	public SeckillUser getByToken(HttpServletResponse response, String token);
	
	public boolean login(HttpServletResponse response, LoginVo loginVo);
	
}
