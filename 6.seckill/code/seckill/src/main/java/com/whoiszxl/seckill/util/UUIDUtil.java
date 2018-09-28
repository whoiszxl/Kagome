package com.whoiszxl.seckill.util;

import java.util.UUID;

/**
 *  UUID工具类
 * @author whoiszxl
 *
 */
public class UUIDUtil {
	
	/**
	 *  生成一个不带"-"符号的UUID
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
