package com.whoiszxl.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whoiszxl.product.dataobject.ProductCategory;

/**
 * 商品分类数据库操作层
 * @author whoiszxl
 *
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

	/**
	 * 通过类目编号批量获取商品分类
	 * @param categoryTypeList
	 * @return
	 */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}

