package com.timetabling.server.data.entities.timetabling.tt;

import java.util.List;

import com.timetabling.server.data.entities.timetabling.lesson.Lesson;
import com.timetabling.server.data.managers.DaoFactory;

public class TeacherTT extends TT {
	
	private Long cathedraId;
	private Long teacherId;
	private String teacherName;

	public TeacherTT(List<Lesson> lessons, Long version) {
		super(lessons, version);
		for (Lesson lesson : lessons)
			lesson.setTeacherTT(this);
		if (!lessons.isEmpty()) {
			this.teacherId = lessons.get(0).getTeacherId();
			this.cathedraId = lessons.get(0).getCurriculumCell().getCathedraId();
			try {
				teacherName = DaoFactory.getTeacherManager().getById(cathedraId, teacherId).getName();
			}
			catch (Exception e) {
				teacherName = " - ";
			}
		}
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public Long getCathedraId() {
		return cathedraId;
	}

	public void setCathedraId(Long cathedraId) {
		this.cathedraId = cathedraId;
	}

}
