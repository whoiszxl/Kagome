package com.whoiszxl.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.whoiszxl.seckill.domain.SeckillUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillUserDaoTest {

	@Autowired
	private SeckillUserDao seckillUserDao;
	
	@Test
	public void testGetSeckillUserById() {
		SeckillUser seckillUserById = seckillUserDao.getSeckillUserById(1L);
		System.out.println(seckillUserById);
	}

	@Test
	public void testUpdate() {
		SeckillUser seckillUser = new SeckillUser();
		seckillUser.setId(1L);
		seckillUser.setPassword("666666");
		seckillUserDao.updatePassword(seckillUser);
	}

}
