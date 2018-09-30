package com.whoiszxl.seckill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoiszxl.seckill.domain.OrderInfo;
import com.whoiszxl.seckill.domain.SeckillUser;
import com.whoiszxl.seckill.service.GoodsService;
import com.whoiszxl.seckill.service.OrderService;
import com.whoiszxl.seckill.service.SeckillService;
import com.whoiszxl.seckill.vo.GoodsVo;

@Service
public class SeckillServiceImpl implements SeckillService {
	
	@Autowired
	private GoodsService goodsService;

	@Autowired
	private OrderService orderService;
	
	@Override
	public OrderInfo seckill(SeckillUser user, GoodsVo goods) {
		//扣减库存 -> 下订单 -> 写入秒杀订单
		goodsService.reduceStock(goods);
		return orderService.createOrder(user, goods);
	}

}
