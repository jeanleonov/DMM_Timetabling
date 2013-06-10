package com.timetabling.server.generating.rules;

import com.timetabling.server.data.entities.timetabling.lesson.Lesson;
import com.timetabling.server.data.entities.timetabling.tt.TimetableIndividual;
import com.timetabling.shared.enums.RuleType;

public class CollisionAvoiding implements IRule {

	@Override
	public RuleType getRuleType() {
		return RuleType.WITHOUT_COLLISIONS;
	}

	@Override
	public float checkTT(TimetableIndividual tt) {
		float penalty = 0;
		for (Lesson lesson : tt.getAllLessons())
			penalty += lesson.hasCollisions()? 1 : 0;
		return penalty==0? 1 : -penalty;
	}

}
