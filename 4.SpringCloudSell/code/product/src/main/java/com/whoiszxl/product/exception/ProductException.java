package com.whoiszxl.product.exception;

import com.whoiszxl.product.enums.ResultEnum;

/**
 * 商品异常
 * @author whoiszxl
 *
 */
public class ProductException extends RuntimeException {

    private Integer code;

    public ProductException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ProductException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

}