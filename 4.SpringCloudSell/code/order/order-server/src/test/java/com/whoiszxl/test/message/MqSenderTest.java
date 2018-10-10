package com.whoiszxl.test.message;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqSenderTest {

	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Test
	public void send() throws Exception {
		amqpTemplate.convertAndSend("myQueue", "now:"+new Date().toString());
	}
	
}
