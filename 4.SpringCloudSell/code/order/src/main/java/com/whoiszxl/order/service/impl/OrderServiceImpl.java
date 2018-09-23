package com.whoiszxl.order.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoiszxl.order.dataobject.OrderMaster;
import com.whoiszxl.order.dto.OrderDTO;
import com.whoiszxl.order.enums.OrderStatusEnum;
import com.whoiszxl.order.enums.PayStatusEnum;
import com.whoiszxl.order.repository.OrderDetailRepository;
import com.whoiszxl.order.repository.OrderMasterRepository;
import com.whoiszxl.order.service.OrderService;
import com.whoiszxl.order.utils.KeyUtil;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private OrderMasterRepository orderMasterRepository;
	
	@Override
	public OrderDTO create(OrderDTO orderDTO) {
		//todo 2.查询商品信息(调用商品服务)
	    //todo 3. 计算总价
	    //todo 4. 扣库存(调用商品服务)
		
	    // 5. 订单入库
		OrderMaster orderMaster = new OrderMaster();	
		orderDTO.setOrderId(KeyUtil.genUniqueKey());
		BeanUtils.copyProperties(orderDTO, orderMaster);
		orderMaster.setOrderAmount(new BigDecimal(5));
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		orderMaster.setOrderId(KeyUtil.genUniqueKey());
		orderMasterRepository.save(orderMaster);
		return orderDTO;
	}

}
