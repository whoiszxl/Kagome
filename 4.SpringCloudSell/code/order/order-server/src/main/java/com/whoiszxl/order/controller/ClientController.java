package com.whoiszxl.order.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.whoiszxl.product.client.ProductClient;
import com.whoiszxl.product.common.DecreaseStockInput;
import com.whoiszxl.product.common.ProductInfoOutput;

import lombok.extern.slf4j.Slf4j;

/**
 * 远程通信测试（客户端）
 * @author Administrator
 *
 */
//@RestController
@Slf4j
public class ClientController {

	@Autowired
	private LoadBalancerClient loadBalancerClient;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ProductClient productClient;
	
	@GetMapping("/getProductMsg")
	public String getProductMsg() {
		//1.第一种方式（写死url）
//		RestTemplate restTemplate = new RestTemplate();
//		String response = restTemplate.getForObject("http://localhost:8080/msg", String.class);
		
		//2.第二种方式（通过应用名）
//		ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
//		String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort() + "/msg");
//		RestTemplate restTemplate = new RestTemplate();
//		String response = restTemplate.getForObject(url, String.class);

		
		//3.第三种方式（注入RestTemplate）
		String response = restTemplate.getForObject("http://PRODUCT/msg", String.class);
		
		log.info("response={}", response);
		return response;
	}
	
	@GetMapping("/getProductMsgByFeign")
	public String getProductMsgByFeign() {
//		String response = productClient.productMsg();
//		log.info("feign请求：response={}", response);
//		return response;
		return "test";
	}
	
	@GetMapping("/products")
	public List<ProductInfoOutput> getProductListByIds() {
		return productClient.getProductListByIds(Arrays.asList("157875196366160022","157875227953464068"));
	}
	
	@GetMapping("/decreaseProduct")
	public void decrease() {
		DecreaseStockInput decreaseStockInput = new DecreaseStockInput("157875196366160022", 10);
		productClient.decreaseStock(Arrays.asList(decreaseStockInput));
	}
}
