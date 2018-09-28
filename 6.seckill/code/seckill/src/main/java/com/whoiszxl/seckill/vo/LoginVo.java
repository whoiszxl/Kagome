package com.whoiszxl.seckill.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.whoiszxl.seckill.validator.IsMobile;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class LoginVo {
	
	@NotNull
	@IsMobile
	private String mobile;
	
	@NotNull
	@Length(min=32)
	private String password;
	
}
