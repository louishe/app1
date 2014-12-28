package com.rmbin.cache.ehcache;

import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.rmbin.cache.Cache;
import com.rmbin.cache.CacheEventListener;
import com.rmbin.cache.CacheExpiredListener;

public class EhCache implements Cache,CacheEventListener {
	
	private net.sf.ehcache.Cache cache;
	
	private CacheExpiredListener listener;
	
	public EhCache(net.sf.ehcache.Cache cache,CacheExpiredListener listener)
	{
		this.cache = cache;
		this.listener = listener;
	}
	
	public Object get(Object key) {
		if(null == key)
			return null;
		Element element = cache.get(key);
		if(element != null)
			return element.getObjectValue();
		return null;
	}

	public void put(Object key, Object value) {
		Element element = new Element(key,value);
		cache.put(element);
	}

	public void update(Object key, Object value) {
		put(key,value);
	}

	public void remove(Object key) {
		cache.remove(key);
	}
	
	public List list() {
		return cache.getKeys();
	}


	public void clear() {
		cache.removeAll();
	}

	public void destory() {
		cache.getCacheManager().removeCache(cache.getName());
	}

	public void notifyElementRemoved(Ehcache ehcache, Element element) {
		
	}

	public void notifyElementUpdated(Ehcache ehcache, Element element) {
		// TODO Auto-generated method stub
		
	}

	public void notifyElementPut(Ehcache ehcache, Element element) {
		// TODO Auto-generated method stub
		
	}

	public void notifyElementExpired(Ehcache ehcache, Element element) {
		if(listener != null)
			listener.notifyElementExpired(cache.getName(),element.getObjectKey());
	}

}
