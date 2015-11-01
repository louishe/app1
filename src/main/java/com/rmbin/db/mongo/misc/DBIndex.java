package com.rmbin.db.mongo.misc;

import com.mongodb.DBObject;

public class DBIndex {

	private DBObject keys;
	
	private DBObject options;

	public DBObject getKeys() {
		return keys;
	}

	public void setKeys(DBObject keys) {
		this.keys = keys;
	}

	public DBObject getOptions() {
		return options;
	}

	public void setOptions(DBObject options) {
		this.options = options;
	}
}
