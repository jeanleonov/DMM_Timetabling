package com.timetabling.server.generating.rules;

import com.timetabling.server.data.entities.timetabling.tt.TimetableIndividual;
import com.timetabling.shared.enums.RuleType;

public interface IRule {
	
	RuleType getRuleType();
	
	double checkTT(TimetableIndividual tt);
	
}
