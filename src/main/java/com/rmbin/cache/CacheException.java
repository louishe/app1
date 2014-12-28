package com.rmbin.cache;

public class CacheException extends RuntimeException{
	
	public CacheException(String msg)
	{
		super(msg);
	}
	
	public CacheException(String msg, Throwable e)
	{
		super(msg,e);
	}
	
	public CacheException(Throwable e)
	{
		super(e);
	}

}
