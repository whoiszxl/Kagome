package com.whoiszxl.product.service;

import java.util.List;

import com.whoiszxl.product.dataobject.ProductCategory;

/**
 * 分类服务
 * @author whoiszxl
 *
 */
public interface CategoryService {

	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
