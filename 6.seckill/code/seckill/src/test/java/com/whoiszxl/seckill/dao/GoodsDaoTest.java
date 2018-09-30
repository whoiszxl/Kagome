package com.whoiszxl.seckill.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.whoiszxl.seckill.SeckillApplicationTests;
import com.whoiszxl.seckill.vo.GoodsVo;

public class GoodsDaoTest extends SeckillApplicationTests {

	@Autowired
	private GoodsDao goodsDao;
	
	@Test
	public void testGetGoodsVoList() {
		List<GoodsVo> goodsVoList = goodsDao.getGoodsVoList();
		System.out.println(goodsVoList);
	}

	@Test
	public void testGetGoodsVoByGoodsId() {
		GoodsVo goodsVoByGoodsId = goodsDao.getGoodsVoByGoodsId(1L);
		System.out.println(goodsVoByGoodsId);
	}

	@Test
	public void testReduceStock() {
		int reduceStock = goodsDao.reduceStock(1L);
		System.out.println("削减库存："+reduceStock);
	}

}
