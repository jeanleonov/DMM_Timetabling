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

	Request<Void> putSubject(SubjectProxy subject);
	Request<Long> getSubjectIdFor(String subjectName);
	Request<SubjectProxy> getSubjectById(long id);
	Request<List<SubjectProxy>> getAllSubjects();
	Request<Void> setSubjectName(long subjectId, String subjectName);
	Request<Void> setSubjectDisplayName(long subjectId, String subectDisplayName);
	Request<Void> deleteSubject(long subjectId);
}
