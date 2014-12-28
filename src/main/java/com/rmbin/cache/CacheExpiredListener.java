package com.rmbin.cache;

public interface CacheExpiredListener {
	
	public void notifyElementExpired(String region, Object key);

}
