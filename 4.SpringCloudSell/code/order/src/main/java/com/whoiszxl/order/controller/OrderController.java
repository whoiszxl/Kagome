package com.whoiszxl.order.controller;

import org.springframework.web.bind.annotation.RestController;

/**
 * 订单控制器
 * @author whoiszxl
 *
 */
@RestController
public class OrderController {
	
	
    /**
     * 1. 参数检验
     * 2. 查询商品信息(调用商品服务)
     * 3. 计算总价
     * 4. 扣库存(调用商品服务)
     * 5. 订单入库
     */
	public void create() {
		
	}
}
