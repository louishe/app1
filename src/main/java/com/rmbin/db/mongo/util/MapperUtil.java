package com.rmbin.db.mongo.util;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.rmbin.db.mongo.annotation.Default;
import com.rmbin.db.mongo.annotation.Entity;
import com.rmbin.db.mongo.misc.DBIndex;

public class MapperUtil {
	
	public static String getEntityName(Class<?> clazz)
	{
		Entity entity = clazz.getAnnotation(Entity.class);
		String name = entity.name();
		if(name.equals(Default.NAME))
		{
			name = clazz.getSimpleName().toLowerCase();
		}
		return name;
	}

	public static List<DBIndex> getDBIndex(String index)
	{
		List<DBIndex> list = new ArrayList<DBIndex>();
		index = index.replaceAll("\\}[^{^}]+\\{", "};{");
        index = index.replaceAll("[{}'']", "");
        String[] items = index.split(";");
        for(String item : items){
        	DBObject keys = new BasicDBObject();
        	DBObject options = new BasicDBObject("background", true);
        	String[] arr = item.split(",");
        	for(String s : arr)
        	{
        		String[] kv = s.split(":");
        		String k = kv[0].trim();
        		String v = kv[1].trim();
        		if(v.equalsIgnoreCase("2d") || v.equalsIgnoreCase("text")){
                    keys.put(k, v);
                }
                else if(k.equalsIgnoreCase("expireAfterSeconds")){
                    options.put(k, Integer.parseInt(v));
                }
                else if(v.equals("1") || v.equals("-1")){
                    keys.put(k, Integer.parseInt(v));
                }
                else if(v.equalsIgnoreCase("true") || v.equalsIgnoreCase("false")){
                    options.put(k, Boolean.parseBoolean(v));
                }
                else if(k.equalsIgnoreCase("name")){
                    options.put(k, v);
                }
        	}
        	DBIndex dbi = new DBIndex();
        	dbi.setKeys(keys);
        	dbi.setOptions(options);
        	list.add(dbi);
        }
        return list;
	}
}
