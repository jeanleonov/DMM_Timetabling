package com.timetabling.client.communication.requests;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.timetabling.client.communication.entities.SubjectProxy;
import com.timetabling.server.base.data.dao.DaoServiceLocator;
import com.timetabling.server.data.managers.simple.SubjectManager;

@Service( value = SubjectManager.class, locator = DaoServiceLocator.class )
public interface SubjectRequest extends RequestContext {

	Request<Long> getSubjectIdFor(String subjectName);
	Request<SubjectProxy> getSpecialtyById(long id);
	Request<List<SubjectProxy>> getAllSpecialties();
	Request<Void>  setSubjectName(long subjectId, String subjectName);
	Request<Void>  setSpecialtyShortName(long subjectId, String subectDisplayName);
}
