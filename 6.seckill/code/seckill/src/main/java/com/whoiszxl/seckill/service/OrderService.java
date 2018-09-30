package com.whoiszxl.seckill.service;

import com.whoiszxl.seckill.domain.OrderInfo;
import com.whoiszxl.seckill.domain.SeckillOrder;
import com.whoiszxl.seckill.domain.SeckillUser;
import com.whoiszxl.seckill.vo.GoodsVo;

public interface OrderService {

	SeckillOrder getSeckillOrderByUserIdGoodsId(long userId, long goodsId);
	
	public OrderInfo createOrder(SeckillUser user, GoodsVo goods);
}
