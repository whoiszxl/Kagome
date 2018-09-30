package com.whoiszxl.seckill.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SeckillGoods {
	private Long id;
	private Long goodsId;
	private Integer stockCount;
	private Date startDate;
	private Date endDate;
}
