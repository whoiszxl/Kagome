package com.whoiszxl.seckill.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SeckillOrder {
	private Long id;
	private Long userId;
	private Long  orderId;
	private Long goodsId;
	
}
