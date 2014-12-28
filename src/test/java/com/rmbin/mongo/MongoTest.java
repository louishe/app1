package com.rmbin.mongo;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoTest {
	
	private String dbName = "";//"KloIVuHCNzIuSYHPIPbo";
	private String host = "10.50.71.12";//"mongo.duapp.com";
	private int port = 27017;//8908;
	private String userName = "j8pLPtRkeHpdWnWviYgVBUnO";//Api Key
	private String password = "CSBfVSBRbNybcndvAbBKMNhtIn2mnXgN";//Secrity Key
	MongoClient client = null;
	DB db = null;
	
	@Before
	public void setUp()
	{
		try
		{
			client = new MongoClient(new ServerAddress(host,port) , Arrays.asList(MongoCredential.createMongoCRCredential(userName, dbName, password.toCharArray())), new MongoClientOptions.Builder().cursorFinalizerEnabled(false).build());
			db = client.getDB("DEMO");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void getCollection()
	{
		db.authenticate(userName, password.toCharArray());
		DBCollection collection = db.getCollection("demo_user");
	}

}
