package com.whoiszxl.order.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoiszxl.order.dataobject.OrderDetail;
import com.whoiszxl.order.dataobject.OrderMaster;
import com.whoiszxl.order.dto.OrderDTO;
import com.whoiszxl.order.enums.OrderStatusEnum;
import com.whoiszxl.order.enums.PayStatusEnum;
import com.whoiszxl.order.repository.OrderDetailRepository;
import com.whoiszxl.order.repository.OrderMasterRepository;
import com.whoiszxl.order.service.OrderService;
import com.whoiszxl.order.utils.KeyUtil;
import com.whoiszxl.product.client.ProductClient;
import com.whoiszxl.product.common.DecreaseStockInput;
import com.whoiszxl.product.common.ProductInfoOutput;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private OrderMasterRepository orderMasterRepository;
	
	@Autowired
	private ProductClient productClient;
	
	@Override
	public OrderDTO create(OrderDTO orderDTO) {
		
		String orderId = KeyUtil.genUniqueKey();
		
		//todo 2.查询商品信息(调用商品服务)
		List<String> productIdList = orderDTO.getOrderDetailList().stream()
			.map(OrderDetail::getProductId)
			.collect(Collectors.toList());
		
		List<ProductInfoOutput> productInfoList = productClient.getProductListByIds(productIdList);
	    //todo 3. 计算总价
		BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
		for(OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
			//单价*数量
			for (ProductInfoOutput productInfo : productInfoList) {
				if(productInfo.getProductId().equals(orderDetail.getProductId())) {
					orderAmount = productInfo.getProductPrice()
							.multiply(new BigDecimal(orderDetail.getProductQuantity()))
							.add(orderAmount);
					
					BeanUtils.copyProperties(productInfo, orderDetail);
					orderDetail.setOrderId(orderId);
					orderDetail.setDetailId(KeyUtil.genUniqueKey());
					
					//订单详情入库
					orderDetailRepository.save(orderDetail);
				}
			}
		}
		
	    //todo 4. 扣库存(调用商品服务)
		List<DecreaseStockInput> cartDTOList = orderDTO.getOrderDetailList().stream()
				.map(e -> new DecreaseStockInput(e.getProductId(), e.getProductQuantity()))
				.collect(Collectors.toList());
		productClient.decreaseStock(cartDTOList);
		
	    // 5. 订单入库
		OrderMaster orderMaster = new OrderMaster();	
		orderDTO.setOrderId(orderId);
		BeanUtils.copyProperties(orderDTO, orderMaster);
		orderMaster.setOrderAmount(orderAmount);
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		Date now = new Date();
		orderMaster.setUpdateTime(now);
		orderMaster.setCreateTime(now);
		orderMasterRepository.save(orderMaster);
		return orderDTO;
	}

}
