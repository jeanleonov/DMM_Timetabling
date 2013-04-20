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

	Request<List<CurriculumCellProxy>> getCurriculumCells(int year, boolean season);
	Request<List<CurriculumCellProxy>> getCurriculumCellsForSpecialty(int year, boolean season, long specialtyId);
	Request<List<CurriculumCellProxy>> getCurriculumCellsForSubject(int year, boolean season, long subjectId);
	Request<List<CurriculumCellProxy>> getCurriculumCellsForJoin(int year, boolean season, long joinId);
	Request<List<CurriculumCellProxy>> getCurriculumCellsForCathedra(int year, boolean season, long cathedraId);
	
}
