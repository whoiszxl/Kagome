package com.whoiszxl.seckill.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 参数校验工具类
 * @author whoiszxl
 *
 */
public class ValidatorUtil {
	
	private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");
	
	/**
	 * 判断是否是手机号
	 * @param 手机号
	 * @return
	 */
	public static boolean isMobile(String src) {
		if(StringUtils.isEmpty(src)) {
			return false;
		}
		Matcher m = mobile_pattern.matcher(src);
		return m.matches();
	}
}
