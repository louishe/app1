package com.rmbin.db.mongo.annotation;

public @interface Property {

	public String name() default Default.NAME;
	
	public boolean lazy() default false;
}
