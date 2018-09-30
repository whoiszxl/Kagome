package com.whoiszxl.order.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.whoiszxl.order.dataobject.ProductInfo;

@FeignClient(name = "product")
public interface ProductClient {

	@GetMapping("/msg")
	String productMsg();
	
	@PostMapping("/product/listForOrder")
	List<ProductInfo> getProductListByIds(List<String> productIdList);
}
