package com.timetabling.client.communication.requests;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.timetabling.client.communication.entities.CurriculumCellProxy;
import com.timetabling.server.base.data.dao.DaoServiceLocator;
import com.timetabling.server.data.managers.simple.CurriculumCellJoinerManager;

@Service( value = CurriculumCellJoinerManager.class, locator = DaoServiceLocator.class )
public interface CurriculumCellJoinerRequest extends RequestContext {

	Request<Long> createJoin(int year, boolean season, List<Long> cellIds);	
	Request<List<CurriculumCellProxy>> getCellsInJoin(int year, boolean season, long joinId);
	
}
