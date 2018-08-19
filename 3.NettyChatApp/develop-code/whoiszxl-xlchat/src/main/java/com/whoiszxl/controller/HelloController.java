package com.whoiszxl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试controller
 * @author Administrator
 *
 */
@RestController
public class HelloController {
	
	@GetMapping("/hello")
	public String hello() {
		return "hello sb2.0";
	}

}
