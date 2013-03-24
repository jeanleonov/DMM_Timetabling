package com.rosinka.tt.server.entities.curriculum.extentions;

import javax.persistence.Transient;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;
import com.rosinka.tt.server.base.data.entities.DatastoreLongEntity;
import com.rosinka.tt.server.entities.curriculum.extentions.Teacher.TeacherRank;
import com.rosinka.tt.server.entities.tt.Time;

/** Describes convenience (WELL, BAD or IMPOSSIBLE) of time (field time) 
 *  for associated teacher (field parent)*/
public class Wish extends DatastoreLongEntity {
	
	public enum Priority{
		WELL (0), 
		BAD (1), 
		IMPOSSIBLE (2);
		
		int code;
		private Priority(int code) {
			this.code = code;
		}
		public int getCode() {
			return code;
		}
		static public Priority getByCode(int code) {
			switch (code) {
			case 0: return WELL;
			case 1: return BAD;
			default: return IMPOSSIBLE;
			}
		}
	}

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

	public Priority getPriority() {
		return priorityCode == -1? null : Priority.getByCode(priorityCode);
	}

	public void setPriority(Priority priority) {
		this.priorityCode = priority.getCode();
	}

	public TeacherRank getTeacherRank() {
		return teacherRank;
	}

	public void setTeacherRank(TeacherRank teacherRank) {
		this.teacherRank = teacherRank;
	}
}
