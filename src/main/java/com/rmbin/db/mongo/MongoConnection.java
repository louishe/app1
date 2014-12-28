package com.rmbin.db.mongo;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.rmbin.db.Connection;
import com.rmbin.db.mongo.Exception.DBConnectionException;

public class MongoConnection implements Connection {
	
	private static final Logger logger = LoggerFactory.getLogger(MongoConnection.class);
	
	private int port;
	
	private String  dataBase;
	
	private String host;
	
	private String userName;
	
	private String password;
	
	private MongoClientOptions options;
	
	private MongoClient client;
	
	private DB db;
	
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	private static class Holder
	{
		final static MongoConnection instance = new MongoConnection();
	}
	
	public MongoConnection getInstance()
	{
		return Holder.instance;
	}
	
	public void connect(String host,String dataBase,int port)
	{
		this.host = host;
		this.dataBase = dataBase;
		this.port = port;
		doConnect();
	}
	

	public void connect(String host, String dataBase,String userName, String password, int port) {
		this.host = host;
		this.dataBase = dataBase;
		this.userName = userName;
		this.password = password;
		this.port = port;
		
	}
	
	public void connect()
	{
		doConnect();
		if(null != userName && null != password)
		{
			try {
				doAuth();
			} catch (DBConnectionException e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	public void doConnect()
	{
		ServerAddress hostAddr = new ServerAddress(new InetSocketAddress(this.host, port));
		if(null != options)
		{
			client = new MongoClient(hostAddr, options);
		}
		else
		{
			client = new MongoClient(hostAddr);
		}
		db = client.getDB(dataBase);
	}
	
	/**
	 * auth via user name & password
	 * @throws DBConnectionException
	 */
	public void doAuth() throws DBConnectionException
	{
		if(null != userName && null != password)
		{
			boolean auth = db.authenticate(userName, password.toCharArray());
			if(auth)
			{
				logger.info("Mongo DB authenticate successfully.");
			}
			else
			{
				throw new DBConnectionException("Mongo DB authenticate failure.");
			}
		}
	}

	public void close() {
		
	}

	public void commit() {

	}

	public void rollback() {

	}

}
