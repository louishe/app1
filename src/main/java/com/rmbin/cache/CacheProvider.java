package com.rmbin.cache;

import java.util.Properties;

import com.rmbin.cache.ehcache.EhCache;

/**
 * Cache Provider
 * @author louis-he
 *
 */
public interface CacheProvider {
	
	public void start(Properties prop);
	
	public void stop();
	
	public EhCache buildCache(String region, boolean autoCreate, CacheExpiredListener listener);

}
