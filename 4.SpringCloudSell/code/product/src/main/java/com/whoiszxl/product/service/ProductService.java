package com.whoiszxl.product.service;

import java.util.List;

import com.whoiszxl.product.dataobject.ProductInfo;

/**
 * 商品服务
 * @author whoiszxl
 *
 */
public interface ProductService {

	/**
	 * 查询所有上架商品
	 * @return
	 */
	List<ProductInfo> findUpAll();
	
	
	List<ProductInfo> findListByProductIdIn(List<String> productIdList);
}
