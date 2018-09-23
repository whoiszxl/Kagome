package com.whoiszxl.product.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.whoiszxl.product.dataobject.ProductInfo;
import com.whoiszxl.product.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

	@Autowired
	private ProductService productService;
	
	@Test
	public void testFindUpAll() {
		List<ProductInfo> findUpAll = productService.findUpAll();
		for (ProductInfo productInfo : findUpAll) {
			System.out.println(productInfo);
		}
	}

}
