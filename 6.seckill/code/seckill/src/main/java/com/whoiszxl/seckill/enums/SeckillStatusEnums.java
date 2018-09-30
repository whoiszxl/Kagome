package com.whoiszxl.seckill.enums;

public enum SeckillStatusEnums {

	SECKILL_NO_START(0, "秒杀还未开始"),
	SECKILL_OVER(2, "秒杀已经结束"),
	SECKILL_ING(1, "秒杀进行时");
	
	private Integer code;
	private String msg;
	
	private SeckillStatusEnums(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
