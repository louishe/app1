package com.rmbin.db.cache;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rmbin.db.mongo.Exception.FieldException;

public class FieldsCache {
	
	private static final Logger logger = LoggerFactory.getLogger(FieldsCache.class);
	
	private ConcurrentHashMap<String, Field[]> cache = new ConcurrentHashMap<String,Field[]>();
	
	private static class Holder
	{
		final static FieldsCache instance = new FieldsCache();
	}

	public static FieldsCache getInstance()
	{
		return Holder.instance;
	}
	
	private List<Field> filterField(Field[] fields)
	{
		List<Field> list = new ArrayList<Field>();
		for(Field field : fields)
		{
			if(!Modifier.isStatic(field.getModifiers()))
			{
				field.setAccessible(true);
				list.add(field);
			}
		}
		return list;
	}
	
	private Field[] getAllFields(Class<?> clazz)
	{
		List<Field> allFields = new ArrayList<Field>();
		allFields.addAll(filterField(clazz.getDeclaredFields()));
		Class parent = clazz.getSuperclass();
		while(null != parent && parent != Object.class)
		{
			allFields.addAll(filterField(parent.getDeclaredFields()));
			parent = parent.getSuperclass();
		}
		return allFields.toArray(new Field[allFields.size()]);
	}
	
	public Field[] get(Class clazz)
	{
		String name = clazz.getName();
		Field[] fields = cache.get(name);
		if(null != fields)
		{
			return fields;
		}
		fields = getAllFields(clazz);
		Field[] temp = cache.putIfAbsent(name, fields);
		if(null != temp)
		{
			return temp;
		}
		else
		{
			return fields;
		}
	}
	
	public Field getField(Class clazz, String fieldName) throws FieldException
	{
		Field field = null;
		Field[] fields = get(clazz);
		for(Field f : fields)
		{
			if(field.getName().equals(fieldName))
			{
				field = f;
				break;
			}
		}
		if(null != field)
			throw new FieldException("Field" + fieldName + " can't be found.");
		return field;
	}
}
