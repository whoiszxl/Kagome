package com.whoiszxl.seckill.service;

import com.whoiszxl.seckill.domain.OrderInfo;
import com.whoiszxl.seckill.domain.SeckillUser;
import com.whoiszxl.seckill.vo.GoodsVo;

public interface SeckillService {

	public OrderInfo seckill(SeckillUser user, GoodsVo goods);
}
