package com.whoiszxl.seckill.vo;

import java.util.Date;

import com.whoiszxl.seckill.domain.Goods;

import lombok.Data;
import lombok.ToString;

public class GoodsVo extends Goods {

	private Double seckillPrice;
	private Integer stockCount;
	private Date startTime;
	private Date endTime;
	public Double getSeckillPrice() {
		return seckillPrice;
	}
	public void setSeckillPrice(Double seckillPrice) {
		this.seckillPrice = seckillPrice;
	}
	public Integer getStockCount() {
		return stockCount;
	}
	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
