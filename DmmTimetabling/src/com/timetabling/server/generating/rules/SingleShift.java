package com.timetabling.server.generating.rules;

import java.util.SortedSet;

import com.timetabling.server.data.entities.timetabling.Time;
import com.timetabling.server.data.entities.timetabling.lesson.Lesson;
import com.timetabling.server.data.entities.timetabling.tt.GroupTT;
import com.timetabling.server.data.entities.timetabling.tt.TimetableIndividual;
import com.timetabling.shared.enums.RuleType;

public class SingleShift implements IRule {

	private final static byte UNDEF_SHIFT = 0;
	private final static byte BACK_SHIFT = 1;
	private final static byte FIRST_SHIFT = 2;
	private final static byte LIKE_BACK_SHIFT = 3;
	private final static byte LIKE_FIRST_SHIFT = 4;
	
	@Override
	public RuleType getRuleType() {
		return RuleType.SINGLE_SHIFT;
	}

	@Override
	public float checkTT(TimetableIndividual tt) {
		float groupsMark = 0;
		for (GroupTT groupTT : tt.getGroupTTs()) {
			byte shift = UNDEF_SHIFT;
			for (SortedSet<Lesson> day : groupTT.getDays()) {
				if (shift == UNDEF_SHIFT)
					shift = getShift(day);
				else {
					byte thisShift = getShift(day);  
					if (shift == thisShift)
						groupsMark += 1;
					else if ((shift-thisShift)%2 == 0)
						groupsMark += 0.5;
				}
			}
		}
		groupsMark /= tt.getGroupTTs().size()*Time.NUMBER_OF_DAYS*2;
		return groupsMark;
	}
	
	public byte getShift(SortedSet<Lesson> day) {
		if (day.isEmpty())
			return UNDEF_SHIFT;
		int last = day.last().getTime().getLessonNumber();
		int first = day.first().getTime().getLessonNumber();
		if (first <= 1 && last < 3)
			return FIRST_SHIFT;
		if (first >= 2 && last < 5)
			return BACK_SHIFT;
		if (first <= 2 && last < 4)
			return LIKE_FIRST_SHIFT;
		if (first >= 2 && last < 6)
			return LIKE_BACK_SHIFT;
		return UNDEF_SHIFT;
	}

}
