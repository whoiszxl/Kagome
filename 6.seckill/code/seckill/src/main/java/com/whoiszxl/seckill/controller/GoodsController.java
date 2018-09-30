package com.whoiszxl.seckill.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whoiszxl.seckill.domain.SeckillUser;
import com.whoiszxl.seckill.enums.SeckillStatusEnums;
import com.whoiszxl.seckill.redis.RedisService;
import com.whoiszxl.seckill.service.GoodsService;
import com.whoiszxl.seckill.service.SeckillUserService;
import com.whoiszxl.seckill.vo.GoodsVo;

/**
 * 商品控制器
 * @author whoiszxl
 *
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	private SeckillUserService seckillUserService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private GoodsService goodsService;

	@GetMapping("/to_list")
	public String list(Model model, SeckillUser user) {
		model.addAttribute("user", user);
		//查询商品列表
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		model.addAttribute("goodsList", goodsList);
		return "goods_list";
	}
	
	@GetMapping("/to_detail/{goodsId}")
	public String detail(Model model, SeckillUser user, @PathVariable("goodsId")long goodsId) {
		model.addAttribute("user", user);
		
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		model.addAttribute("goods", goods);
		
		long startAt = goods.getStartTime().getTime();
    	long endAt = goods.getEndTime().getTime();
    	long now = System.currentTimeMillis();
    	
    	int seckillStatus = 0;
    	int remainSeconds = 0;
    	
    	if(now < startAt) {
    		//秒杀未开始
    		seckillStatus = SeckillStatusEnums.SECKILL_NO_START.getCode();
    		remainSeconds = (int)((startAt - now)/1000);
    	}else if(now > endAt) {
    		//秒杀已结束
    		seckillStatus = SeckillStatusEnums.SECKILL_OVER.getCode();
    		remainSeconds = -1;
    	}else {
    		//秒杀进行时
    		seckillStatus = SeckillStatusEnums.SECKILL_ING.getCode();
    		remainSeconds = 0;
    	}
    	
    	model.addAttribute("seckillStatus", seckillStatus);
    	model.addAttribute("remainSeconds", remainSeconds);
    	
    	return "goods_detail";
	}
	
	
}
