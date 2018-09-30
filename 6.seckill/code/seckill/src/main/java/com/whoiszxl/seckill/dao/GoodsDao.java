package com.whoiszxl.seckill.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.whoiszxl.seckill.domain.SeckillGoods;
import com.whoiszxl.seckill.vo.GoodsVo;

@Mapper
public interface GoodsDao {

	@Select("select g.*, mg.stock_count, mg.start_time, mg.end_time, mg.seckill_price from seckill_goods mg left join goods g on mg.goods_id = g.id")
	public List<GoodsVo> getGoodsVoList();
	
	@Select("select g.*,mg.stock_count, mg.start_time, mg.end_time,mg.seckill_price from seckill_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
	public GoodsVo getGoodsVoByGoodsId(@Param("goodsId")long goodsId);
	
	@Update("update seckill_goods set stock_count = stock_count - 1 where goods_id = #{goodsId}")
	public int reduceStock(@Param("goodsId")long goodsId);
	
}
