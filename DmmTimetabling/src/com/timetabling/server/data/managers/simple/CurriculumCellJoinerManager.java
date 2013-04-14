package com.timetabling.server.data.managers.simple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.googlecode.objectify.Key;
import com.timetabling.server.base.common.KeyHelper;
import com.timetabling.server.base.data.dao.DAOT;
import com.timetabling.server.base.data.dao.DAOT.DatastoreOperation;
import com.timetabling.server.base.data.dao.GenericDAO;
import com.timetabling.server.data.entities.curriculum.CurriculumCell;
import com.timetabling.server.data.entities.curriculum.extentions.CurriculumCellJoiner;

public class CurriculumCellJoinerManager extends GenericDAO<CurriculumCellJoiner> {

	public CurriculumCellJoinerManager() {
		super(CurriculumCellJoiner.class);
	}
	
	/** @return id of created join
	 * @throws Exception */
	public long createJoin(final int year, final boolean season, final List<Long> cellIds) throws Exception {
		return DAOT.runInTransaction(logger, new DatastoreOperation<Long>() {

			@Override
			public Long run(DAOT daot) throws Exception {
				Utils.setNamespaceForSemester(year, season);
				CurriculumCellJoiner join = new CurriculumCellJoiner();
				long joinId = put(join).getId();
				List<Key<CurriculumCell>> cellKeys = new ArrayList<Key<CurriculumCell>>(cellIds.size());
				for (Long cellId : cellIds)
					cellKeys.add(KeyHelper.getKey(CurriculumCell.class, cellId));
				Collection<CurriculumCell> cells = ofy().get(cellKeys).values();
				for (CurriculumCell cell : cells)
					cell.setJoinId(joinId);
				ofy().put(cells);
				return joinId;
			}

			@Override
			public String getOperationName() {
				return "Joining of cells";
			}
			
		});
	}
	
	public List<CurriculumCell> getCellsInJoin(int year, boolean season, long joinId) {
		Utils.setNamespaceForSemester(year, season);
		return ofy().query(CurriculumCell.class).filter("joinId", joinId).list();
	}
	
	public List<List<CurriculumCell>> getJoinedCells(int year, boolean season) {
		Utils.setNamespaceForSemester(year, season);
		List<Key<CurriculumCellJoiner>> joinerKeys = ofy().query(CurriculumCellJoiner.class).listKeys();
		List<List<CurriculumCell>> result = new ArrayList<List<CurriculumCell>>(joinerKeys.size());
		for (Key<CurriculumCellJoiner> joinerKey : joinerKeys)
			result.add(getCellsInJoin(year, season, joinerKey.getId()));
		return result;
	}

}
