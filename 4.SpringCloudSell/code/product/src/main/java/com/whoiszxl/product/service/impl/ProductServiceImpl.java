package com.whoiszxl.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoiszxl.product.dataobject.ProductInfo;
import com.whoiszxl.product.enums.ProductStatusEnum;
import com.whoiszxl.product.repository.ProductInfoRepository;
import com.whoiszxl.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductInfoRepository productInfoRepository;
	
	@Override
	public List<ProductInfo> findUpAll() {
		return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
	}

}
