package com.whoiszxl.product.utils;

import com.whoiszxl.product.enums.ResultStatusEnum;
import com.whoiszxl.product.vo.ResultVO;

public class ResultVOUtil {

	/**
	 * 构建一个成功的带参数的result
	 * @param object
	 * @return
	 */
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(ResultStatusEnum.SUCCESS.getCode());
        resultVO.setMsg(ResultStatusEnum.SUCCESS.getMessage());
        return resultVO;
    }
}

