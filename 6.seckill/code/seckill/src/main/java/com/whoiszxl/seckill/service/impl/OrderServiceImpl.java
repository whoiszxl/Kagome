package com.whoiszxl.seckill.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoiszxl.seckill.dao.OrderDao;
import com.whoiszxl.seckill.domain.OrderInfo;
import com.whoiszxl.seckill.domain.SeckillOrder;
import com.whoiszxl.seckill.domain.SeckillUser;
import com.whoiszxl.seckill.service.OrderService;
import com.whoiszxl.seckill.vo.GoodsVo;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	
	public SeckillOrder getSeckillOrderByUserIdGoodsId(long userId, long goodsId) {
		return orderDao.getSeckillOrderByUserIdAndGoodsId(userId, goodsId);
	}
	
	@Override
	public OrderInfo createOrder(SeckillUser user, GoodsVo goods) {
		//添加记录到orderInfo
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getSeckillPrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(user.getId());
		long orderId = orderDao.insert(orderInfo);
		
		//添加记录到秒杀订单中去
		SeckillOrder seckillOrder = new SeckillOrder();
		seckillOrder.setGoodsId(goods.getId());
		seckillOrder.setOrderId(orderId);
		seckillOrder.setUserId(user.getId());
		orderDao.insertSeckillOrder(seckillOrder);
		return orderInfo;
	}

}
