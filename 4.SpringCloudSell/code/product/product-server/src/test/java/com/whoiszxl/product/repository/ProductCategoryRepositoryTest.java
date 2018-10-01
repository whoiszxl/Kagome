package com.whoiszxl.product.repository;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.whoiszxl.product.dataobject.ProductCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	
	@Test
	public void testFindByCategoryTypeIn() {
		List<ProductCategory> findByCategoryTypeIn = productCategoryRepository.findByCategoryTypeIn(Arrays.asList(11, 22));
		for (ProductCategory productCategory : findByCategoryTypeIn) {
			System.out.println(productCategory);
		}
	}

}
