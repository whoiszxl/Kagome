package com.whoiszxl.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.whoiszxl.seckill.domain.OrderInfo;
import com.whoiszxl.seckill.domain.SeckillOrder;
import com.whoiszxl.seckill.domain.SeckillUser;
import com.whoiszxl.seckill.redis.RedisService;
import com.whoiszxl.seckill.result.CodeMsg;
import com.whoiszxl.seckill.service.GoodsService;
import com.whoiszxl.seckill.service.OrderService;
import com.whoiszxl.seckill.service.SeckillService;
import com.whoiszxl.seckill.service.SeckillUserService;
import com.whoiszxl.seckill.vo.GoodsVo;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

	@Autowired
	private SeckillUserService seckillUserService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SeckillService seckillService;
	
	
	@RequestMapping("/do_seckill")
	public String seckill(Model model, SeckillUser user, @RequestParam("goodsId")long goodsId) {
		model.addAttribute("user", user);
    	if(user == null) {
    		return "login";
    	}
    	//判断库存
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	int stock = goods.getStockCount();
    	if(stock <= 0) {
    		model.addAttribute("errmsg", CodeMsg.SECKILL_OVER.getMsg());
    		return "seckill_fail";
    	}
    	
    	//判断是否秒杀到了
    	SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
    	if(order != null) {
    		model.addAttribute("errmsg", CodeMsg.REPEATE_SECKILL.getMsg());
    		return "seckill_fail";
    	}
    	
    	//减库存 下订单 写入秒杀订单
    	OrderInfo orderInfo = seckillService.seckill(user, goods);
    	model.addAttribute("orderInfo", orderInfo);
    	model.addAttribute("goods", goods);
        return "order_detail";
	}
}
