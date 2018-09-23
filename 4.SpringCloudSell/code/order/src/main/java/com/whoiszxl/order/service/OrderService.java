package com.whoiszxl.order.service;

import com.whoiszxl.order.dto.OrderDTO;

/**
 * 订单服务
 * @author whoiszxl
 *
 */
public interface OrderService {

	/**
	 * 创建订单
	 * @param orderDTO
	 * @return
	 */
    OrderDTO create(OrderDTO orderDTO);
	
}
