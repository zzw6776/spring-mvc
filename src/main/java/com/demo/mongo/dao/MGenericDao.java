package com.demo.mongo.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public abstract class MGenericDao<T> {

	@Autowired
	protected MongoTemplate mongoTemplate;

	private Class<T> type;

	@SuppressWarnings("unchecked")
	public MGenericDao() {
		Type type = getClass().getGenericSuperclass();
		while (type != null && (!(type instanceof ParameterizedType) || !(MGenericDao.class.equals(((ParameterizedType) type).getRawType())))) {
			type = ((Class<?>) type).getGenericSuperclass();
		}
		if (type!=null) {
			this.type = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
		}
	}

	public void insert(T t) {
		this.mongoTemplate.insert(t);
	}

	public void insert(T t, String name) {
		this.mongoTemplate.insert(t, name);
	}

	public void save(T t) {
		this.mongoTemplate.save(t);
	}

	public void save(T t, String name) {
		this.mongoTemplate.save(t, name);
	}

	public List<T> find(Query query) {
		return this.mongoTemplate.find(query, type);
	}

	public List<T> find(Query query, String collectionName) {
		return this.mongoTemplate.find(query, type, collectionName);
	}

	public List<T> findAll(String name) {
		return this.mongoTemplate.findAll(type, name);
	}

	public void remove(Query query) {
		this.mongoTemplate.remove(query, type);
	}

	public void remove(Query query, String collectionName) {
		this.mongoTemplate.remove(query, type, collectionName);
	}
}