package com.timetabling.server.data.managers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.timetabling.server.base.common.KeyHelper;
import com.timetabling.server.base.data.dao.DAOT;
import com.timetabling.server.base.data.dao.DAOT.DatastoreOperation;
import com.timetabling.server.base.data.dao.GenericDAO;
import com.timetabling.server.base.data.entities.DatastoreLongEntity;
import com.timetabling.server.data.entities.curriculum.CurriculumCell;
import com.timetabling.server.data.entities.curriculum.extentions.Cathedra;

public class CurriculumExtensionSaver extends GenericDAO<CurriculumCell> {

	/** Access modifier is DEFAULT here and it is not accident.
	 *  Just {@link DaoManagerFactory} can create managers */
	CurriculumExtensionSaver() {
		super(CurriculumCell.class);
	}
	
	/** Should be used if cathedra assign itself to curriculum cell. <br>
	 *  It means that user is logged in as Cathedra.
	 *  @param year will be sent to {@link Utils#setNamespaceForSemester(year, season)}
	 *  @param season will be sent to {@link Utils#setNamespaceForSemester(year, season)}
	 *  @param curriculumCellId id of {@link CurriculumCell} <br>
	 *  	   <font color=gray> (field from {@link DatastoreLongEntity}
			   which is extended by {@link CurriculumCell})</font>
	 *  @param cathedraId id of {@link Cathedra} <br>
	 *  	   <font color=gray> (field from {@link DatastoreLongEntity}
               which is extended by {@link Cathedra}) </font> 
	 * @throws Exception */
	public void assigneCathedraToCurriculumCell (
			final int year, final boolean season,
			final long curriculumCellId, 
			final long cathedraId )
					throws Exception {
		Method cathedraSetter = CurriculumCell.class.getMethod("setCathedraId", Long.class);
		String operationName = "Assigning of cathedra to curriculum cell";
		setFieldValueInCurriculumCell(
				year, season, curriculumCellId, cathedraId, 
				cathedraSetter,
				operationName);

	}
	
	/** Should be used if Margo assign cathedras to all curriculum cells. <br>
	 *  It means that user is logged in as Margo. <br>
	 *  <font color=gray>  Number of curriculum ids and cathedra ids must be equal!!! </font>
	 *  @param year will be sent to {@link Utils#setNamespaceForSemester(year, season)}
	 *  @param season will be sent to {@link Utils#setNamespaceForSemester(year, season)}
	 *  @param curriculumCellIds list of ids of {@link CurriculumCell}
	 *  @param cathedraIds list of ids of {@link Cathedra} 
	 * @throws Exception */
	public void assigneCathedrasToCurriculumCells (
			final int year, final boolean season,
			final List<Long> curriculumCellIds, 
			final List<Long> cathedraIds )
					throws Exception {
		Method cathedraSetter = CurriculumCell.class.getMethod("setCathedraId", Long.class);
		String operationName = "Assigning of cathedras to curriculum cells";
		setFieldValueInCurriculumCells(
				year, season, curriculumCellIds, cathedraIds, 
				cathedraSetter,
				operationName);
	}
	
	public void	setNumberOfSubgroupsToCurriculumCell (
			final int year, final boolean season,
			final long curriculumCellId, 
			final byte numberOfSubgroups )
					throws Exception {
		Method numberOfSubgroupsSetter = CurriculumCell.class.getMethod("setNumberOfSubgroups", Byte.class);
		String operationName = "Setting number of subgroups to curriculum cell";
		setFieldValueInCurriculumCell(
				year, season, curriculumCellId, numberOfSubgroups, 
				numberOfSubgroupsSetter,
				operationName);
	}
	
	public void	setNumberOfSubgroupsToCurriculumCells (
			final int year, final boolean season,
			final List<Long> curriculumCellIds, 
			final List<Byte> numbersOfSubgroups )
					throws Exception {
		Method numberOfSubgroupsSetter = CurriculumCell.class.getMethod("setNumberOfSubgroups", Byte.class);
		String operationName = "Setting numbers of subgroups to curriculum cells";
		setFieldValueInCurriculumCells(
				year, season, curriculumCellIds, numbersOfSubgroups, 
				numberOfSubgroupsSetter,
				operationName);
	}
	
	public void	setJoinIdToCurriculumCell (
			final int year, final boolean season,
			final long curriculumCellId, 
			final long joinId )
					throws Exception {
		Method joinIdSetter = CurriculumCell.class.getMethod("setJoinId", Long.class);
		String operationName = "Setting join id to curriculum cell";
		setFieldValueInCurriculumCell(
				year, season, curriculumCellId, joinId, 
				joinIdSetter,
				operationName);
	}
	
