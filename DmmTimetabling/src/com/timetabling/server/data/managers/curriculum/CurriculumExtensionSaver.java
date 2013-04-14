package com.timetabling.server.data.managers.curriculum;

import java.lang.reflect.Method;
import java.util.List;

import com.timetabling.server.base.data.dao.GenericDAO;
import com.timetabling.server.base.data.entities.DatastoreLongEntity;
import com.timetabling.server.data.entities.curriculum.CurriculumCell;
import com.timetabling.server.data.entities.curriculum.extentions.Cathedra;
import com.timetabling.server.data.managers.simple.Utils;

public class CurriculumExtensionSaver extends GenericDAO<CurriculumCell> {

	public CurriculumExtensionSaver() {
		super(CurriculumCell.class);
	}
	
	/** Should be used if cathedra assign itself to curriculum cell. <br>
	 *  It means that user is logged in as Cathedra.
	 *  @param year will be sent to {@link Utils#setNamespaceForSemester(year, season)}
	 *  @param season will be sent to {@link Utils#setNamespaceForSemester(year, season)}
	 *  @param cellId id of {@link CurriculumCell} <br>
	 *  	   <font color=gray> (field from {@link DatastoreLongEntity}
			   which is extended by {@link CurriculumCell})</font>
	 *  @param cathedraId id of {@link Cathedra} <br>
	 *  	   <font color=gray> (field from {@link DatastoreLongEntity}
               which is extended by {@link Cathedra}) </font> 
	 * @throws Exception */
	public void assigneCathedraToCurriculumCell (
			final int year, final boolean season,
			final long cellId, 
			final long cathedraId )
					throws Exception {
		Method cathedraSetter = CurriculumCell.class.getMethod("setCathedraId", Long.class);
		String namespace = Utils.getNamespaceNameForSemester(year, season);
		Utils.<CurriculumCell>setFieldValueInEntity(namespace, CurriculumCell.class, cellId, cathedraId, cathedraSetter);

	}
	
	/** Should be used if Margo assign cathedras to all curriculum cells. <br>
	 *  It means that user is logged in as Margo. <br>
	 *  <font color=gray>  Number of curriculum ids and cathedra ids must be equal!!! </font>
	 *  @param year will be sent to {@link Utils#setNamespaceForSemester(year, season)}
	 *  @param season will be sent to {@link Utils#setNamespaceForSemester(year, season)}
	 *  @param cellIds list of ids of {@link CurriculumCell}
	 *  @param cathedraIds list of ids of {@link Cathedra} 
	 * @throws Exception */
	public void assigneCathedrasToCurriculumCells (
			final int year, final boolean season,
			final List<Long> cellIds, 
			final List<Long> cathedraIds )
					throws Exception {
		Method cathedraSetter = CurriculumCell.class.getMethod("setCathedraId", Long.class);
		String namespace = Utils.getNamespaceNameForSemester(year, season);
		Utils.<CurriculumCell>setFieldValueInEntities(namespace, CurriculumCell.class, cellIds, cathedraIds, cathedraSetter);
	}
	
	public void	setNumberOfSubgroupsToCurriculumCell (
			final int year, final boolean season,
			final long cellId, 
			final byte numberOfSubgroups )
					throws Exception {
		Method numberOfSubgroupsSetter = CurriculumCell.class.getMethod("setNumberOfSubgroups", Byte.class);
		String namespace = Utils.getNamespaceNameForSemester(year, season);
		Utils.<CurriculumCell>setFieldValueInEntity(namespace, CurriculumCell.class, cellId, numberOfSubgroups, numberOfSubgroupsSetter);
	}
	
	public void	setNumberOfSubgroupsToCurriculumCells (
			final int year, final boolean season,
			final List<Long> cellIds,
			final List<Byte> numbersOfSubgroups )
					throws Exception {
		Method numberOfSubgroupsSetter = CurriculumCell.class.getMethod("setNumberOfSubgroups", Byte.class);
		String namespace = Utils.getNamespaceNameForSemester(year, season);
		Utils.<CurriculumCell>setFieldValueInEntities(namespace, CurriculumCell.class, cellIds, numbersOfSubgroups, numberOfSubgroupsSetter);
	}
	
	public void	setJoinIdToCurriculumCell (
			final int year, final boolean season,
			final long cellId, 
			final long joinId )
					throws Exception {
		Method joinIdSetter = CurriculumCell.class.getMethod("setJoinId", Long.class);
		String namespace = Utils.getNamespaceNameForSemester(year, season);
		Utils.<CurriculumCell>setFieldValueInEntity(namespace, CurriculumCell.class, cellId, joinId, joinIdSetter);
	}
	
