package com.whoiszxl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "com.whoiszxl")
@PropertySource("classpath:secret.properties")
public class SecretConfig {

	/**
	 * 七牛云http地址
	 */
	private String qiniuHttpBase;
	
	/**
	 * 七牛云accessKey
	 */
	private String qiniuAccessKey;
	
	/**
	 * 七牛云secretKey
	 */
	private String qiniuSecretKey;
	
	/**
	 * 七牛云bucket空间
	 */
	private String qiniuBucket;

	
	
	public String getQiniuHttpBase() {
		return qiniuHttpBase;
	}

	public void setQiniuHttpBase(String qiniuHttpBase) {
		this.qiniuHttpBase = qiniuHttpBase;
	}

	public String getQiniuAccessKey() {
		return qiniuAccessKey;
	}

	public void setQiniuAccessKey(String qiniuAccessKey) {
		this.qiniuAccessKey = qiniuAccessKey;
	}

	public String getQiniuSecretKey() {
		return qiniuSecretKey;
	}

	public void setQiniuSecretKey(String qiniuSecretKey) {
		this.qiniuSecretKey = qiniuSecretKey;
	}

	public String getQiniuBucket() {
		return qiniuBucket;
	}

	public void setQiniuBucket(String qiniuBucket) {
		this.qiniuBucket = qiniuBucket;
	}
	
}
