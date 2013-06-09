package com.timetabling.server.data.entities.timetabling.tt;

import java.util.List;

import com.timetabling.server.data.entities.timetabling.lesson.Lesson;

public class TeacherTT extends TT {
	
	private Long teacherId;

	public TeacherTT(List<Lesson> lessons, Long version) {
		super(lessons, version);
		for (Lesson lesson : lessons)
			lesson.setTeacherTT(this);
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

}
