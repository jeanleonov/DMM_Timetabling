package com.timetabling.server.base.data;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.web.bindery.requestfactory.shared.Locator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.util.DAOBase;
import com.timetabling.server.base.data.entities.DatastoreLongEntity;

/**
 * Generic @Locator for objects that extend StringIdEntity
 */
public class ObjectifyLongLocator extends Locator<DatastoreLongEntity, String> {
	@Override
	public DatastoreLongEntity create(Class<? extends DatastoreLongEntity> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public DatastoreLongEntity find(Class<? extends DatastoreLongEntity> clazz,
			String id) {
		DAOBase daoBase = new DAOBase();

		DatastoreLongEntity entity = daoBase.ofy().find(
				new Key<DatastoreLongEntity>(KeyFactory.stringToKey(id)));

		if (null != entity)
			entity.setEntityKey(id);

		return entity;
	}

	@Override
	public Class<DatastoreLongEntity> getDomainType() {
		// Never called
		return null;
	}

	@Override
	public String getId(DatastoreLongEntity domainObject) {
		return domainObject.getEntityKey();
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(DatastoreLongEntity domainObject) {
		return domainObject.getEntityVersion();
	}
}
