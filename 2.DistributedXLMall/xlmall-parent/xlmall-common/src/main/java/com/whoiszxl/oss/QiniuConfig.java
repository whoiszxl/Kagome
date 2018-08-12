package com.whoiszxl.oss;

public class QiniuConfig {

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
	
	public QiniuConfig(String qiniuHttpBase, String qiniuAccessKey, String qiniuSecretKey, String qiniuBucket) {
		super();
		this.qiniuHttpBase = qiniuHttpBase;
		this.qiniuAccessKey = qiniuAccessKey;
		this.qiniuSecretKey = qiniuSecretKey;
		this.qiniuBucket = qiniuBucket;
	}

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
