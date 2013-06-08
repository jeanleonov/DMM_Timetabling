package com.timetabling.client.base.communication;

import com.google.web.bindery.requestfactory.shared.ExtraTypes;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.timetabling.client.communication.entities.CathedraProxy;
import com.timetabling.client.communication.entities.CurriculumCellProxy;
import com.timetabling.client.communication.entities.RoomsInfoProxy;
import com.timetabling.client.communication.entities.SpecialtyProxy;
import com.timetabling.client.communication.entities.SubjectProxy;
import com.timetabling.client.communication.entities.TeacherProxy;
import com.timetabling.client.communication.entities.WishProxy;
import com.timetabling.client.communication.requests.CathedraRequest;
import com.timetabling.client.communication.requests.CurriculumCellJoinerRequest;
import com.timetabling.client.communication.requests.CurriculumCellRequest;
import com.timetabling.client.communication.requests.CurriculumExtendingRequest;
import com.timetabling.client.communication.requests.CurriculumSavingRequest;
import com.timetabling.client.communication.requests.RoomsInfoRequest;
import com.timetabling.client.communication.requests.RulePriorityRequest;
import com.timetabling.client.communication.requests.SpecialtyRequest;
import com.timetabling.client.communication.requests.SubjectRequest;
import com.timetabling.client.communication.requests.TeacherRequest;
import com.timetabling.client.communication.requests.testing.TestRequest;

/**
 * Request factory for current application. Include request for entities.
 */
@ExtraTypes( {	CathedraProxy.class, SpecialtyProxy.class, RoomsInfoProxy.class,
				SubjectProxy.class, TeacherProxy.class, CurriculumCellProxy.class, WishProxy.class} )
public interface ObjectifyRequestFactory extends RequestFactory {
	
	CathedraRequest createCathedraRequest();
	CurriculumCellJoinerRequest createCurriculumCellJoinerRequest();
	CurriculumCellRequest createCurriculumCellRequest();
	CurriculumExtendingRequest createCurriculumExtendingRequest();
	CurriculumSavingRequest createCurriculumSavingRequest();
	RoomsInfoRequest createRoomsInfoRequest();
	RulePriorityRequest createRulePriorityRequest();
	SpecialtyRequest createSpecialtyRequest();
	SubjectRequest createSubjectRequest();
	TeacherRequest createTeacherRequest();
	
	TestRequest createCurriculumReaderRequest();
	
}