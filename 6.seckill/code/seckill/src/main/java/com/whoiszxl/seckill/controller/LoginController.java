package com.whoiszxl.seckill.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whoiszxl.seckill.redis.RedisService;
import com.whoiszxl.seckill.result.Result;
import com.whoiszxl.seckill.service.SeckillUserService;
import com.whoiszxl.seckill.vo.LoginVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 登陆控制器
 * 
 * @author whoiszxl
 *
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

	@Autowired
	private SeckillUserService seckillUserService;

	@Autowired
	private RedisService redisService;

	@RequestMapping("/to_login")
	public String toLogin() {
		return "login";
	}
	
	
	

	@RequestMapping("/do_login")
	@ResponseBody
	public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
		log.info(loginVo.toString());
		// 登录
		seckillUserService.login(response, loginVo);
		return Result.success(true);
	}
}