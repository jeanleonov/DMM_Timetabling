package com.timetabling.server.base.data.dao;

import java.util.Map;

import com.googlecode.objectify.Key;

/**
 * Generic interface containing a list of essential datastore operations
 * @param <T> - specify entity class to operate on 
 */
public interface IGenericDAO<T>
{
	public Key<T> put(T entity) throws Exception;
	
	public Key<T> update(T entity, Key<T> key) throws Exception;
	
	public Map<Key<T>, T> putAll(Iterable<T> entities);
	
	public boolean delete(T entity) throws Exception;
	
	public boolean deleteByKey(Key<T> entityKey) throws Exception;
	
	public void deleteAll(Iterable<T> entities);
	
	public void deleteKeys(Iterable<Key<T>> keys);
	
	public T get(Long id) throws Exception;
	
	public T get(Key<T> key) throws Exception;
	
	//for test purpose only
	public void clearTable();
}
