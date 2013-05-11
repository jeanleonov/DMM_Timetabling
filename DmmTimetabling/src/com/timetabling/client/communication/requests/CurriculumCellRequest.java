package com.timetabling.client.communication.requests;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.timetabling.client.communication.entities.CurriculumCellProxy;
import com.timetabling.server.base.data.dao.DaoServiceLocator;
import com.timetabling.server.data.managers.simple.CurriculumCellManager;

@Service( value = CurriculumCellManager.class, locator = DaoServiceLocator.class )
public interface CurriculumCellRequest extends RequestContext {

	Request<Void> putCurriculumCell(int year, boolean season, CurriculumCellProxy cell);
	Request<Void> setTeacherForLesson(int year, boolean season, long cellId, byte subgroup, long teacherId);
	Request<List<CurriculumCellProxy>> getCurriculumCells(int year, boolean season);
	Request<List<CurriculumCellProxy>> getCurriculumCellsForSpecialty(int year, boolean season, long specialtyId);
	Request<List<CurriculumCellProxy>> getCurriculumCellsForCource(int year, boolean season, byte course);
	Request<List<CurriculumCellProxy>> getCurriculumCellsForCathedra(int year, boolean season, long cathedraId);
	Request<List<CurriculumCellProxy>> getCurriculumCellsForSpecialtyCourse(int year, boolean season, long specialtyId, byte course);
	Request<List<CurriculumCellProxy>> getCurriculumCellsForCathedraCourse(int year, boolean season, long cathedraId, byte course);
	Request<List<CurriculumCellProxy>> getCurriculumCellsForCathedraSpecialty(int year, boolean season, long cathedraId, long specialtyId);
	Request<List<CurriculumCellProxy>> getCurriculumCellsForCathedraSpecialtyCourse(int year, boolean season, long cathedraId, long specialtyId, byte course);
	Request<Void> deleteCell(int year, boolean season, long cellId);
	
}
