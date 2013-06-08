package com.timetabling.server.data.entities.timetabling.tt;

import java.util.ArrayList;
import java.util.List;

import com.timetabling.server.data.entities.timetabling.lesson.Lesson;

public class GroupTT extends TT {
	
	private Long specialtyId;
	private byte course;
	private byte groupNumber;
	
	public GroupTT(List<Lesson> lessons) {
		super(lessons);
		for (Lesson lesson : lessons) {
			List<GroupTT> groupTTs = lesson.getGroupTTs();
			if (groupTTs == null) {
				groupTTs = new ArrayList<GroupTT>(3);
				lesson.setGroupTTs(groupTTs);
			}
			groupTTs.add(this);
		}
	}

	public Long getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(Long specialtyId) {
		this.specialtyId = specialtyId;
	}

	public byte getCourse() {
		return course;
	}

	public void setCourse(byte course) {
		this.course = course;
	}

	public byte getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(byte groupNumber) {
		this.groupNumber = groupNumber;
	}
	
}
