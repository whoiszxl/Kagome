package com.whoiszxl.seckill.redis;

public abstract class BasePrefix implements KeyPrefix{
	
	/** 过期时间 */
	private int expireSeconds;
	
	/** Redis前缀 */
	private String prefix;
	
	/**
	 * 0代表永不过期
	 * @param prefix
	 */
	public BasePrefix(String prefix) {
		this(0, prefix);
	}
	
	public BasePrefix( int expireSeconds, String prefix) {
		this.expireSeconds = expireSeconds;
		this.prefix = prefix;
	}
	
	public int expireSeconds() {
		return expireSeconds;
	}

	public String getPrefix() {
		String className = getClass().getSimpleName();
		return className + ":" + prefix;
	}

}

