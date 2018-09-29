package com.whoiszxl.seckill.redis;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.whoiszxl.seckill.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {

	@Autowired
	private RedisService redisService;
	
	private String mkey = "10";
	
	@Test
	public void testGet() {
		User user = redisService.get(UserKey.getByName, mkey, User.class);
		System.out.println(user);
	}

	@Test
	public void testSet() {
		User user = new User();
		user.setId(10);
		user.setName("王菲");
		redisService.set(UserKey.getByName, mkey, user);
	}

	@Test
	public void testExists() {
		boolean exists = redisService.exists(UserKey.getByName, mkey);
		System.out.println("键值为"+ mkey +"是否存在："+exists);
	}

	@Test
	public void testIncr() {
		Long incr = redisService.incr(SeckillUserKey.token, "apple");
		System.out.println("自增：" + incr);
	}

	@Test
	public void testDecr() {
		Long decr = redisService.decr(SeckillUserKey.token, "apple");
		System.out.println("自减：" + decr);
	}

}
