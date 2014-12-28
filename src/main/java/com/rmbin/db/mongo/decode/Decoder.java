package com.rmbin.db.mongo.decode;

public interface Decoder {
	
	public void decode(Object obj);
	
	public boolean isNullField();

}
