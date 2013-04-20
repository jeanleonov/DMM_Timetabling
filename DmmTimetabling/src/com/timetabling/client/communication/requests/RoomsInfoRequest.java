package com.timetabling.client.communication.requests;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.timetabling.client.communication.entities.RoomsInfoProxy;
import com.timetabling.server.base.data.dao.DaoServiceLocator;
import com.timetabling.server.data.managers.simple.RoomsInfoManager;

@Service( value = RoomsInfoManager.class, locator = DaoServiceLocator.class )
public interface RoomsInfoRequest extends RequestContext {

	Request<Void> setRoomsInfoForSemester(int year, boolean season, RoomsInfoProxy info);
	Request<RoomsInfoProxy> getRoomsInfoForSemester(int year, boolean season);
	
}
