package com.timetabling.server.generating.rules.hard;

import com.timetabling.server.data.entities.timetabling.tt.TimetableIndividual;
import com.timetabling.server.generating.rules.IRule;
import com.timetabling.shared.enums.RuleType;

public class WithoutCollisions implements IRule {

	@Override
	public RuleType getRuleType() {
		return RuleType.WITHOUT_COLLISIONS;
	}

	@Override
	public float checkTT(TimetableIndividual tt) {
		// TODO
		return 0;
	}

}
