package com.timetabling.server.data.entities.timetabling.tt;

import java.util.Iterator;
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
	protected Long activeVersion;
	
	@SuppressWarnings("unchecked")
	public TT(List<Lesson> lessons, Long version) {
		days = new SortedSet[ConstantsServer.DAYS_IN_WEEK*2];
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
	
	public void updateLessonPosition(Lesson lesson, Long newVersion) {
		Time oldTime = lesson.getTimeInVersion(activeVersion);
		if (oldTime.getWeekType() == WeekType.LOWER || oldTime.getWeekType() == WeekType.FULL) {
			SortedSet<Lesson> lowerWeekLessons = days[oldTime.getWeekDay()];
			Iterator<Lesson> iterator = lowerWeekLessons.iterator();
			while (iterator.hasNext())
				if (lesson == iterator.next()) {
					iterator.remove();
					break;
				}
		}
		if (oldTime.getWeekType() == WeekType.UPPER || oldTime.getWeekType() == WeekType.FULL) {
			SortedSet<Lesson> upperWeekLessons = days[oldTime.getWeekDay()+ConstantsServer.DAYS_IN_WEEK];
			Iterator<Lesson> iterator = upperWeekLessons.iterator();
			while (iterator.hasNext())
				if (lesson == iterator.next()) {
					iterator.remove();
					break;
				}
		}
		addLesson(lesson);
		activeVersion = newVersion;
	}
	
	private void addLesson(Lesson lesson) {
		Time time = lesson.getTime();
		if (time.getWeekType() == WeekType.LOWER || time.getWeekType() == WeekType.FULL)
			days[time.getWeekDay()].add(lesson);
		if (time.getWeekType() == WeekType.UPPER || time.getWeekType() == WeekType.FULL)
			days[time.getWeekDay()+ConstantsServer.DAYS_IN_WEEK].add(lesson);
	}
	
	public SortedSet<Lesson>[] getDays() {
		return days;
	}

}
