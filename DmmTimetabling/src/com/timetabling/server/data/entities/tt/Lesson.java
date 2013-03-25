package com.timetabling.server.data.entities.tt;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;
import com.timetabling.server.base.data.entities.DatastoreLongEntity;
import com.timetabling.server.data.entities.curriculum.CurriculumCell;
import com.timetabling.server.data.entities.curriculum.extentions.Teacher;

@Entity
@Cached
public class Lesson extends DatastoreLongEntity {

	@Parent private Key<CurriculumCell> parent;
	private byte subGroupNumber;
	private long teacherId;

	@Transient private Map<Version, Time> versionTimeMap;
	@Transient private List<GroupTT> groupTTs;
	@Transient private TeacherTT teacherTT;
	@Transient private List<Lesson> lessonsWithMyTeacherOrGroup;		// = groupTTs(i).lessons + taecherTT.lessons
	@Transient private Teacher teacher;
	@Transient private CurriculumCell curriculumCell;

	public Lesson() {
	}
	
	public byte getSubGroupNumber() {
		return subGroupNumber;
	}

	public void setSubGroupNumber(byte subGroupNumber) {
		this.subGroupNumber = subGroupNumber;
	}
	
	public long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(long teacherId) {
		this.teacherId = teacherId;
	}

	/**
	 * Checks: is busy its teacher or its group at specified time
	 *@return found collisions or null, if collisions didn't find
	 *@exception throws exception if time of some lesson is not fully defined
	 **/
	public List<Lesson> findPotentialCollisions(Time targetTime, Version version) throws Exception{
		List<Lesson> collisions=null;
		for (Lesson lesson : lessonsWithMyTeacherOrGroup)
			if (lesson != this && lesson.getTimeInVersion(version).hasConflictWith(targetTime)){
				if (collisions == null)
					collisions = new LinkedList<Lesson>();
				collisions.add(lesson);
			}
		return collisions;
	}
	
	public List<GroupTT> getGroupTTs() {
		return groupTTs;
	}
	
	/**
	 * Intended just for GroupTT in initialization process
	 * @param groutTT
	 */
	void addGoupTT(GroupTT groutTT) {
		if (groupTTs == null)
			groupTTs = new ArrayList<GroupTT>(curriculumCell.getNumberOfSubgroups());
		groupTTs.add(groutTT);
	}

	public Key<CurriculumCell> getParent() {
		return parent;
	}

	public void setParent(Key<CurriculumCell> parent) {
		this.parent = parent;
	}

	public Time getTimeInVersion(Version version) {
		return versionTimeMap.get(version);
	}

	public void setTimeInVersion(Time time, Version version) {
		versionTimeMap.put(version, time);
	}

	public TeacherTT getTeacherTT() {
		return teacherTT;
	}

	public void setTeacherTT(TeacherTT teacherTT) {
		this.teacherTT = teacherTT;
	}

	public List<Lesson> getLessonsWithMyTeacherOrGroup() {
		return lessonsWithMyTeacherOrGroup;
	}

	public void setLessonsWithMyTeacherOrGroup(
			List<Lesson> lessonsWithMyTeacherOrGroup) {
		this.lessonsWithMyTeacherOrGroup = lessonsWithMyTeacherOrGroup;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public CurriculumCell getCurriculumCell() {
		return curriculumCell;
	}

	public void setCurriculumCell(CurriculumCell curriculumCell) {
		this.curriculumCell = curriculumCell;
	}

	public void setGroupTTs(List<GroupTT> groupTTs) {
		this.groupTTs = groupTTs;
	}
	
}
