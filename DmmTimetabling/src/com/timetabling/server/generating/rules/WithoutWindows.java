package com.timetabling.server.generating.rules;

import java.util.SortedSet;

import com.timetabling.server.data.entities.timetabling.Time;
import com.timetabling.server.data.entities.timetabling.lesson.Lesson;
import com.timetabling.server.data.entities.timetabling.tt.GroupTT;
import com.timetabling.server.data.entities.timetabling.tt.TeacherTT;
import com.timetabling.server.data.entities.timetabling.tt.TimetableIndividual;
import com.timetabling.shared.enums.RuleType;

public class WithoutWindows implements IRule {
	
	private final static float TEACHER_DAY_WITHOUT_WINDOW_MARK = 0.1f;
	private final static float GROUP_DAY_WITHOUT_WINDOW_MARK = 0.9f;

	@Override
	public RuleType getRuleType() {
		return RuleType.WITHOUT_WINDOWS;
	}

	@Override
	public float checkTT(TimetableIndividual tt) {
		float teachersMark = 0;
		for (TeacherTT teacherTT : tt.getTeacherTTs())
			for (SortedSet<Lesson> day : teacherTT.getDays())
				teachersMark += TEACHER_DAY_WITHOUT_WINDOW_MARK / (countWindows(day)+1);
		teachersMark /= tt.getTeacherTTs().size()*Time.NUMBER_OF_DAYS*2;
		float groupsMark = 0;
		for (GroupTT groupTT : tt.getGroupTTs())
			for (SortedSet<Lesson> day : groupTT.getDays())
				groupsMark += GROUP_DAY_WITHOUT_WINDOW_MARK / (countWindows(day)+1);
		groupsMark /= tt.getGroupTTs().size()*Time.NUMBER_OF_DAYS*2;
		return teachersMark + groupsMark;
	}
	
	private int countWindows(SortedSet<Lesson> day) {
		Time previous = null;
		int windows = 0;
		for (Lesson lesson : day) {
			if (previous != null && lesson.getTime().getLessonNumber()-previous.getLessonNumber() > 1)
				windows++;
			previous = lesson.getTime();
		}
		return windows;
	}

}
