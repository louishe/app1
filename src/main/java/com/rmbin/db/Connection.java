package com.rmbin.db;

public interface Connection {
	
	public void connect(String host, String dataBase,int port);
	
	public void connect(String host,String dataBase,String userName,String password,int port);
	
	public void close();
	
	public void commit();
	
	public void rollback();

}
