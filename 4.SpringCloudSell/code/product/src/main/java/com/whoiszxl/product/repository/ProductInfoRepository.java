package com.whoiszxl.product.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.whoiszxl.product.dataobject.ProductInfo;

/**
 * 商品信息数据库操作层
 * @author whoiszxl
 *
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String>{

	/**
	 * 通过商品状态查询商品信息
	 * @param productStatus
	 * @return
	 */
    List<ProductInfo> findByProductStatus(Integer productStatus);

    /**
     * 通过商品id集合批量查询商品
     * @param productIdList
     * @return
     */
    List<ProductInfo> findByProductIdIn(List<String> productIdList);
}