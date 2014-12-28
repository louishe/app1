package com.rmbin.db.mongo.decode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBObject;
import com.rmbin.db.mongo.annotation.Default;
import com.rmbin.db.mongo.annotation.Property;
import com.rmbin.db.mongo.util.DataType;

public class PropertyDecoder extends AbstractDecoder {
	
	private static final Logger logger = LoggerFactory.getLogger(PropertyDecoder.class);

	protected PropertyDecoder(Field field,DBObject obj) {
		super(field);
		String fieldName = field.getName();
		Property prop = field.getAnnotation(Property.class);
		if(prop != null)
		{
			String name = prop.name();
			if(name != Default.NAME)
			{
				fieldName = name;
			}
		}
		value = obj.get(fieldName);
	}

	public void decode(Object obj) {
		Class<?> type = field.getType();
		try
		{
			if(type.isArray())
			{
				Class compType = type.getComponentType();
				if(DataType.isByte(compType))
				{
					decodeBinaray(obj);
				}
				else
				{
					decodeArray(obj, compType);
				}
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
	}
	
	private void decodeBinaray(Object obj) throws IllegalArgumentException, IllegalAccessException
	{
		field.set(obj, (byte[])obj);
	}
	
	private void decodeArray(Object obj, Class compType) throws IllegalArgumentException, IllegalAccessException
	{
		List list = (ArrayList)obj;
		int size = list.size();
		if(DataType.isString(compType))
		{
			String[] str = new String[size];
			for(int i = 0; i < size;i++)
			{
				str[i] = list.get(i).toString();
			}
			field.set(obj,str);
		}
		else if(DataType.isInteger(compType))
		{
			int[] arr = new int[size];
			for(int i = 0; i < size; i++)
			{
				arr[i] = Integer.parseInt(list.get(i).toString());
			}
			field.set(obj, arr);
		}
		else if(DataType.isLong(compType))
		{
			long[] arr = new long[size];
			for(int i = 0; i < size; i++)
			{
				arr[i] = Long.parseLong(list.get(i).toString()); 
			}
			field.set(obj, arr);
		}
		else if(DataType.isDouble(compType))
		{
			double[] arr = new double[size];
			for(int i = 0; i < size; i++)
			{
				arr[i] = Double.parseDouble(list.get(i).toString());
			}
			field.set(obj, arr);
		}
		else if(DataType.isShort(compType))
		{
			short[] arr = new short[size];
			for(int i= 0; i < size;i++)
			{
				arr[i] = Short.parseShort(list.get(i).toString());
			}
			field.set(obj, arr);
		}
		else if(DataType.isFloat(compType))
		{
			float[] arr = new float[size];
			for(int i = 0; i < size; i++)
			{
				arr[i] = Float.parseFloat(list.get(i).toString());
			}
			field.set(obj, arr);
		}
	}

}
