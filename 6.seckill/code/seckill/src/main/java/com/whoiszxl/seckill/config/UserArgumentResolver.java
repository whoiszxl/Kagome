package com.whoiszxl.seckill.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.whoiszxl.seckill.constants.Constants;
import com.whoiszxl.seckill.domain.SeckillUser;
import com.whoiszxl.seckill.service.SeckillUserService;

@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	SeckillUserService userService;

	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> clazz = parameter.getParameterType();
		return clazz == SeckillUser.class;
	}

	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

		//获取token
		String paramToken = request.getParameter(Constants.COOKI_NAME_TOKEN);
		String cookieToken = getCookieValue(request, Constants.COOKI_NAME_TOKEN);
		if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
			return null;
		}
		String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
		//通过token获取到redis中的用户对象并延长cookie
		return userService.getByToken(response, token);
	}

	/**
	 * 通过cookie名获取cookie的值
	 * @param request
	 * @param cookiName
	 * @return
	 */
	private String getCookieValue(HttpServletRequest request, String cookiName) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookiName)) {
				return cookie.getValue();
			}
		}
		return null;
	}

}