	public void	setJoinIdsToCurriculumCells (
			final int year, final boolean season,
			final List<Long> cellIds, 
			final List<Long> joinIds )
					throws Exception {
		Method joinIdSetter = CurriculumCell.class.getMethod("setJoinId", Long.class);
		String namespace = Utils.getNamespaceNameForSemester(year, season);
		Utils.<CurriculumCell>setFieldValueInEntities(namespace, CurriculumCell.class, cellIds, joinIds, joinIdSetter);
	}
	
	public void	setDisplayNameToCurriculumCell (
			final int year, final boolean season,
			final long cellId, 
			final String displayName )
					throws Exception {
		Method displayNameSetter = CurriculumCell.class.getMethod("setDisplayName", String.class);
		String namespace = Utils.getNamespaceNameForSemester(year, season);
		Utils.<CurriculumCell>setFieldValueInEntity(namespace, CurriculumCell.class, cellId, displayName, displayNameSetter);
	}
	
	public void	setDisplayNamesToCurriculumCells (
			final int year, final boolean season,
			final List<Long> cellIds, 
			final List<String> displayNames )
					throws Exception {
		Method displayNameSetter = CurriculumCell.class.getMethod("setDisplayName", String.class);
		String namespace = Utils.getNamespaceNameForSemester(year, season);
		Utils.<CurriculumCell>setFieldValueInEntities(namespace, CurriculumCell.class, cellIds, displayNames, displayNameSetter);
	}
	
	
	
	
//=== magic Utils: =============================================
	
	// TODO delete it after checking Utils
	
//	private void setFieldValueInCurriculumCell (
//			final int year, final boolean season,
//			final long curriculumCellId, 
//			final Object value,
//			final Method setter,
//			final String operationName)
//					throws Exception {
//		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
//			@Override
//			public Void run(DAOT daot) throws Exception {
//				Utils.setNamespaceForSemester(year, season);
//				Key<CurriculumCell> curriculumCellKey 
//									= KeyHelper.getKey(CurriculumCell.class, curriculumCellId);
//				CurriculumCell cell = daot.getOfy().get(curriculumCellKey);
//				setter.invoke(cell, value);
//				daot.getOfy().put(cell);
//				return null;
//			}
//			@Override
//			public String getOperationName() {
//				return operationName;
//			}
//		});
//	}
//	
//	private void setFieldValueInCurriculumCells (
//			final int year, final boolean season,
//			final List<Long> curriculumCellIds, 
//			final List<?> values,
//			final Method setter,
//			final String operationName)
//					throws Exception {
//		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
//			@Override
//			public Void run(DAOT daot) throws Exception {
//				Utils.setNamespaceForSemester(year, season);
//				if (curriculumCellIds.size() != values.size())
//					throw new Exception("Arguments is wrong! Number of curriculum ids and values must be equal.");
//				List<Key<CurriculumCell>> curriculumCellKeys = convertIdsToKeys(curriculumCellIds);
//				Map<Key<CurriculumCell>,CurriculumCell> keysCellsMap = daot.getOfy().get(curriculumCellKeys);
//				Collection<CurriculumCell> cellsWithAssignedCathedras;
//				cellsWithAssignedCathedras = setValuesInLoadedCurriculumCells(
//											 keysCellsMap, curriculumCellIds, values, setter);
//				daot.getOfy().put(cellsWithAssignedCathedras);
//				return null;
//			}
//			@Override
//			public String getOperationName() {
//				return operationName;
//			}
//		});
//	}
//	
//	private final static 
//	List<Key<CurriculumCell>> convertIdsToKeys(List<Long> curriculumCellIds) {
//		List<Key<CurriculumCell>> curriculumCellKeys;
//		curriculumCellKeys = new ArrayList<Key<CurriculumCell>>(curriculumCellIds.size()); 
//		for (Long id : curriculumCellIds)
//			curriculumCellKeys.add(KeyHelper.getKey(CurriculumCell.class, id));
//		return curriculumCellKeys;
//	}
//	
//	private final static 
//	Collection<CurriculumCell> setValuesInLoadedCurriculumCells(
//			Map<Key<CurriculumCell>,CurriculumCell> loadedKeysCellsMap,
//			List<Long> curriculumCellIds, 
//			List<?> values,
//			Method setter)
//					throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		for (Key<CurriculumCell> cellKey : loadedKeysCellsMap.keySet()) {
//			Long cellId = cellKey.getId();
//			for (int i=0; i<curriculumCellIds.size(); i++)
//				if (cellId.equals(curriculumCellIds.get(i))) {
//					Object value = values.get(i);
//					CurriculumCell cell = loadedKeysCellsMap.get(cellKey); 
//					setter.invoke(cell, value);
//					break;
//				}
//		}
//		return loadedKeysCellsMap.values();
//	}
	
}
