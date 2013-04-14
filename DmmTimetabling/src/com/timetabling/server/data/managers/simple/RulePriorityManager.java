package com.timetabling.server.data.managers.simple;

import com.googlecode.objectify.Key;
import com.timetabling.server.base.common.KeyHelper;
import com.timetabling.server.base.data.dao.GenericDAO;
import com.timetabling.server.data.entities.timetabling.rule.RulePriority;

public class RulePriorityManager extends GenericDAO<RulePriority> {

	public RulePriorityManager() {
		super(RulePriority.class);
	}
	
	public void setRulePriority(int ruleTypeCode, float priority) throws Exception {
		put(new RulePriority(ruleTypeCode,priority));
	}
	
	public float getRulePriority(int ruleTypeCode) throws Exception {
		Key<RulePriority> key = KeyHelper.getKey(RulePriority.class, (long) ruleTypeCode);
		RulePriority rulePriority = ofy().find(key);
		if (rulePriority == null)
			return 0f;
		return rulePriority.getPriority();
	}

}
