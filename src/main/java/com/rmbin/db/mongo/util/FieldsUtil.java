package com.rmbin.db.mongo.util;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rmbin.db.cache.FieldsCache;
import com.rmbin.db.mongo.annotation.Default;
import com.rmbin.db.mongo.annotation.Ref;

public class FieldsUtil {

	private final static Logger logger = LoggerFactory.getLogger(FieldsUtil.class);

	public static Object get(Field field, Object obj) {
		Object value = null;
			try {
				value = field.get(obj);
			} catch (IllegalArgumentException e) {
				logger.error("IllegalArgumentException:" + " Can't get field value.");
			} catch (IllegalAccessException e) {
				logger.error("IllegalAccessException:" + " Can't get field value.");
			}
		return value;
	}
	
	public static void set(Object obj, Field field, Object value)
	{
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException e) {
			logger.error("IllegalArgumentException:" + " Can't set field value.");
		} catch (IllegalAccessException e) {
			logger.error("IllegalAccessException:" + " Can't set field value.");
		}
	}
	
	
	public static void copy(Object src, Object target)
	{
		if(src == null || target == null)
			return;
		Field[] fields = FieldsCache.getInstance().get(src.getClass());
		for(Field field : fields)
		{
			Object value = get(field, src);
			set(target,field,value);
		}
	}
	
	public static Class<?> getRealType(Class<?> clazz, Field field)
	{
		if(clazz.isInterface())
		{
			Ref ref = clazz.getAnnotation(Ref.class);
			if(ref != null && ref.Impl() != Default.class)
			{
				return ref.Impl();
			}
		}
		return clazz;
	}
	
	
}
