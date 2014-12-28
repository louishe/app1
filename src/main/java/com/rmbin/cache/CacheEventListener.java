package com.rmbin.cache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public interface CacheEventListener {
	
	public void notifyElementRemoved(Ehcache ehcache, Element element);
	
	public void notifyElementUpdated(Ehcache ehcache, Element element);
	
	public void notifyElementPut(Ehcache ehcache,Element element);
	
	public void notifyElementExpired(Ehcache ehcache,Element element);

}
