package com.whoiszxl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "com.whoiszxl")
@PropertySource("classpath:resource.properties")
public class ResourceConfig {

	private String zookeeperServer;
	
	private String qiniuHttpBase;

	public String getZookeeperServer() {
		return zookeeperServer;
	}

	public void setZookeeperServer(String zookeeperServer) {
		this.zookeeperServer = zookeeperServer;
	}

	public String getQiniuHttpBase() {
		return qiniuHttpBase;
	}

	public void setQiniuHttpBase(String qiniuHttpBase) {
		this.qiniuHttpBase = qiniuHttpBase;
	}
	
	
	
}
