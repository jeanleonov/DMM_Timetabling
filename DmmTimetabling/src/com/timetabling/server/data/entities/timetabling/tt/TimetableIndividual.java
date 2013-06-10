package com.timetabling.server.data.entities.timetabling.tt;

import java.util.ArrayList;
import java.util.List;

import com.timetabling.server.data.entities.timetabling.Time;
import com.timetabling.server.data.entities.timetabling.lesson.Lesson;

public class TimetableIndividual {
	
	/** year - '2012' <=> academic year 2012-2013  */
	private int year;
	private boolean season;
	private Long activeVersion;
	
	private List<GroupTT> groupTTs;
	private List<TeacherTT> teacherTTs;
	private List<Lesson> allLessons;

	public TimetableIndividual() {
		groupTTs = new ArrayList<GroupTT>();
		teacherTTs = new ArrayList<TeacherTT>();
	}
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public boolean isSeason() {
		return season;
	}

	public void setSeason(boolean season) {
		this.season = season;
	}

	public Long getActiveVersion() {
		return activeVersion;
	}

	public void setActiveVersion(Long activeVersion) {
		this.activeVersion = activeVersion;
	}

	public List<GroupTT> getGroupTTs() {
		return groupTTs;
	}
	
	public void addGroupTT(GroupTT tt) {
		groupTTs.add(tt);
	}

	public List<TeacherTT> getTeacherTTs() {
		return teacherTTs;
	}
	
	public void addTeacherTT(TeacherTT tt) {
		teacherTTs.add(tt);
	}

	public List<Lesson> getAllLessons() {
		return allLessons;
	}

	public void setAllLessons(List<Lesson> allLessons) {
		this.allLessons = allLessons;
	}

	/**
	 * @return number of FREE computer rooms at target time
	 */
	public int getNumberOfFreeComputerRooms(Time time){
		// TODO
		return 0;
	}
	
	public void setVersion(Long version) {
		for (Lesson lesson : getAllLessons())
			lesson.setTimeFromVersion(version);
		activeVersion = version;
	}
	
	public void setVersionAndMoveLessonsInTTs(Long version) {
		for (TeacherTT teacherTT : teacherTTs)
			teacherTT.cleanDays();
		for (GroupTT groupTT : groupTTs)
			groupTT.cleanDays();
		for (Lesson lesson : getAllLessons())
			lesson.setTimeFromVersionAndMoveLessonsInTTs(version);
		activeVersion = version;
	}
	
}
