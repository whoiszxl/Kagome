package com.whoiszxl.manager.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 * @author whoiszxl
 *
 */
@RestController
@RequestMapping("/login")
public class LoginController {

	
	@GetMapping("/name")
	public Map<String, String> name() {
		//获取当前登录用户的用户名
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Map<String, String> map = new HashMap<>();
		map.put("loginName", name);
		return map;
	}
}
