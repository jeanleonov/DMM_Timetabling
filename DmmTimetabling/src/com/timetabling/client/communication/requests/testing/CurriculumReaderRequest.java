package com.timetabling.client.communication.requests.testing;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.timetabling.server.base.data.dao.DaoServiceLocator;
import com.timetabling.server.data.managers.testing.CurriculumReaderRunner;

@Service( value = CurriculumReaderRunner.class, locator = DaoServiceLocator.class )
public interface CurriculumReaderRequest extends RequestContext {
	
	Request<Void> runReading();
	
}
