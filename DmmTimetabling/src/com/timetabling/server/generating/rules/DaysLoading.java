package com.timetabling.server.generating.rules;

import java.util.SortedSet;

import com.timetabling.server.data.entities.timetabling.lesson.Lesson;
import com.timetabling.server.data.entities.timetabling.tt.GroupTT;
import com.timetabling.server.data.entities.timetabling.tt.TeacherTT;
import com.timetabling.server.data.entities.timetabling.tt.TimetableIndividual;
import com.timetabling.shared.enums.RuleType;

public class DaysLoading implements IRule {
	
	private final static int MAX_TEACHER_LOADING = 3;
	private final static int MAX_GROUP_LOADING = 3;

	@Override
	public RuleType getRuleType() {
		return RuleType.DAYS_LOADING;
	}

	@Override
	public float checkTT(TimetableIndividual tt) {
		float mark = 0;
		for (TeacherTT teacherTT : tt.getTeacherTTs())
			for (SortedSet<Lesson> day : teacherTT.getDays()) {
				if (day.size() <= MAX_TEACHER_LOADING)
					mark += 0.5;
				if (day.size() < MAX_TEACHER_LOADING)
					mark += 0.5;
			}
		for (GroupTT groupTT : tt.getGroupTTs()) {
			for (SortedSet<Lesson> day : groupTT.getDays()) {
				if (day.size() <= MAX_GROUP_LOADING)
					mark += 0.5;
				if (day.size() < MAX_GROUP_LOADING)
					mark += 0.5;
			}
		}
		return mark / (tt.getGroupTTs().size() + tt.getTeacherTTs().size());
	}

}
