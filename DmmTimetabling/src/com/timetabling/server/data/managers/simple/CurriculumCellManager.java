package com.timetabling.server.data.managers.simple;

import java.util.List;

import com.timetabling.server.base.data.dao.GenericDAO;
import com.timetabling.server.data.entities.curriculum.CurriculumCell;

public class CurriculumCellManager extends GenericDAO<CurriculumCell> {

	public CurriculumCellManager() {
		super(CurriculumCell.class);
	}
	
	public List<CurriculumCell> getCurriculumCells(int year, boolean season) {
		Utils.setNamespaceForSemester(year, season);
		return ofy().query(CurriculumCell.class).list();
	}
	
	public List<CurriculumCell> getCurriculumCellsForSpecialty(int year, boolean season, long specialtyId) {
		Utils.setNamespaceForSemester(year, season);
		return ofy().query(CurriculumCell.class).filter("specialtyId", specialtyId).list();
	}
	
	public List<CurriculumCell> getCurriculumCellsForSubject(int year, boolean season, long subjectId) {
		Utils.setNamespaceForSemester(year, season);
		return ofy().query(CurriculumCell.class).filter("subjectId", subjectId).list();
	}
	
	public List<CurriculumCell> getCurriculumCellsForJoin(int year, boolean season, long joinId) {
		Utils.setNamespaceForSemester(year, season);
		return ofy().query(CurriculumCell.class).filter("joinId", joinId).list();
	}
	
	public List<CurriculumCell> getCurriculumCellsForCathedra(int year, boolean season, long cathedraId) {
		Utils.setNamespaceForSemester(year, season);
		return ofy().query(CurriculumCell.class).filter("cathedraId", cathedraId).list();
	}

}
