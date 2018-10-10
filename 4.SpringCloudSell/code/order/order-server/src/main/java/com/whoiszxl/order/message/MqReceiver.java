package com.whoiszxl.order.message;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * MQ消息接收
 * @author Administrator
 *
 */
@Slf4j
@Component
public class MqReceiver {

	//被动创建队列@RabbitListener(queues = "myQueue")
	//自动创建队列@RabbitListener(queuesToDeclare = @Queue("myQueue"))
	//自动创建，Exchange和Queue绑定
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue("myQueue"),
			exchange = @Exchange("myExchange")))
	public void process(String message) {
		log.info("MqReceiver: {}", message);
	}
}
