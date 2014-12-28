package com.rmbin.db.mongo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ID {
	
	public IdType type() default IdType.AUTO_GENERATE;
	
	public long start() default 1l;// go oning...

}
