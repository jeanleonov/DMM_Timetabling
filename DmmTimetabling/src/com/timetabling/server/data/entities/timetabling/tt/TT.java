package com.timetabling.server.data.entities.timetabling.tt;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.timetabling.server.data.entities.timetabling.Time;
import com.timetabling.server.data.entities.timetabling.Time.WeekType;
import com.timetabling.server.data.entities.timetabling.lesson.Lesson;
import com.timetabling.shared.ConstantsServer;

public class TT {

	protected SortedSet<Lesson>[] days;
	protected List<Lesson> lessons;
	
	@SuppressWarnings("unchecked")
	public TT(List<Lesson> lessons) {
		days = new SortedSet[ConstantsServer.DAYS_IN_WEEK*2];
		for (int i=0; i<days.length; i++)
			days[i] = new TreeSet<Lesson>(LessonsComparator.get());
		for (Lesson lesson : lessons) {
			Time time = lesson.getTime();
			if (time.getWeekType() == WeekType.LOWER || time.getWeekType() == WeekType.FULL)
				days[time.getWeekDay()].add(lesson);
			if (time.getWeekType() == WeekType.UPPER || time.getWeekType() == WeekType.FULL)
				days[time.getWeekDay()+ConstantsServer.DAYS_IN_WEEK].add(lesson);
		}
		this.lessons = lessons;
	}

	public List<Lesson> getLessons() {
		return lessons;
	}

}
