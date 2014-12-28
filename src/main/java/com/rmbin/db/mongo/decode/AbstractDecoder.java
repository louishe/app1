package com.rmbin.db.mongo.decode;

import java.lang.reflect.Field;

public abstract class AbstractDecoder implements Decoder{
	
	protected Field field;
	
	protected Object value;

	protected AbstractDecoder(Field field)
	{
		this.field = field;
	}
	
	public boolean isNullField() {
		return value == null;
	}
}
