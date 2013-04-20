package com.timetabling.client.communication.requests;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.timetabling.server.base.data.dao.DaoServiceLocator;
import com.timetabling.server.data.managers.curriculum.CurriculumExtensionSaver;

@Service( value = CurriculumExtensionSaver.class, locator = DaoServiceLocator.class )
public interface CurriculumExtendingRequest extends RequestContext {
	
	Request<Void> assigneCathedraToCurriculumCell (
			int year, boolean season,
			long cellId, 
			long cathedraId );

	Request<Void> assigneCathedrasToCurriculumCells (
			int year, boolean season,
			List<Long> cellIds, 
			List<Long> cathedraIds );
	
	Request<Void> setNumberOfSubgroupsToCurriculumCell (
			int year, boolean season,
			long cellId, 
			byte numberOfSubgroups );
	
	Request<Void> setNumberOfSubgroupsToCurriculumCells (
			int year, boolean season,
			List<Long> cellIds,
			List<Byte> numbersOfSubgroups );
	
	Request<Void> setJoinIdToCurriculumCell (
			int year, boolean season,
			long cellId, 
			long joinId );
	
	Request<Void> setJoinIdsToCurriculumCells (
			int year, boolean season,
			List<Long> cellIds, 
			List<Long> joinIds );
}
