package com.whoiszxl.seckill.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderInfo {
	
	private Long id;
	private Long userId;
	private Long goodsId;
	private Long  deliveryAddrId;
	private String goodsName;
	private Integer goodsCount;
	private Double goodsPrice;
	private Integer orderChannel;
	private Integer status;
	private Date createDate;
	private Date payDate;
	
}
