package com.whoiszxl.order.controller;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * mq
 * @author whoiszxl
 *
 */
@RestController
public class MessageController {

	@Autowired
	private AmqpTemplate amqpTemplate;
	
	
	@RequestMapping("/send")
	public String send() {
		amqpTemplate.convertAndSend("myQueue", "time:"+new Date().toString());
		return "success";
	}

}
