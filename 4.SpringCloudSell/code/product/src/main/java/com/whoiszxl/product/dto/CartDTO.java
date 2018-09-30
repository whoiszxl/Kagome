package com.whoiszxl.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

	/** 商品ID */
	private String productId;
	
	/** 商品数量 */
	private Integer productQuantity;
}
