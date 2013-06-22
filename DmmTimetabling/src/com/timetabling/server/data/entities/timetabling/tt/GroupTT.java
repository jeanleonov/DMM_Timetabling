package com.timetabling.server.data.entities.timetabling.tt;

import java.util.ArrayList;
import java.util.List;

import com.timetabling.server.data.entities.timetabling.lesson.Lesson;
import com.timetabling.server.data.managers.DaoFactory;

public class GroupTT extends TT {
	
	private Long specialtyId;
	private String specialtyName;
	private byte course;
	private byte groupNumber;
	
	public GroupTT(List<Lesson> lessons, Long version, Long specId, byte course, byte group) {
		super(lessons, version);
		for (Lesson lesson : lessons) {
			List<GroupTT> groupTTs = lesson.getGroupTTs();
			if (groupTTs == null) {
				groupTTs = new ArrayList<GroupTT>(3);
				lesson.setGroupTTs(groupTTs);
			}
			groupTTs.add(this);
		}
		this.specialtyId = specId;
		this.course = course;
		this.groupNumber = group;
		try {
			specialtyName = DaoFactory.getSpecialtyManager().getSpecialtyById(specialtyId).getShortName();
		}
		catch (Exception e) {
			specialtyName = "";
		}
	}

	public Long getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(Long specialtyId) {
		this.specialtyId = specialtyId;
	}

	public String getSpecialtyName() {
		return specialtyName;
	}

	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
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
