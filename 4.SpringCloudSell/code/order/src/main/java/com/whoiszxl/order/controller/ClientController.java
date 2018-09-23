package com.whoiszxl.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * 远程通信测试（客户端）
 * @author Administrator
 *
 */
@RestController
@Slf4j
public class ClientController {

	
	@GetMapping("/getProductMsg")
	public String getProductMsg() {
		//1.第一种方式
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject("http://localhost:8080/msg", String.class);
		log.info("response={}", response);
		return response;
	}
}