	public void	setJoinIdsToCurriculumCells (
			final int year, final boolean season,
			final List<Long> curriculumCellIds, 
			final List<Long> joinIds )
					throws Exception {
		Method joinIdSetter = CurriculumCell.class.getMethod("setJoinId", Long.class);
		String operationName = "Setting join ids to curriculum cells";
		setFieldValueInCurriculumCells(
				year, season, curriculumCellIds, joinIds, 
				joinIdSetter,
				operationName);
	}
	
	public void	setDisplayNameToCurriculumCell (
			final int year, final boolean season,
			final long curriculumCellId, 
			final String displayName )
					throws Exception {
		Method displayNameSetter = CurriculumCell.class.getMethod("setNumberOfSubgroups", Byte.class);
		String operationName = "Setting number of subgroups to curriculum cell";
		setFieldValueInCurriculumCell(
				year, season, curriculumCellId, displayName, 
				displayNameSetter,
				operationName);
	}
	
	public void	setDisplayNamesToCurriculumCells (
			final int year, final boolean season,
			final List<Long> curriculumCellIds, 
			final List<String> displayNames )
					throws Exception {
		Method displayNameSetter = CurriculumCell.class.getMethod("setDisplayName", String.class);
		String operationName = "Setting numbers of subgroups to curriculum cells";
		setFieldValueInCurriculumCells(
				year, season, curriculumCellIds, displayNames, 
				displayNameSetter,
				operationName);
	}
	
	
	
	
//=== magic Utils: =============================================
	
	
	private void setFieldValueInCurriculumCell (
			final int year, final boolean season,
			final long curriculumCellId, 
			final Object value,
			final Method setter,
			final String operationName)
					throws Exception {
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				Utils.setNamespaceForSemester(year, season);
				Key<CurriculumCell> curriculumCellKey 
									= KeyHelper.getKey(CurriculumCell.class, curriculumCellId);
				CurriculumCell cell = ofy().get(curriculumCellKey);
				setter.invoke(cell, value);
				ofy().put(cell);
				return null;
			}
			@Override
			public String getOperationName() {
				return operationName;
			}
		});
	}
	
	private void setFieldValueInCurriculumCells (
			final int year, final boolean season,
			final List<Long> curriculumCellIds, 
			final List<?> values,
			final Method setter,
			final String operationName)
					throws Exception {
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				Utils.setNamespaceForSemester(year, season);
				if (curriculumCellIds.size() != values.size())
					throw new Exception("Arguments is wrong! Number of curriculum ids and values must be equal.");
				List<Key<CurriculumCell>> curriculumCellKeys = convertIdsToKeys(curriculumCellIds);
				Map<Key<CurriculumCell>,CurriculumCell> keysCellsMap = daot.getOfy().get(curriculumCellKeys);
				Collection<CurriculumCell> cellsWithAssignedCathedras;
				cellsWithAssignedCathedras = setValuesInLoadedCurriculumCells(
											 keysCellsMap, curriculumCellIds, values, setter);
				daot.getOfy().put(cellsWithAssignedCathedras);
				return null;
			}
			@Override
			public String getOperationName() {
				return operationName;
			}
		});
	}
	
	private final static 
	List<Key<CurriculumCell>> convertIdsToKeys(List<Long> curriculumCellIds) {
		List<Key<CurriculumCell>> curriculumCellKeys;
		curriculumCellKeys = new ArrayList<Key<CurriculumCell>>(curriculumCellIds.size()); 
		for (Long id : curriculumCellIds)
			curriculumCellKeys.add(KeyHelper.getKey(CurriculumCell.class, id));
		return curriculumCellKeys;
	}
	
	private final static 
	Collection<CurriculumCell> setValuesInLoadedCurriculumCells(
			Map<Key<CurriculumCell>,CurriculumCell> loadedKeysCellsMap,
			List<Long> curriculumCellIds, 
			List<?> values,
			Method setter)
					throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (Key<CurriculumCell> cellKey : loadedKeysCellsMap.keySet()) {
			Long cellId = cellKey.getId();
			for (int i=0; i<curriculumCellIds.size(); i++)
				if (cellId.equals(curriculumCellIds.get(i))) {
					Object value = values.get(i);
					CurriculumCell cell = loadedKeysCellsMap.get(cellKey); 
					setter.invoke(cell, value);
					break;
				}
		}
		return loadedKeysCellsMap.values();
	}
	
}
