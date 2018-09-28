package com.whoiszxl.seckill.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author whoiszxl
 *
 */
@Data
@ToString
public class SeckillUser {
	
	private Long id;
	private String nickname;
	private String password;
	private String salt;
	private String head;
	private Date registerDate;
	private Date lastLoginDate;
	private Integer loginCount;

}
