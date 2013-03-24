package com.rosinka.tt.server.entities.tt;

import java.util.List;

import com.rosinka.tt.server.entities.curriculum.extentions.Teacher;

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
	
	public double getMarkForVersion(Version version) {
		// TODO
		return 1d;
	}

}
