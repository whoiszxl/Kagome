package com.whoiszxl.product.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.whoiszxl.product.common.DecreaseStockInput;
import com.whoiszxl.product.common.ProductInfoOutput;


@FeignClient(name = "product")
public interface ProductClient {

	
	@PostMapping("/product/listForOrder")
	List<ProductInfoOutput> getProductListByIds(List<String> productIdList);
	
	@PostMapping("/product/decreaseStock")
	void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList);
	
}