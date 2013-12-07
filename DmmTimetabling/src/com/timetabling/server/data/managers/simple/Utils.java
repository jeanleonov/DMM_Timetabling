package com.timetabling.server.data.managers.simple;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.googlecode.objectify.Key;
import com.timetabling.server.base.common.KeyHelper;
import com.timetabling.server.base.common.NamespaceController;
import com.timetabling.server.base.data.dao.DAOT;
import com.timetabling.server.base.data.dao.DAOT.DatastoreOperation;
import com.timetabling.server.base.data.entities.DatastoreLongEntity;
import com.timetabling.shared.ConstantsShared;

public class Utils {
	
	private static Logger logger = Logger.getLogger(Utils.class.getSimpleName());
	
	/**  - CurriculumCell, Lesson, TimeWithVersion, Version and CuriculumCellJoiner (XXX-entities)
	 *  are placed in specified namespace (see gae datastore namespace). <br>
	 *   - Each semester is connected with namecpace. <br>
	 *   - It means that different XXX-entities can be placed in different namespaces.  <br>
	 *   - Use this method when you save or load some of XXX-entities for going to right namespace. <br>
	 *  For example: <br> 
	 *   - year=2012, semester=AUTUMN_WINTER means semester which start at 1 September 2012; <br>
	 *   - year=2012, semester=WINTER_SUMMER means semester which start at ~20 January 2012;
	 *  */
	public static void setNamespaceForSemester(int year, boolean season) {
		String namespace = getNamespaceNameForSemester(year, season);
		NamespaceController.getInstance().updateNamespace(namespace);
	}
	public static void setNamespaceGeneral() {
		NamespaceController.getInstance().updateNamespace(NamespaceController.generalNamespace);
	}
	
	public static String getNamespaceNameForSemester(int year, boolean season) {
		return new StringBuilder().append(year).append('_').append(getSeasonName(season)).toString();
	}
	
	public static String getSeasonName(boolean season) {
		return season==ConstantsShared.WINTER_SUMMER? "winter-summer" : "autumn-winter";
	}
	
	
	
	public static <Entity extends DatastoreLongEntity>
	void setFieldValueInEntity (
			final String namespaceName,
			final Class<Entity> entityClass,
			final long entityId, 
			final Object value,
			final Method setter)
					throws Exception {
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				NamespaceController.getInstance().updateNamespace(namespaceName);
				Key<Entity> entityKey = (Key<Entity>) KeyHelper.getKey(entityClass, entityId);
				Entity entity = daot.getOfy().get(entityKey);
				setter.invoke(entity, value);
				daot.getOfy().put(entity);
				return null;
			}
			@Override
			public String getOperationName() {
				StringBuilder builder = new StringBuilder();
				builder.append(value).append(" is set to entity ").append(entityClass.getSimpleName());
				builder.append(" by setter ").append(setter.getName());
				return builder.toString();
			}
		});
	}
	
	public static <Entity extends DatastoreLongEntity>
	void setFieldValueInEntities (
			final String namespaceName,
			final Class<Entity> entityClass,
			final List<Long> entitiesIds, 
			final List<?> values,
			final Method setter)
					throws Exception {
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				NamespaceController.getInstance().updateNamespace(namespaceName);
				if (entitiesIds.size() != values.size())
					throw new Exception("Arguments is wrong! Number of curriculum ids and values must be equal.");
				List<Key<Entity>> entityKeys = convertIdsToKeys(entitiesIds, entityClass);
				Map<Key<Entity>,Entity> keysEntitiesMap = daot.getOfy().get(entityKeys);
				Collection<Entity> entitiesWithSetValue;
				entitiesWithSetValue = setValuesInLoadedEntities(
											 keysEntitiesMap, entitiesIds, values, setter);
				daot.getOfy().put(entitiesWithSetValue);
				return null;
			}
			@Override
			public String getOperationName() {
				StringBuilder builder = new StringBuilder();
				builder.append("New values is set to entities ").append(entityClass.getSimpleName());
				builder.append(" by setter ").append(setter.getName());
				return builder.toString();
			}
		});
	}
	
	private final static <Entity extends DatastoreLongEntity>
	List<Key<Entity>> convertIdsToKeys(List<Long> entitiesIds, Class<Entity> entityClass) {
		List<Key<Entity>> entitiesKeys;
		entitiesKeys = new ArrayList<Key<Entity>>(entitiesIds.size()); 
		for (Long id : entitiesIds)
			entitiesKeys.add(KeyHelper.getKey(entityClass, id));
		return entitiesKeys;
	}
	
	private final static <Entity extends DatastoreLongEntity>
	Collection<Entity> setValuesInLoadedEntities(
			Map<Key<Entity>,Entity> loadedKeysEntitiesMap,
			List<Long> entitiesIds, 
			List<?> values,
			Method setter)
					throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (Key<Entity> entityKey : loadedKeysEntitiesMap.keySet()) {
			Long entityId = entityKey.getId();
			for (int i=0; i<entitiesIds.size(); i++)
				if (entityId.equals(entitiesIds.get(i))) {
					Object value = values.get(i);
					Entity entity = loadedKeysEntitiesMap.get(entityKey); 
					setter.invoke(entity, value);
					break;
				}
		}
		return loadedKeysEntitiesMap.values();
	}

}
