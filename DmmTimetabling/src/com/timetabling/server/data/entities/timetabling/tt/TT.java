package com.timetabling.server.data.entities.timetabling.tt;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.timetabling.server.data.entities.timetabling.Time;
import com.timetabling.server.data.entities.timetabling.Time.WeekType;
import com.timetabling.server.data.entities.timetabling.lesson.Lesson;

public class TT {

	protected SortedSet<Lesson>[] days;
	protected List<Lesson> lessons;
	protected Long activeVersion;
	
	@SuppressWarnings("unchecked")
	public TT(List<Lesson> lessons, Long version) {
		days = new SortedSet[Time.NUMBER_OF_DAYS*2];
		for (int i=0; i<days.length; i++)
			days[i] = new TreeSet<Lesson>(LessonsComparator.get());
		for (Lesson lesson : lessons)
			addLesson(lesson);
		this.lessons = lessons;
		activeVersion = version;
	}

	public List<Lesson> getLessons() {
		return lessons;
	}
	
	public void cleanDays() {
		for (SortedSet<Lesson> day : days)
			day.clear();
	}
	
	public void addLesson(Lesson lesson) {
		Time time = lesson.getTime();
		if (time.getWeekType() == WeekType.LOWER || time.getWeekType() == WeekType.FULL)
			days[time.getWeekDay()].add(lesson);
		if (time.getWeekType() == WeekType.UPPER || time.getWeekType() == WeekType.FULL)
			days[time.getWeekDay()+Time.NUMBER_OF_DAYS].add(lesson);
	}
	
	public SortedSet<Lesson>[] getDays() {
		return days;
	}

}
