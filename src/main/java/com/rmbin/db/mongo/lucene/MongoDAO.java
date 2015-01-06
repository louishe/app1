package com.rmbin.db.mongo.lucene;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.rmbin.db.mongo.MongoConnection;
import com.rmbin.db.mongo.annotation.Default;
import com.rmbin.db.mongo.annotation.EnsureIndex;
import com.rmbin.db.mongo.annotation.Entity;
import com.rmbin.db.mongo.annotation.SplitType;
import com.rmbin.db.mongo.misc.DBIndex;
import com.rmbin.db.mongo.util.MapperUtil;

public class MongoDAO<T> {

	private static final Logger logger = LoggerFactory.getLogger(MongoDAO.class);

	private Class<T> clazz;

	private DB db;

	private WriteConcern writeConcern;

	private DBCollection coll;

	public MongoDAO(Class<T> clazz) {
		this.clazz = clazz;
		db = MongoConnection.getInstance().getDB();
		writeConcern = db.getWriteConcern();
		Entity entity = clazz.getAnnotation(Entity.class);
		SplitType splitType = entity.split();
		if (splitType == SplitType.NONE) {
			String name = MapperUtil.getEntityName(clazz);
			initCollection(name, entity);
		}
	}

	private void initCollection(String name, Entity entity) {
		if (entity.capped() && !db.collectionExists(name)) {
			DBObject options = new BasicDBObject("capped", true);
			long cappedSize = entity.capSize();
			long capMax = entity.capMax();
			if (cappedSize != Default.CAP_SIZE) {
				options.put("size", cappedSize);
			}
			if (capMax != entity.capMax()) {
				options.put("max", capMax);
			}
			coll = db.createCollection(name, options);
		} else {
			coll = db.getCollection(name);
		}
		EnsureIndex index = clazz.getAnnotation(EnsureIndex.class);
		if(null != index)
		{
			List<DBIndex> list = MapperUtil.getDBIndex(index.value());
			for(DBIndex ind : list)
			{
				coll.createIndex(ind.getKeys(), ind.getOptions());
			}
		}
	}

}
