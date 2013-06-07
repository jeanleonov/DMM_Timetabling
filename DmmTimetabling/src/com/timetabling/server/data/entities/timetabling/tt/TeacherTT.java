package com.timetabling.server.data.entities.timetabling.tt;

import java.util.List;

import com.timetabling.server.data.entities.curriculum.extentions.Teacher;
import com.timetabling.server.data.entities.timetabling.Version;
import com.timetabling.server.data.entities.timetabling.lesson.Lesson;

/** Timetable of specified {@link Teacher}. <br>
 *  Implements {@link Markable} for generating and evaluation of timetable.*/
public class TeacherTT {
	
	private Teacher teacher;
	
	/**
	 * lessons contains:
	 * - all the lessons that this teacher has;
	 * - phantom lessons created from teacher's Wishes with priority IMPOSSIBLE (stub for identification busy time)
	 */
	private List<Lesson> lessons;

	public TeacherTT() {
	}

	public Teacher getTeacher() {
		return teacher;
	}
	
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	/**
	 * @return
	 * - all the lessons that this teacher has;
	 * - phantom lessons created from teacher's Wishes with priority IMPOSSIBLE (stub for identification busy time)
	 */
	public List<Lesson> getLessons() {
		return lessons;
	}
	
	public float getMarkForVersion(Version version) {
		// TODO
		return 1f;
	}

}
