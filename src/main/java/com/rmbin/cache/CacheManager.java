package com.rmbin.cache;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rmbin.cache.ehcache.EhcacheProvider;

/**
 * Cache Manger is the interface that return the Cache Provider
 * @author louis-he
 *
 */
public class CacheManager
{

	private static final Logger logger = LoggerFactory.getLogger(CacheManager.class);

	private static CacheProvider provider;

	private static CacheExpiredListener listener;
	
	private String CACHE_CONFIG = "/cache.properties";

	private static final String EHCACHE_PROVIDER = "cache.provider_1";

	/**
	 * Init Cache Manger and find Provider
	 * @param listener
	 */
	public void initProvider(CacheExpiredListener listener) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(CACHE_CONFIG);
		if (null == in) {
			in = getClass().getResourceAsStream(CACHE_CONFIG);
		}
		if (null == in)
			throw new CacheException("Can't find the configuraiton.");
		CacheManager.listener = listener;
		Properties prop = new Properties();
		try {
			prop.load(in);
			logger.info("Cache configuration has been loaded in.");
			in.close();
			provider = getCacheProvider(prop.getProperty(EHCACHE_PROVIDER));
			provider.start(prop);
		} catch (Exception e) {
			logger.error("Init Cache Provider error.");
		}
	}

	private CacheProvider getCacheProvider(String cacheProvider) throws Exception {
		if ("ehcache".equals(cacheProvider)) {
			logger.info("EhcacheProvider is found.");
			return new EhcacheProvider();
		}
		return (CacheProvider) Class.forName(cacheProvider).newInstance();
	}
	
	public static final Cache _GetCache(String region,boolean autoCreate)
	{
		return provider.buildCache(region, autoCreate, listener);
	}
	
	public void shutDown()
	{
		provider.stop();
	}
	
	public static final Object get(String region,Object key)
	{
		if(null != region && key != null)
		{
			Cache cache = _GetCache(region, false);
			if(cache != null)
			{
				return cache.get(key);
			}
		}
		return null;
	}
	
	public static final void put(String region,Object key,Object value)
	{
		Cache cache = _GetCache(region, true);
		cache.put(key, value);
	}
	
	public static final void remove(String region,Object key)
	{
		Cache cache = _GetCache(region, false);
		if(cache != null)
		{
			cache.remove(key);
		}
	}
	
	public static final void clear(String region)
	{
		Cache cache = _GetCache(region, false);
		if(cache != null)
			cache.clear();
	}

	public static final List list(String region)
	{
		Cache cache = _GetCache(region, false);
		if(cache != null)
		{
			return cache.list();
		}
		return null;
	}
}
