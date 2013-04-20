package com.timetabling.client.communication.requests;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.timetabling.client.communication.entities.CathedraProxy;
import com.timetabling.server.base.data.dao.DaoServiceLocator;
import com.timetabling.server.data.managers.simple.CathedraManager;

@Service( value = CathedraManager.class, locator = DaoServiceLocator.class )
public interface CathedraRequest extends RequestContext {
	
	Request<Void> putCathedra(CathedraProxy cathedra);
	Request<Void> deleteCathedra(long cathedraId);
	Request<List<CathedraProxy>> getAllCathedras();
	Request<Void> setCathedraName(long cathedraId, String cathedraName);
	Request<Void> setCathedraEmail(long cathedraId, String cathedraEmail);
	
}
