package com.timetabling.server.data.entities.timetabling.rule;

import javax.persistence.Id;

import com.timetabling.server.base.data.entities.DatastoreEntity;

public class RulePriority extends DatastoreEntity {
	
	@Id private long ruleTypeCode = -1;
	private float priority;
	
	public RulePriority() {
	}
	
	public RulePriority(int ruleTypeCode, float priority) {
		this.ruleTypeCode = ruleTypeCode;
		this.priority = priority;
	}

	public long getRuleTypeCode() {
		return ruleTypeCode;
	}
	
	public void setRuleTypeCode(long ruleTypeCode) {
		this.ruleTypeCode = ruleTypeCode;
	}
	
	public float getPriority() {
		return priority;
	}
	
	public void setPriority(float priority) {
		this.priority = priority;
	}
	
}
