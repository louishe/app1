package com.rmbin.cache;

import junit.framework.TestCase;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import com.rmbin.cache.ehcache.EhCache;
import com.rmbin.cache.ehcache.EhcacheProvider;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EhCacheTest extends TestCase{
	
	public static final String REGION_1 = "test1";
	
	EhCache cache = null;
	
	public void setUp()
	{
		CacheProvider provider = new EhcacheProvider();
		provider.start(null);
		cache = provider.buildCache(REGION_1, true, null);
		cache.put("test_1_1", "1");
	}
	
	public void testRemoveCache()
	{
		assertEquals(cache.get("test_1_1"),"1");
		cache.remove("test_1_1");
		assertEquals(cache.get("test_1_1"),null);
	}
	
	public void tearDown()
	{
		cache.destory();
	}

}
