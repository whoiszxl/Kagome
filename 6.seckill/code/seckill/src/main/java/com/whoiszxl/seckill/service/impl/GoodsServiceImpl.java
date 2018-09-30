package com.whoiszxl.seckill.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoiszxl.seckill.dao.GoodsDao;
import com.whoiszxl.seckill.domain.SeckillGoods;
import com.whoiszxl.seckill.service.GoodsService;
import com.whoiszxl.seckill.vo.GoodsVo;

@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsDao goodsDao;
	
	@Override
	public List<GoodsVo> listGoodsVo() {
		return goodsDao.getGoodsVoList();
	}

	@Override
	public GoodsVo getGoodsVoByGoodsId(long goodsId) {
		return goodsDao.getGoodsVoByGoodsId(goodsId);
	}

	@Override
	public void reduceStock(GoodsVo goods) {
		goodsDao.reduceStock(goods.getId());
	}

}
