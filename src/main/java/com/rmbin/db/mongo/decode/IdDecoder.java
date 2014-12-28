package com.rmbin.db.mongo.decode;

import java.lang.reflect.Field;

import com.mongodb.DBObject;
import com.rmbin.db.mongo.util.FieldsUtil;

public class IdDecoder extends AbstractDecoder {

	protected IdDecoder(Field field, DBObject obj) {
		super(field);
		this.value = obj;
	}

	public void decode(Object obj) {
		FieldsUtil.set(obj, field, value.toString());
	}

}
