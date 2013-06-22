package com.timetabling.server.generating.rules;

import java.util.SortedSet;

import com.timetabling.server.data.entities.timetabling.Time;
import com.timetabling.server.data.entities.timetabling.lesson.Lesson;
import com.timetabling.server.data.entities.timetabling.tt.GroupTT;
import com.timetabling.server.data.entities.timetabling.tt.TeacherTT;
import com.timetabling.server.data.entities.timetabling.tt.TimetableIndividual;
import com.timetabling.shared.enums.RuleType;

public class DaysLoading implements IRule {
	
	private final static int MAX_TEACHER_LOADING = 3;
	private final static int MAX_GROUP_LOADING = 3;
	private final static float TEACHER_NORMAL_LOADING_MARK = 0.5f;
	private final static float GROUP_NORMAL_LOADING_MARK = 0.5f;
	

	@Override
	public RuleType getRuleType() {
		return RuleType.DAYS_LOADING;
	}

	@Override
	public float checkTT(TimetableIndividual tt) {
		float teachersMark = 0;
		for (TeacherTT teacherTT : tt.getTeacherTTs())
			for (SortedSet<Lesson> day : teacherTT.getDays()) {
				if (day.size() <= MAX_TEACHER_LOADING && day.size() > 0)
					teachersMark += 0.5;
				if (day.size() < MAX_TEACHER_LOADING && day.size() > 1)
					teachersMark += 0.5;
			}
		teachersMark /= tt.getTeacherTTs().size()*Time.NUMBER_OF_DAYS*2;
		float groupsMark = 0;
		for (GroupTT groupTT : tt.getGroupTTs()) {
			for (SortedSet<Lesson> day : groupTT.getDays()) {
				if (day.size() <= MAX_GROUP_LOADING && day.size() > 0)
					groupsMark += 0.5;
				if (day.size() < MAX_GROUP_LOADING && day.size() > 1)
					groupsMark += 0.5;
			}
		}
		groupsMark /= tt.getGroupTTs().size()*Time.NUMBER_OF_DAYS*2;
		return teachersMark*TEACHER_NORMAL_LOADING_MARK + groupsMark*GROUP_NORMAL_LOADING_MARK;
	}

}
