package com.whoiszxl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages= {"com.whoiszxl.mapper"})//扫描mybatis包路径
@ComponentScan(basePackages= {"com.whoiszxl"})//扫描所有需要的包，包含一些工具类包所在的路径
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

