package com.whoiszxl.product.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.whoiszxl.product.dataobject.ProductInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

	@Autowired
	private ProductInfoRepository productInfoRepository;
	
	@Test
	public void testFindByProductStatus() {
		List<ProductInfo> findByProductStatus = productInfoRepository.findByProductStatus(0);
		for (ProductInfo productInfo : findByProductStatus) {
			System.out.println(productInfo);
		}
	}

	@Test
	public void testFindByProductIdIn() {
		List<ProductInfo> findByProductIdIn = productInfoRepository.findByProductIdIn(Arrays.asList("157875196366160022","157875227953464068"));
		for (ProductInfo productInfo : findByProductIdIn) {
			System.out.println(productInfo);
		}
	}
	

}
