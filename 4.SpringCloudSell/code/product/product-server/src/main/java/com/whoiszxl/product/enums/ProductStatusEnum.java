package com.whoiszxl.product.enums;

import lombok.Getter;

/**
 * 商品上下架状态
 * @author whoiszxl
 *
 */
@Getter
public enum ProductStatusEnum {
    UP(0, "在架"),
    DOWN(1, "下架"),
    ;

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}