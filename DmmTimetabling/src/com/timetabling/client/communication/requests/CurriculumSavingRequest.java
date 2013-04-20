package com.timetabling.client.communication.requests;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.timetabling.client.communication.entities.CurriculumCellProxy;
import com.timetabling.server.base.data.dao.DaoServiceLocator;
import com.timetabling.server.data.managers.curriculum.CurriculumSaver;

@Service( value = CurriculumSaver.class, locator = DaoServiceLocator.class )
public interface CurriculumSavingRequest extends RequestContext {
	
	Request<Void> saveCurriculumsForSemester(
			int year, boolean season, 
			List<CurriculumCellProxy> curriculumsCells);
	
	Request<Void> saveCurriculumCell(
			int year, boolean season, 
			CurriculumCellProxy curriculumCell);
}
