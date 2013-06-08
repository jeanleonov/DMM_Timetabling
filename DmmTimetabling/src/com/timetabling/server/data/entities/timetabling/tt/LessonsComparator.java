package com.timetabling.server.data.entities.timetabling.tt;

import java.util.Comparator;

import com.timetabling.server.data.entities.timetabling.lesson.Lesson;

public class LessonsComparator implements Comparator<Lesson> {
	
	private static LessonsComparator instance = null;
	
	private LessonsComparator() {
	}
	
	public static LessonsComparator get() {
		if (instance == null)
			instance = new LessonsComparator();
		return instance;
	}

	@Override
	public int compare(Lesson lesson1, Lesson lesson2) {
		int lessonsNumberDifference = lesson1.getTime().getLessonNumber() - lesson2.getTime().getLessonNumber(); 
		if (lessonsNumberDifference > 0)
			return 1;
		if (lessonsNumberDifference == 0)
			return 0;
		return -1;
	}

}
