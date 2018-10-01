package com.whoiszxl.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoiszxl.product.dataobject.ProductCategory;
import com.whoiszxl.product.repository.ProductCategoryRepository;
import com.whoiszxl.product.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	
	@Override
	public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
		
		return productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
	}

}
