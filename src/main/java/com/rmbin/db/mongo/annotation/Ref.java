package com.rmbin.db.mongo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Ref {

	public String name() default Default.NAME;
	
	public String cascade() default Default.CASCADE;
	
	public boolean reduce() default false;//Go oning...
	
	public Class<?> Impl() default Default.class;//Go oning...
}
