package com.timetabling.client.communication.requests;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.timetabling.client.communication.entities.SpecialtyProxy;
import com.timetabling.server.base.data.dao.DaoServiceLocator;
import com.timetabling.server.data.managers.simple.SpecialtyManager;

@Service( value = SpecialtyManager.class, locator = DaoServiceLocator.class )
public interface SpecialtyRequest extends RequestContext {
	
	Request<Long> getSpecialtyIdFor(String specialtyName);
	Request<SpecialtyProxy> getSpecialtyById(long id);
	Request<List<SpecialtyProxy>> getAllSpecialties();
	Request<Void> setSpecialtyName(long specialtyId, String specialtyName);
	Request<Void> setSpecialtyShortName(long specialtyId, String specialtyShortName);
	
}
