package com.rosinka.tt.server.base.data.dao;

import java.util.Map;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.rosinka.tt.server.base.data.dao.DAOT.DatastoreOperation;
import com.rosinka.tt.server.base.data.entities.DatastoreEntity;
import com.rosinka.tt.server.base.exceptions.InternalException;
import com.rosinka.tt.server.base.task.mailexception.MailLoggerHandler;
import com.rosinka.tt.server.managers.DaoManagerFactory;

/**
 * Implementation of {IGenericDAO} interface
 */
public class GenericDAO<T extends DatastoreEntity> extends DAOBase implements IGenericDAO<T>
{
	protected final Logger logger;

	protected Class<T> clazz;

	/**
	 * Get the associated domain class
	 * 
	 * @param clazz
	 */
	protected GenericDAO(Class<T> clazz)
	{
		this.clazz = clazz;

		// initiate DaoFactory in order to register all required entities
		DaoManagerFactory.initiate();

		logger = Logger.getLogger(clazz.getSimpleName());

		// currently application users only on type of logger handler
		if ( logger.getHandlers().length == 0)
		{
			logger.addHandler(new MailLoggerHandler());
		}
	}

	@Override
	public Objectify ofy()
	{
		return ObjectifyService.begin();
	}

	public Objectify beginTransaction()
	{
		return ObjectifyService.beginTransaction();
	}

	/**
	 * Inserts new-created entity of a specific type into DB. Throws exception
	 * in case of any DB issues Returns new-created entity.
	 * 
	 * @param entity
	 *            - entity to put into datastore
	 * @throws Exception
	 */
	@Override
	public Key<T> put(final T entity) throws Exception
	{
		Key<T> key = DAOT.runWithoutTransaction(logger, new DatastoreOperation<Key<T>>()
		{
			@Override
			public Key<T> run(DAOT daot)
			{
				return daot.getOfy().put(entity);
			}

			@Override
			public String getOperationName()
			{
				return "Put operation on entity kind := " + clazz.getSimpleName();
			}
		});

		return key;
	}

	/**
	 * Updates given entity in datastore Throws exception in case of any DB or
	 * entity not found issues
	 * 
	 * @param entity
	 *            - entity to update in datasore
	 * @throws InternalException
	 */
	@Override
	public Key<T> update(final T entity, final Key<T> key) throws Exception
	{
		Key<T> updateKey = DAOT.runWithoutTransaction(logger, new DatastoreOperation<Key<T>>()
		{
			@Override
			public Key<T> run(DAOT daot)
			{
				if ( null != key && null != daot.getOfy().get(key))
				{
					return daot.getOfy().put(entity);
				}
				else
				{
					return null;
				}
			}

			@Override
			public String getOperationName()
			{
				return "Update operation on entity kind := " + clazz.getSimpleName() + " with entity id := " + key.getId();
			}
		});

		if ( null == updateKey)
		{
			throw new InternalException("UnknownEntity " + key.getKind() + "with id :=" + key.getId());
		}

		return updateKey;
	}

	/**
	 * Fetched entity by a given long ID Throws exception in case of any DB or
	 * entity not found issues
	 * 
	 * @param entity
	 *            ID
	 * @return entity - entity fetched by a given ID
	 * @throws Exception
	 */
	@Override
	public T get(final Long id) throws Exception
	{
		return DAOT.runWithoutTransaction(logger, new DatastoreOperation<T>()
		{

			@Override
			public T run(DAOT daot) throws Exception
			{
				return ofy().get(clazz, id);
			}

			@Override
			public String getOperationName()
			{
				return "Get by id. Entity: '" + clazz.getSimpleName() + "', id: '" + id + "'";
			}
		});
	}

	/**
	 * Fetched entity by a given Key Throws exception in case of any DB or
	 * entity not found issues
	 * 
	 * @param entity
	 *            Key
	 * @throws InternalException
	 * @return entity - entity fetched by a given Key
	 */
	@Override
	public T get(final Key<T> key) throws Exception
	{
		return DAOT.runWithoutTransaction(logger, new DatastoreOperation<T>()
		{

			@Override
			public T run(DAOT daot) throws Exception
			{
				return ofy().get(key);
			}

			@Override
			public String getOperationName()
			{
				return "Get by key. Entity: '" + clazz.getSimpleName() + "', id: '" + key.getId() + "'";
			}
		});
	}

	/**
	 * Performs deletion of a given entity Throws exception in case of any DB or
	 * entity not found issues
	 * 
	 * @param entity
	 *            instance
	 * @return flag indicating operation success
	 * @throws InternalException
	 */
	@Override
	public boolean delete(final T entity) throws Exception
	{
		return DAOT.runInTransaction(logger, new DatastoreOperation<Boolean>()
		{
			@Override
			public Boolean run(DAOT daot)
			{
				daot.getOfy().delete(entity);

				return true;
			}

			@Override
			public String getOperationName()
			{
				return "Delete operation on entity kind := " + clazz.getSimpleName() + " with entity key := " + null == entity.getEntityKey() ? "null" : entity.getEntityKey();
			}
		});
	}

	/**
	 * Performs deletion of an entity by a given entity key Throws exception in
	 * case of any DB or entity not found issues
	 * 
	 * @param entity
	 *            key
	 * @return flag indicating operation success
	 */
	@Override
	public boolean deleteByKey(final Key<T> entityKey) throws Exception
	{
		return DAOT.runWithoutTransaction(logger, new DatastoreOperation<Boolean>()
		{
			@Override
			public Boolean run(DAOT daot)
			{
				daot.getOfy().delete(entityKey);

				return true;
			}

			@Override
			public String getOperationName()
			{
				return "Delete by key operation on entity kind := " + clazz.getSimpleName() + " with entity key := " + KeyFactory.keyToString(entityKey.getRaw());
			}
		});
	}

	@Override
	public Map<Key<T>, T> putAll(Iterable<T> entities)
	{
		return ofy().put(entities);
	}

	@Override
	public void deleteAll(Iterable<T> entities)
	{
		ofy().delete(entities);
	}

	@Override
	public void deleteKeys(Iterable<Key<T>> keys)
	{
		ofy().delete(keys);
	}

	/**
	 * Clears all entities of specific kind. For test purpose only.
	 */
	@Override
	public void clearTable()
	{
		Iterable<Key<T>> allKeys = ofy().query(clazz).fetchKeys();

		// get all keys for entities of kind T
		deleteKeys(allKeys);
	}
}
