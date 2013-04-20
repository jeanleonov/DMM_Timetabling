package com.timetabling.server.data.entities.curriculum.extentions;

import javax.persistence.Transient;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;
import com.timetabling.server.base.data.entities.DatastoreLongEntity;
import com.timetabling.server.data.entities.timetabling.Time;
import com.timetabling.shared.enums.TeacherRank;
import com.timetabling.shared.enums.WishPriority;

/** Describes convenience (WELL, BAD or IMPOSSIBLE) of time (field time) 
 *  for associated teacher (field parent)*/
public class Wish extends DatastoreLongEntity {

	@Parent private Key<Teacher> parent;
	private int timeKey = -1;
	@Unindexed private int priorityCode = -1;
	
	@Transient private TeacherRank teacherRank;
	@Transient private Time time = null;

	public Wish() {
	}

	public Key<Teacher> getParent() {
		return parent;
	}

	public void setParent(Key<Teacher> parent) {
		this.parent = parent;
	}

	public int getTimeKey() {
		return timeKey;
	}

	public void setTimeKey(int timeKey) {
		this.timeKey = timeKey;
	}

	public int getPriorityCode() {
		return priorityCode;
	}

	public void setPriorityCode(int priorityCode) {
		this.priorityCode = priorityCode;
	}

	public Time getTime() {
		if (time == null) {
			if (timeKey == -1)
				return null;
			time = new Time(timeKey);
		}
		return time;
	}

	public void setTime(Time time) {
		this.timeKey = time.getKey();
		this.time = time;
	}

	public WishPriority getPriority() {
		return priorityCode == -1? null : WishPriority.getByCode(priorityCode);
	}

	public void setPriority(WishPriority priority) {
		this.priorityCode = priority.getCode();
	}

	public TeacherRank getTeacherRank() {
		return teacherRank;
	}

	public void setTeacherRank(TeacherRank teacherRank) {
		this.teacherRank = teacherRank;
	}
}
