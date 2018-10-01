package com.whoiszxl.product.common;

import lombok.Data;

/**
 * 减库存入参
 * @author whoiszxl
 *
 */
@Data
public class DecreaseStockInput {

    private String productId;

    private Integer productQuantity;

    public DecreaseStockInput() {
    }

    public DecreaseStockInput(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
