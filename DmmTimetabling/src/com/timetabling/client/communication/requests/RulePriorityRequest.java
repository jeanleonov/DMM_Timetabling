package com.timetabling.client.communication.requests;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.timetabling.server.base.data.dao.DaoServiceLocator;
import com.timetabling.server.data.managers.simple.RulePriorityManager;

@Service( value = RulePriorityManager.class, locator = DaoServiceLocator.class )
public interface RulePriorityRequest extends RequestContext {

	Request<Void> setRulePriority(int ruleTypeCode, float priority);
	Request<Float> getRulePriority(int ruleTypeCode);
}
