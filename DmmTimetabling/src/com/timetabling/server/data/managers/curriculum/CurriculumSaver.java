package com.timetabling.server.data.managers.curriculum;

import java.util.List;

import com.timetabling.server.base.data.dao.DAOT;
import com.timetabling.server.base.data.dao.DAOT.DatastoreOperation;
import com.timetabling.server.base.data.dao.GenericDAO;
import com.timetabling.server.data.entities.curriculum.CurriculumCell;
import com.timetabling.server.data.managers.simple.Utils;

public class CurriculumSaver extends GenericDAO<CurriculumCell> {

	public CurriculumSaver() {
		super(CurriculumCell.class);
	}
	
	/** What is 'year' and 'season'? For example: <br> 
	 *   - year=2012, semester=AUTUMN_WINTER means semester which start at 1 September 2012; <br>
	 *   - year=2012, semester=WINTER_SUMMER means semester which start at ~20 January 2012; <br>
	 *   Use constants Utils.WINTER_SUMMER and Utils.AUTUMN_WINTER !*/
	public void saveCurriculumsForSemester(
			final int year, 
			final boolean season, 
			final List<CurriculumCell> curriculumsCells) 
					throws Exception {
		Utils.setNamespaceForSemester(year, season);
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				daot.getOfy().put(curriculumsCells);
				return null;
			}
			@Override
			public String getOperationName() {
				return "Persisting of curriculums cells for " + Utils.getSeasonName(season) + " " + year + "season.";
			}
		});
	}
	
	public void saveCurriculumCell(
			final int year, 
			final boolean season, 
			final CurriculumCell curriculumCell) 
					throws Exception {
		Utils.setNamespaceForSemester(year, season);
		DAOT.runInTransaction(logger, new DatastoreOperation<Void>() {
			@Override
			public Void run(DAOT daot) throws Exception {
				daot.getOfy().put(curriculumCell);
				return null;
			}
			@Override
			public String getOperationName() {
				return "Persisting of curriculum cell for " + Utils.getSeasonName(season) + " " + year + "season.";
			}
		});
	}
	
}
