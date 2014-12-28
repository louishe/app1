package com.rmbin.cache.ehcache;

import java.net.URL;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rmbin.cache.CacheException;
import com.rmbin.cache.CacheExpiredListener;
import com.rmbin.cache.CacheProvider;

public class EhcacheProvider implements CacheProvider{
	
	private final static Logger logger = LoggerFactory.getLogger(EhcacheProvider.class);
	
	private CacheManager manager;
	
	private ConcurrentHashMap<String,EhCache> _CacheManager;
	
	private String CONFIG_XML = "/ehCache.xml";

	public void start(Properties prop) {
		if(manager != null)
		{
			logger.info("A Cache Manager has been exits.");
			return;
		}
		URL xml = getClass().getClassLoader().getParent().getResource(CONFIG_XML);
		if(xml == null)
			xml = getClass().getResource(CONFIG_XML);
		if(xml == null)
			throw new CacheException("Can't find ehCache configuration!");
		manager = new CacheManager();
		_CacheManager = new ConcurrentHashMap<String,EhCache>();
	}

	public void stop() {
		if(manager != null)
		{
			logger.info("This Cache Manager is stopped.");
			manager.shutdown();
			manager = null;
		}
	}

	public EhCache buildCache(String region, boolean autoCreate, CacheExpiredListener listener) {
		EhCache ehcache = _CacheManager.get(region);
		if(ehcache == null && autoCreate)
		{
			net.sf.ehcache.Cache cache = manager.getCache(region);
			if(cache == null)
			{
				manager.addCache(region);
				cache = manager.getCache(region);
			}
			ehcache = new EhCache(cache, listener);
			_CacheManager.put(region, ehcache);
		}
		return ehcache;
	}
	
}
