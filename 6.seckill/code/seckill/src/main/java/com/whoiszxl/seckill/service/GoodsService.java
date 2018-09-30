package com.whoiszxl.seckill.service;

import java.util.List;

import com.whoiszxl.seckill.vo.GoodsVo;

public interface GoodsService {

	List<GoodsVo> listGoodsVo();
	
	GoodsVo getGoodsVoByGoodsId(long goodsId);
	
	void reduceStock(GoodsVo goods);
}
