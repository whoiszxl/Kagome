package com.whoiszxl.order.message;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.whoiszxl.order.utils.JsonUtil;
import com.whoiszxl.product.common.ProductInfoOutput;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProductInfoReceiver {

	@RabbitListener(queuesToDeclare = @Queue("productInfo"))
	public void process(String message) {
		ProductInfoOutput productInfoOutput = (ProductInfoOutput)JsonUtil.fromJson(message, ProductInfoOutput.class);
		log.info("从队列【{}】接收到消息【{}】","productInfo", productInfoOutput);
	}
	
}
