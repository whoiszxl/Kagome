package com.whoiszxl.product.enums;

import lombok.Getter;

/**
 * 商品上下架状态
 * @author whoiszxl
 *
 */
@Getter
public enum ResultStatusEnum {
    SUCCESS(0, "成功"),
    FAIL(1, "失败"),
    ;

    private Integer code;

    private String message;

    ResultStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}