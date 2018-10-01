package com.whoiszxl.order.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.order.converter.OrderForm2OrderDTOConverter;
import com.whoiszxl.order.dto.OrderDTO;
import com.whoiszxl.order.enums.ResultEnum;
import com.whoiszxl.order.exception.OrderException;
import com.whoiszxl.order.form.OrderForm;
import com.whoiszxl.order.service.OrderService;
import com.whoiszxl.order.utils.ResultVOUtil;
import com.whoiszxl.order.vo.ResultVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 订单控制器
 * @author whoiszxl
 *
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
    /**
     * 1. 参数检验
     * 2. 查询商品信息(调用商品服务)
     * 3. 计算总价
     * 4. 扣库存(调用商品服务)
     * 5. 订单入库
     */
	@PostMapping("/create")
	public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			log.error("【创建订单】参数不正确, orderForm={}", orderForm);
			throw new OrderException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
		}
		
		//orderForm =》 orderDTO
		OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
		if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
			log.error("【创建订单】购物车信息为空");
			throw new OrderException(ResultEnum.CART_EMPTY);
		}
		OrderDTO result = orderService.create(orderDTO);
		
		Map<String, String> map = new HashMap<>();
		map.put("orderId", result.getOrderId());
		return ResultVOUtil.success(map);
	}
}
