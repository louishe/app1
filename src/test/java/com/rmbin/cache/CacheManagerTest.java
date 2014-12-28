package com.rmbin.cache;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CacheManagerTest {
	
	private CacheManager manager = null;

	@Test
	public  void test0Setup() throws Exception {
		manager = new CacheManager();
		manager.initProvider(null);
	}

	@Test
	public void test1put() {
		manager.put("test1", "key1", "value1");
	}
	
	@Test
	public void test2Get()
	{
		assertEquals(manager.get("test1", "key1"), "value1");
	}

}
