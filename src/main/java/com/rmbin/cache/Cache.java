package com.rmbin.cache;

import java.util.List;

/**
 * 
 * @author louis.he
 *
 * @version 0.1
 *
 * @since 2014-12-2 9:36AM
 */

public interface Cache {
	
	public Object get(Object key);
	
	public void put(Object key, Object value);
	
	public void update(Object key, Object value);
	
	public void remove(Object key);
	
	public List list();
	
	public void clear();
	
	public void destory();

}
