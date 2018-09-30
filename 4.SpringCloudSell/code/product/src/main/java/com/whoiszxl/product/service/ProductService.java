package com.whoiszxl.product.service;

import java.util.List;

import com.whoiszxl.product.dataobject.ProductInfo;
import com.whoiszxl.product.dto.CartDTO;

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
	
	
	/**
	 * 通过商品id查询商品列表
	 * @param productIdList 商品ids
	 * @return 商品列表
	 */
	List<ProductInfo> findListByProductIdIn(List<String> productIdList);
	
	
	/**
	 * 扣减多个商品的库存
	 * @param cartDTOList
	 */
	void decreaseStock(List<CartDTO> cartDTOList);
	
}
